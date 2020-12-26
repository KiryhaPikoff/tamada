package ul.ulstu.tamada.rest.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ul.ulstu.tamada.configuration.enums.UserRole;

import javax.validation.constraints.NotNull;

@Data
public class CredentialsDto {

    @NotNull
    @ApiModelProperty("(Логин | Телефон) пользователя")
    private String login;

    @NotNull
    @ApiModelProperty("Пароль пользователя")
    private String password;

    @NotNull
    @ApiModelProperty("Роль пользователя { CUSTOMER | ADMIN }")
    private UserRole role;
}
