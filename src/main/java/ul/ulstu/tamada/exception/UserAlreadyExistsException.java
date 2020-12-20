package ul.ulstu.tamada.exception;

public class UserAlreadyExistsException extends TamadaException {

    public UserAlreadyExistsException(String login) {
        super(
                String.format("Пользователь с логином %s уже существует", login)
        );
    }
}
