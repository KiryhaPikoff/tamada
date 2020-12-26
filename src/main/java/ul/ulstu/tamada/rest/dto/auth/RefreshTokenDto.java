package ul.ulstu.tamada.rest.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RefreshTokenDto {

    @NotNull
    @ApiModelProperty("refresh токен")
    private String refresh;
}
