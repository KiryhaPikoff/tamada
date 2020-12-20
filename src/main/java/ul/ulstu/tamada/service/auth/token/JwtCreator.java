package ul.ulstu.tamada.service.auth.token;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ul.ulstu.tamada.configuration.parameters.TokenParameters;
import ul.ulstu.tamada.configuration.security.keys.keyloader.IKeysLoader;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtCreator implements ITokenCreator {

    private final IKeysLoader keysLoader;
    private static final String ROLE_CLAIM_NAME = "role";

    public JwtCreator(IKeysLoader keysLoader) {
        this.keysLoader = keysLoader;
    }

    @Override
    @SneakyThrows
    public String createToken(TokenParameters parameters) {
        var uuid = UUID.randomUUID();
        var expires = Calendar.getInstance();
        expires.add(Calendar.MINUTE, parameters.getExpiredInMinutes());

        var signer = new RSASSASigner(keysLoader.getPrivateKey());
        var claims = new JWTClaimsSet.Builder()
                .subject(parameters.getIdent())
                .jwtID(uuid.toString())
                .expirationTime(expires.getTime())
                .claim(ROLE_CLAIM_NAME, parameters.getRole())
                .issueTime(
                        Date.from(
                                LocalDateTime.now()
                                        .atZone(ZoneId.systemDefault())
                                        .toInstant()
                        )
                )
                .build();

        var jwt = new SignedJWT(
                new JWSHeader
                        .Builder(JWSAlgorithm.RS256)
                        .build(),
                claims
        );

        jwt.sign(signer);

        return jwt.serialize();
    }
}
