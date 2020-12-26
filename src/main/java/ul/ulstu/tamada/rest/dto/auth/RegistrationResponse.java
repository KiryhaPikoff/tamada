package ul.ulstu.tamada.rest.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegistrationResponse {

    @ApiModelProperty("Номер телефона, на который был отправлен ОТП")
    private String phone;

    @ApiModelProperty("ID ОТП")
    private Long codeId;
}
