package ul.ulstu.tamada.rest.dto;

import lombok.Data;

@Data
public class CheckOtpDto {

    private String phone;

    private String otpId;

    private String code;
}
