package ul.ulstu.tamada.rest.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OtpDto {

    @ApiModelProperty("ID ОТП")
    private Long otpId;

    @ApiModelProperty("ОТП")
    private String code;
}
