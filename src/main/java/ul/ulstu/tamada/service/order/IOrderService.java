package ul.ulstu.tamada.service.order;

import ul.ulstu.tamada.rest.dto.order.CreateOrderRequest;
import ul.ulstu.tamada.rest.dto.order.DetailedOrdersResponse;

public interface IOrderService {

    DetailedOrdersResponse findAllOrders();

    void createOrder(String userLogin, CreateOrderRequest order);

    void approveOrder(Long orderId);

    void cancelOrder(Long orderId);
}
