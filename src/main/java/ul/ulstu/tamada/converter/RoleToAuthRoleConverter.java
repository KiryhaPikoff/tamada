package ul.ulstu.tamada.converter;

import org.springframework.stereotype.Component;
import ul.ulstu.tamada.configuration.enums.UserRole;
import ul.ulstu.tamada.exception.UserRoleNotFoundException;

@Component
public class RoleToAuthRoleConverter extends BaseConverter<String, UserRole> {

    @Override
    public UserRole convert(String role) {
        try {
            return UserRole.valueOf(role.toUpperCase());
        } catch (Exception exception) {
            throw new UserRoleNotFoundException(role);
        }
    }
}
