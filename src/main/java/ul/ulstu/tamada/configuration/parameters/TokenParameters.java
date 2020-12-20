package ul.ulstu.tamada.configuration.parameters;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class TokenParameters {

    private String ident;
    private String role;
    private Integer expiredInMinutes;
    private Map<String, Object> claims;
}
