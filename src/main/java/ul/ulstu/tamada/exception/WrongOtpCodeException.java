package ul.ulstu.tamada.exception;

public class WrongOtpCodeException extends TamadaException {

    public WrongOtpCodeException() {
        super("Неверный код, попробуйте еще раз");
    }
}
