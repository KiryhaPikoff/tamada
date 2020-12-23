package ul.ulstu.tamada.service.order;

import ul.ulstu.tamada.rest.dto.order.CreateOrderRequest;
import ul.ulstu.tamada.rest.dto.order.DetailedOrderResponse;

import java.util.List;

public interface IOrderService {

    List<DetailedOrderResponse> findAllOrders();

    void createOrder(String userLogin, CreateOrderRequest order);

    void approveOrder(Long orderId);

    void cancelOrder(Long orderId);
}
