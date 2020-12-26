package ul.ulstu.tamada.converter;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ul.ulstu.tamada.model.Customer;
import ul.ulstu.tamada.rest.dto.customer.CustomerAnimatorResponse;
import ul.ulstu.tamada.rest.dto.customer.CustomerInfoDto;
import ul.ulstu.tamada.rest.dto.customer.CustomerOrderResponse;

import java.util.stream.Collectors;

@Component
public class CustomerToCustomerInfoDtoConverter extends BaseConverter<Customer, CustomerInfoDto> {

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public CustomerInfoDto convert(Customer customer) {
        var customerInfo = new CustomerInfoDto();

        customerInfo.setName(customer.getName());
        customerInfo.setPhone(customer.getLogin());

        var customerOrders = customer.getOrders().stream()
                .map(order -> {
                    var customerOrder = new CustomerOrderResponse();
                    customerOrder.setId(order.getId());
                    customerOrder.setAddress(order.getAddress());
                    customerOrder.setCreatedAt(order.getCreatedAt());
                    customerOrder.setDate(order.getDate());
                    customerOrder.setEvent(order.getEvent());

                    var customerAnimator = new CustomerAnimatorResponse();
                    customerAnimator.setId(order.getAnimator().getId());
                    customerAnimator.setName(order.getAnimator().getName());

                    customerOrder.setAnimator(customerAnimator);
                    customerOrder.setStatus(order.getStatus());

                    return customerOrder;
                })
                .collect(Collectors.toList());

        customerInfo.setOrders(customerOrders);

        return customerInfo;
    }
}
