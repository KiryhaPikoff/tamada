package ul.ulstu.tamada.configuration.beans;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ul.ulstu.tamada.configuration.properties.TokenProperties;

@Configuration
public class TokenPropertiesConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "jwt.customer")
    TokenProperties customerTokenProperties() {
        return new TokenProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "jwt.admin")
    TokenProperties adminTokenProperties() {
        return new TokenProperties();
    }
}
