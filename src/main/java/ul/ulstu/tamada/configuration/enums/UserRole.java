package ul.ulstu.tamada.configuration.enums;

public enum UserRole {

    CUSTOMER(Names.CUSTOMER),
    ADMIN(Names.ADMIN);

    public static class Names {
        public static final String CUSTOMER = "customer";
        public static final String ADMIN = "admin";
    }

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
