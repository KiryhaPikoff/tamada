package ul.ulstu.tamada.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IterationDto {

    private Integer iteration;
    private BigDecimal result;
    private String difference;
}
