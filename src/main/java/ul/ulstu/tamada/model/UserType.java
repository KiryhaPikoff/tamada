package ul.ulstu.tamada.model;

import lombok.Getter;

@Getter
public enum UserType {

    CUSTOMER(Customer.class),
    ADMIN(Admin.class);

    private final Class<? extends User> userType;

    UserType(Class<? extends User> userType) {
        this.userType = userType;
    }
}
