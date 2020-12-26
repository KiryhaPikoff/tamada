package ul.ulstu.tamada.scheduler;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ul.ulstu.tamada.model.enums.OrderStatus;
import ul.ulstu.tamada.repository.IOrderRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Log4j2
@Component
public class OrdersDoneScheduler {

    private final IOrderRepository orderRepository;

    public OrdersDoneScheduler(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Scheduled(cron = "${orders.done-schedule}")
    public void handle() {
        doneOrders();
    }

    private void doneOrders() {
        orderRepository.getAllByStatusIn(List.of(OrderStatus.APPROVED))
                .forEach(order -> {
                    try {
                        var ordDate = order.getDate().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                        var now = LocalDate.now();
                        if (ordDate.equals(now)) {
                            order.setStatus(OrderStatus.DONE);
                            orderRepository.save(order);
                        }
                    } catch (Exception ex) {
                        log.error("Error while DONE order {}", order.getId(), ex);
                    }
                });
    }
}
