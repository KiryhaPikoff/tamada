package ul.ulstu.tamada.rest.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CheckOtpDto {

    @NotNull
    @ApiModelProperty("Номер телефона регистрируемого человека")
    private String phone;

    @NotNull
    @ApiModelProperty("ID ОТП")
    private Long codeId;

    @NotNull
    @ApiModelProperty("ОТП код")
    private String code;
}
