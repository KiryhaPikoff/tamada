package ul.ulstu.tamada.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TokenPairDto {

    @ApiModelProperty("Access токен")
    private String access;

    @ApiModelProperty("Refresh токен")
    private String refresh;
}
