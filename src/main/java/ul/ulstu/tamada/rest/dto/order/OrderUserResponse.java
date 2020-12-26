package ul.ulstu.tamada.rest.dto.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderUserResponse {

    @ApiModelProperty("Номер телефона заказчика мероприятия")
    private String phone;

    @ApiModelProperty("ФИО заказчика")
    private String name;
}
