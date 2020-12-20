package ul.ulstu.tamada.configuration.encoder;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Log4j2
@Configuration
public class PasswordEncoderConfig {

    private static final Integer DEFAULT_STRENGTH = 15;

    @Bean
    @SneakyThrows
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(DEFAULT_STRENGTH);
    }
}