package ul.ulstu.tamada.rest.dto.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DetailedOrdersResponse {

    @ApiModelProperty("Список заказов")
    private List<DetailedOrderResponse> orders;
}
