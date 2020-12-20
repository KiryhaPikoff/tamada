package ul.ulstu.tamada.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegistrationRequest {

    @ApiModelProperty("Номер телефона пользователя")
    private String phone;

    @ApiModelProperty("Имя пользователя")
    private String name;

    @ApiModelProperty("Фамилия пользователя")
    private String surname;

    @ApiModelProperty("Отчество пользователя")
    private String patronymic;

    @ApiModelProperty("Пароль пользователя")
    private String password;
}
