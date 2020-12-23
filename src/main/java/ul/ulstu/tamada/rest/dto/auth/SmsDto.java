package ul.ulstu.tamada.rest.dto.auth;

import lombok.Data;

@Data
public class SmsDto {

    private String phone;

    private String message;
}
