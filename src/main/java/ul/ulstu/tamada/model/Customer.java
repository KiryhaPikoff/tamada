package ul.ulstu.tamada.model;

import lombok.Data;
import ul.ulstu.tamada.model.enums.CustomerStatus;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "customers")
public class Customer extends User {

    private String name;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "customer",
            cascade = CascadeType.ALL
    )
    private List<Order> orders;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status;
}
