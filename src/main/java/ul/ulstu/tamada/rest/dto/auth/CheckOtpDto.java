package ul.ulstu.tamada.rest.dto.auth;

import lombok.Data;

@Data
public class CheckOtpDto {

    private String phone;

    private Long codeId;

    private String code;
}
