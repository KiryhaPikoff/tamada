package ul.ulstu.tamada.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ul.ulstu.tamada.model.enums.AnimatorStatus;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "animators")
public class Animator {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "animators_seq"
    )
    @SequenceGenerator(
            name = "animators_seq",
            sequenceName = "animators_seq",
            allocationSize = 1
    )
    private Long id;

    private String name;

    private Integer age;

    @Lob
    private String description;

    @Lob
    private String motto;

    private Integer price;

    @Enumerated(EnumType.STRING)
    private AnimatorStatus status;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "animator",
            cascade = CascadeType.ALL
    )
    private List<Order> orders;
}
