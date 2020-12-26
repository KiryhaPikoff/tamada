package ul.ulstu.tamada.exception;

public class OrderNotFoundException extends TamadaException {

    public OrderNotFoundException(Long orderId) {
        super(String.format("Заказ №%s не найден", orderId));
    }
}
