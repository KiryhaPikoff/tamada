package ul.ulstu.tamada.exception;

public class UserRoleNotFoundException extends TamadaException {

    public UserRoleNotFoundException(String role) {
        super(String.format("Роль %s не найдена", role));
    }
}
