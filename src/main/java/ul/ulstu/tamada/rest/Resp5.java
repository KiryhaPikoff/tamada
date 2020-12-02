package ul.ulstu.tamada.rest;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Resp5 {

    private List<BigDecimal> lagrCoefs;
    private BigDecimal lnVal;
    private String lnString;

    private BigDecimal[][] tableEndSub;
    private BigDecimal newVal;
    private String newString;

    private BigDecimal[][] linSplainCoefs;
    private BigDecimal splainVal;
    private List<String> splainsString;
}
