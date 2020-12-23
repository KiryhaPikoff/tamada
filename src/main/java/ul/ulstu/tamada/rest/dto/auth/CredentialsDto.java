package ul.ulstu.tamada.rest.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ul.ulstu.tamada.configuration.enums.UserRole;

@Data
public class CredentialsDto {

    @ApiModelProperty("(Логин | Телефон) пользователя")
    private String login;

    @ApiModelProperty("Пароль пользователя")
    private String password;

    @ApiModelProperty("Роль пользователя { CUSTOMER | ADMIN }")
    private UserRole role;
}
