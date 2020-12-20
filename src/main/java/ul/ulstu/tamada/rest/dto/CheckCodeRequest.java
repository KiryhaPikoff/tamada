package ul.ulstu.tamada.rest.dto;

import lombok.Data;

@Data
public class CheckCodeRequest {

    private String phone;

    private Long codeId;

    private String code;
}
