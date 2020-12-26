package ul.ulstu.tamada.exception;

public class CustomerNotFoundException extends TamadaException {

    public CustomerNotFoundException(String customerPhone) {
        super(String.format("Пользователь с номером телефона %s не найден", customerPhone));
    }
}
