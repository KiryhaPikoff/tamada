package ul.ulstu.tamada.rest.dto.auth;

import lombok.Data;

@Data
public class OtpDto {

    private Long otpId;
    private String code;
}
