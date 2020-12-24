package ul.ulstu.tamada.exception;

public class ExtensionNotAllowedException extends TamadaException {

    public ExtensionNotAllowedException(String extension) {
        super(String.format("Расширение файла %s не поддерживается", extension));
    }
}
