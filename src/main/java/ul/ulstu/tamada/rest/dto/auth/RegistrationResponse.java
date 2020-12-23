package ul.ulstu.tamada.rest.dto.auth;

import lombok.Data;

@Data
public class RegistrationResponse {

    private String phone;

    private Long codeId;
}
