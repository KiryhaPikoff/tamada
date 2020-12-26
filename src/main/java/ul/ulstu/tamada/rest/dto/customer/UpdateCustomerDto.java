package ul.ulstu.tamada.rest.dto.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateCustomerDto {

    @NotNull
    @ApiModelProperty("ФИО пользователя")
    private String name;

    @NotNull
    @ApiModelProperty("Пароль пользователя")
    private String password;
}
