package ul.ulstu.tamada.exception;

public class OtpNotFoundException extends TamadaException {

    public OtpNotFoundException() {
        super("ОТП код не найден");
    }
}
