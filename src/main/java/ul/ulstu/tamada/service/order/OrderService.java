package ul.ulstu.tamada.service.order;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ul.ulstu.tamada.exception.AnimatorNotFoundException;
import ul.ulstu.tamada.exception.CustomerNotFoundException;
import ul.ulstu.tamada.exception.OrderNotFoundException;
import ul.ulstu.tamada.exception.OperationWithOrderFailedException;
import ul.ulstu.tamada.model.Customer;
import ul.ulstu.tamada.model.Order;
import ul.ulstu.tamada.model.UserType;
import ul.ulstu.tamada.model.enums.OrderStatus;
import ul.ulstu.tamada.repository.IAnimatorRepository;
import ul.ulstu.tamada.repository.IOrderRepository;
import ul.ulstu.tamada.repository.IUserRepository;
import ul.ulstu.tamada.rest.dto.order.CreateOrderRequest;
import ul.ulstu.tamada.rest.dto.order.DetailedOrderResponse;
import ul.ulstu.tamada.rest.dto.order.DetailedOrdersResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final ConversionService conversionService;
    private final IUserRepository userRepository;
    private final IAnimatorRepository animatorRepository;

    private final List<OrderStatus> orderStatusesMayBeApproved = List.of(
            OrderStatus.PROCESSING
    );

    private final List<OrderStatus> orderStatusesMayBeCanceled = List.of(
            OrderStatus.PROCESSING,
            OrderStatus.APPROVED
    );

    public OrderService(
            IOrderRepository orderRepository,
            ConversionService conversionService,
            IUserRepository userRepository,
            IAnimatorRepository animatorRepository
    ) {
        this.orderRepository = orderRepository;
        this.conversionService = conversionService;
        this.userRepository = userRepository;
        this.animatorRepository = animatorRepository;
    }


    @Override
    public DetailedOrdersResponse findAllOrders() {
        var response = new DetailedOrdersResponse();

        var orders = orderRepository.getAllOrdersOrderByCreationDate().stream()
                .map(order -> conversionService.convert(order, DetailedOrderResponse.class))
                .collect(Collectors.toList());

        response.setOrders(orders);

        return response;
    }

    @Override
    public void createOrder(String userLogin, CreateOrderRequest orderDto) {
        var user = (Customer) userRepository.findByLogin(userLogin, UserType.CUSTOMER)
                .orElseThrow(() -> new CustomerNotFoundException(userLogin));

        var animator = animatorRepository.findById(orderDto.getAnimatorId())
                .orElseThrow(AnimatorNotFoundException::new);

        var order = new Order();
        order.setCustomer(user);
        order.setAnimator(animator);
        order.setDate(orderDto.getDate());
        order.setAddress(orderDto.getAddress());
        order.setCreatedAt(LocalDateTime.now());
        order.setEvent(orderDto.getEvent());
        order.setStatus(OrderStatus.PROCESSING);

        orderRepository.save(order);
    }

    @Override
    public void approveOrder(Long orderId) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (!orderStatusesMayBeApproved.contains(order.getStatus())) {
            throw new OperationWithOrderFailedException();
        }

        order.setStatus(OrderStatus.APPROVED);

        orderRepository.save(order);
    }

    @Override
    public void cancelOrder(Long orderId) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (!orderStatusesMayBeCanceled.contains(order.getStatus())) {
            throw new OperationWithOrderFailedException();
        }

        order.setStatus(OrderStatus.CANCELED);

        orderRepository.save(order);
    }
}
