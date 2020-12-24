package ul.ulstu.tamada.authorization;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import ul.ulstu.tamada.configuration.enums.UserRole;
import ul.ulstu.tamada.exception.TokenNotValidException;
import ul.ulstu.tamada.service.auth.jwtextractor.IJwtExtractor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Aspect
@Log4j2
@Configuration
public class AuthorizationAspect {

    private final IJwtExtractor jwtExtractor;

    private final List<Type> _authorizationAnnotations = List.of(
            AllowedUserRoles.class
    );

    public AuthorizationAspect(IJwtExtractor jwtExtractor) {
        this.jwtExtractor = jwtExtractor;
    }

    @Before("execution(* ul.ulstu.tamada.rest.controller.*.*(..))")
    public void before(JoinPoint joinPoint) {
        log.info("Authorization started: ");

        var methodAuthAnnotations = this.obtainMethodAnnotations(joinPoint);

        if (methodAuthAnnotations.isEmpty()) {
            return;
        }

        var isAuthorized = methodAuthAnnotations.stream()
            .anyMatch(annotation -> this.authorize(annotation, joinPoint));

        if (!isAuthorized) {
            log.info("Authorization failed");
            throw new AccessDeniedException("Доступ запрещен");
        }
    }

    private boolean authorize(Annotation annotation, JoinPoint joinPoint) {
        var method = this.obtainMethod(joinPoint);
        var jwt = this.obtainJwt();
        var role = jwtExtractor.getRole(jwt).isEmpty() ? null : jwtExtractor.getRole(jwt).get();
        var sub = jwtExtractor.getSubject(jwt);

        log.info("Authorization: role {} and sub {}", role, sub);

        if (annotation instanceof AllowedUserRoles) {
            var allowedRolesAnnotation = method.getDeclaredAnnotation(AllowedUserRoles.class);
            var allowedRoles = List.of(allowedRolesAnnotation.roles());
            if (allowedRoles.isEmpty()) {
                return false;
            }
            var isAllowedRole = allowedRoles.stream()
                    .anyMatch(userRole -> userRole.getName().equals(role));
            if (isAllowedRole) {
                return true;
            }
            var allowedRoleNames = allowedRoles.stream()
                    .map(UserRole::getName)
                    .collect(Collectors.joining("; "));
            log.error("Authorization failed for {} role {}, method is allowed only for [{}]", sub, role, allowedRoleNames);
        }

        return false;
    }

    private List<Annotation> obtainMethodAnnotations(JoinPoint joinPoint) {
        var methodAnnotations = this.obtainMethod(joinPoint).getDeclaredAnnotations();
        return Stream.of(methodAnnotations)
                .filter(annotation -> {
                            var annotType = annotation.annotationType();
                            return _authorizationAnnotations.contains(annotType);
                        }
                ).collect(Collectors.toList());
    }

    private String obtainJwt() {
        try {
            var stringToken = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return ((Jwt) stringToken).getTokenValue();
        } catch (ClassCastException cce) {
            throw new TokenNotValidException();
        } catch (Exception exception) {
            var message = "Not Jwt type authorization attempt was made";
            log.error(message, exception);
            throw new AccessDeniedException(message);
        }
    }

    private Method obtainMethod(JoinPoint joinPoint) {
        var signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }
}
