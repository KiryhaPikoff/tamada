package ul.ulstu.tamada.converter;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ul.ulstu.tamada.model.Order;
import ul.ulstu.tamada.rest.dto.order.DetailedOrderResponse;
import ul.ulstu.tamada.rest.dto.order.OrderAnimatorResponse;
import ul.ulstu.tamada.rest.dto.order.OrderUserResponse;

@Component
public class OrderToDetailedOrderResponseConverter extends BaseConverter<Order, DetailedOrderResponse> {

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public DetailedOrderResponse convert(Order order) {
        var detailedOrder = new DetailedOrderResponse();

        detailedOrder.setId(order.getId());
        detailedOrder.setDate(order.getDate());
        detailedOrder.setEvent(order.getEvent());
        detailedOrder.setCreatedAt(order.getCreatedAt());
        detailedOrder.setAddress(order.getAddress());
        detailedOrder.setStatus(order.getStatus());

        var animator = new OrderAnimatorResponse();
        animator.setId(order.getAnimator().getId());
        animator.setName(order.getAnimator().getName());
        detailedOrder.setAnimator(animator);

        var user = new OrderUserResponse();
        user.setName(order.getCustomer().getName());
        user.setPhone(order.getCustomer().getLogin());
        detailedOrder.setUser(user);

        return detailedOrder;
    }
}
