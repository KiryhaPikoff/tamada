package ul.ulstu.tamada.configuration.beans;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import ul.ulstu.tamada.configuration.security.keys.keyloader.IKeysLoader;

import java.security.interfaces.RSAPublicKey;

@Configuration
public class JwtVerifierConfiguration {

    private final IKeysLoader keysLoader;

    public JwtVerifierConfiguration(IKeysLoader keysLoader) {
        this.keysLoader = keysLoader;
    }

    @Bean
    JWSVerifier jwtVerifier() {
        return new RSASSAVerifier((RSAPublicKey) keysLoader.getPublicKey());
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
                .withPublicKey(
                        (RSAPublicKey) keysLoader.getPublicKey()
                ).build();
    }
}
