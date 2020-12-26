package ul.ulstu.tamada.rest.dto.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderAnimatorResponse {

    @ApiModelProperty("Идентификатор аниматора")
    private Long id;

    @ApiModelProperty("ФИО аниматора")
    private String name;
}
