package ul.ulstu.tamada.model;

import lombok.Data;
import ul.ulstu.tamada.model.enums.EventType;
import ul.ulstu.tamada.model.enums.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "orders_seq"
    )
    @SequenceGenerator(
            name = "orders_seq",
            sequenceName = "orders_seq",
            allocationSize = 1
    )
    private Long id;

    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animator_id", nullable = false)
    private Animator animator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private EventType event;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String address;

    private LocalDateTime createdAt;
}
