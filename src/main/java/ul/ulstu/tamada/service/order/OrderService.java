package ul.ulstu.tamada.service.order;

import org.springframework.stereotype.Service;
import ul.ulstu.tamada.rest.dto.order.CreateOrderRequest;
import ul.ulstu.tamada.rest.dto.order.DetailedOrderResponse;

import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Override
    public List<DetailedOrderResponse> findAllOrders() {
        return null;
    }

    @Override
    public void createOrder(String userLogin, CreateOrderRequest order) {

    }

    @Override
    public void approveOrder(Long orderId) {

    }

    @Override
    public void cancelOrder(Long orderId) {

    }
}
