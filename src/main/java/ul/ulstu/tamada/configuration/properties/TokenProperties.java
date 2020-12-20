package ul.ulstu.tamada.configuration.properties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenProperties {

    private Integer accessTokenValidMinutes;
    private Integer refreshTokenValidMinutes;
}
