package ul.ulstu.tamada.exception;

public class OperationWithOrderFailedException extends TamadaException {

    public OperationWithOrderFailedException() {
        super("Сейчас невозможно выполнить эту операцию над заказом");
    }
}
