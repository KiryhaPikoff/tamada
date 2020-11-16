package ul.ulstu.tamada.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Resp2 {

    private BigDecimal[][] preobraz;
    private BigDecimal[][] nevyazky;
    private List<Iter2> iterations;
}
