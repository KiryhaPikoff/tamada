package ul.ulstu.tamada.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "otp_codes")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "otp_codes_seq")
    @SequenceGenerator(name = "otp_codes_seq",
            sequenceName = "otp_codes_seq", allocationSize = 1)
    private Long id;
    private String phone;
    private String code;
    private LocalDateTime blockUntil;
    private Integer availableAttempts;
}
