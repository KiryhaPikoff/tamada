package ul.ulstu.tamada.service.statistic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ul.ulstu.tamada.model.enums.EventType;
import ul.ulstu.tamada.model.enums.OrderStatus;
import ul.ulstu.tamada.rest.dto.statistic.OrderStatisticResponse;
import ul.ulstu.tamada.rest.dto.statistic.OrdersStatisticResponse;
import ul.ulstu.tamada.repository.IOrderRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class StatisticService implements IStatisticService {

    private final IOrderRepository orderRepository;

    @Override
    public OrdersStatisticResponse getOrdersStatistic() {
        var doneOrders = orderRepository.getAllByStatusIn(
                List.of(OrderStatus.DONE)
        );

        var ordersByEventType = Stream.of(EventType.values())
                .map(eventType -> {
                    var ordersByEventResponse = new OrderStatisticResponse();

                    var ordersWithEventType = doneOrders.stream()
                            .filter(order -> order.getEvent() == eventType)
                            .collect(Collectors.toList());

                    ordersByEventResponse.setEvent(eventType);
                    ordersByEventResponse.setCount(ordersWithEventType.size());

                    return ordersByEventResponse;
                })
                .collect(Collectors.toList());

        var response = new OrdersStatisticResponse();

        response.setStatistics(ordersByEventType);
        response.setTotal(
                ordersByEventType.stream()
                        .map(OrderStatisticResponse::getCount)
                        .reduce(0, Integer::sum)
        );

        return response;
    }
}
