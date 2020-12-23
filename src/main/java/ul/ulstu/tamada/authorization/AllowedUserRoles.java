package ul.ulstu.tamada.authorization;

import ul.ulstu.tamada.configuration.enums.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowedUserRoles {

    UserRole[] roles() default {};
}
