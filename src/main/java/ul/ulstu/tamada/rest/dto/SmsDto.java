package ul.ulstu.tamada.rest.dto;

import lombok.Data;

@Data
public class SmsDto {

    private String phone;

    private String message;
}
