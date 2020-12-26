package ul.ulstu.tamada.rest.dto.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CustomerInfoDto {

    @ApiModelProperty("Номер телефона заказчика мероприятия")
    private String phone;

    @ApiModelProperty("ФИО заказчика")
    private String name;

    @ApiModelProperty("Список заказов пользователя")
    private List<CustomerOrderResponse> orders;
}
