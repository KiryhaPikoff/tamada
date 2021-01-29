package ul.ulstu.tamada.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Aspect
@Log4j2
@Configuration
@RequiredArgsConstructor
public class LoggingAspect {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Before("execution(* ul.ulstu.tamada.service..*.*(..))")
    public void before(JoinPoint joinPoint) {
        var argsJson = Stream.of(joinPoint.getArgs())
                .map(arg -> {
                    try {
                        var argJson = objectMapper.writeValueAsString(arg);
                        return String.format("[%s]", argJson);
                    } catch (JsonProcessingException e) {
                        log.error("Couldn't log method args");
                        return "[missed arg]";
                    }
                }).collect(Collectors.joining("\n"));

        var classMethod = String.format(
                "%s::%s",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName()
        );

        log.info("Method call {} with args \n {}", classMethod, argsJson);
    }
}
