package ul.ulstu.tamada.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegistrationRequest {

    @ApiModelProperty("Номер телефона пользователя")
    private String phone;

    @ApiModelProperty("ФИО пользователя")
    private String name;

    @ApiModelProperty("Пароль пользователя")
    private String password;
}
