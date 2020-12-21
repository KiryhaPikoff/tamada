package ul.ulstu.tamada.converter;

import org.springframework.stereotype.Component;
import ul.ulstu.tamada.configuration.enums.UserRole;
import ul.ulstu.tamada.model.UserType;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserRoleToUserTypeConverter extends BaseConverter<UserRole, UserType> {

    private final Map<UserRole, UserType> _userRoleToType = new HashMap<>() {{
        put(UserRole.CUSTOMER, UserType.CUSTOMER);
        put(UserRole.ADMIN, UserType.ADMIN);
    }};

    @Override
    public UserType convert(UserRole userRole) {
        return _userRoleToType.get(userRole);
    }
}