package ul.ulstu.tamada.rest.dto.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerAnimatorResponse {

    @ApiModelProperty("Идентификатор аниматора")
    private Long id;

    @ApiModelProperty("ФИО аниматора")
    private String name;
}
