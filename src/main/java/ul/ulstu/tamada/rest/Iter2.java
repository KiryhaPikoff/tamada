package ul.ulstu.tamada.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Iter2 {

    private Integer iteration;

    private BigDecimal x1;
    private BigDecimal x2;
    private BigDecimal x3;
    private BigDecimal x4;

    private BigDecimal R1;
    private BigDecimal R2;
    private BigDecimal R3;
    private BigDecimal R4;

    private BigDecimal norma;
}
