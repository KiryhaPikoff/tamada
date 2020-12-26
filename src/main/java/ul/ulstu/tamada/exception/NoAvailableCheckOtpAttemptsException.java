package ul.ulstu.tamada.exception;

public class NoAvailableCheckOtpAttemptsException extends TamadaException {

    public NoAvailableCheckOtpAttemptsException() {
        super("Не осталось попыток для проверки ОТП. Попробуйте зарегистрироваться позже.");
    }
}
