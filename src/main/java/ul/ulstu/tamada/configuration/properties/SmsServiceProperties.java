package ul.ulstu.tamada.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "sms")
public class SmsServiceProperties {

    private String smscApiUrl;
}
