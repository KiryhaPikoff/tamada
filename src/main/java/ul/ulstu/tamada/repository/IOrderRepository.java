package ul.ulstu.tamada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ul.ulstu.tamada.model.Order;
import ul.ulstu.tamada.model.enums.OrderStatus;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT order FROM Order order ORDER BY order.createdAt DESC NULLS LAST")
    List<Order> getAllOrdersOrderByCreationDate();

    List<Order> getAllByStatusIn(List<OrderStatus> statusIn);
}
