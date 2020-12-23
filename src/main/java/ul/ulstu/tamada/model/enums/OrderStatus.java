package ul.ulstu.tamada.model.enums;

public enum OrderStatus {

    /* Заказ создан, но еще не подтвержден администратором */
    PROCESSING ,

    /* Заказ подтвержден администратором */
    APPROVED,

    /* Мероприятие было проведено */
    DONE,

    /* Заказ был отменен */
    CANCELED
}
