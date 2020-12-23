package ul.ulstu.tamada.service.auth.jwtextractor;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ul.ulstu.tamada.exception.TokenNotValidException;

import java.text.ParseException;
import java.util.Optional;

@Log4j2
@Component
public class JwtExtractor implements IJwtExtractor {

    private final JWSVerifier verifier;

    public JwtExtractor(JWSVerifier verifier) {
        this.verifier = verifier;
    }

    @Override
    public Optional<String> getId(String token) {
        SignedJWT signedJWT;
        try {
            signedJWT = SignedJWT.parse(token);
            var id = signedJWT.getJWTClaimsSet().getJWTID();
            return Optional.ofNullable(id);
        } catch (ParseException pex) {
            log.error("Token parsing error: ", pex);
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getSubject(String token) {
        SignedJWT signedJWT;
        try {
            signedJWT = SignedJWT.parse(token);
            var subject = signedJWT.getJWTClaimsSet().getSubject();
            return Optional.ofNullable(subject);
        } catch (ParseException pex) {
            log.error("Token parsing error: ", pex);
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getRole(String token) {
        SignedJWT signedJWT;
        try {
            signedJWT = SignedJWT.parse(token);
            var role = (String) signedJWT.getJWTClaimsSet().getClaim("role");
            return Optional.ofNullable(role);
        } catch (ParseException pex) {
            log.error("Token parsing error: ", pex);
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getClaim(String token, String claimName) {
        SignedJWT signedJWT;
        try {
            signedJWT = SignedJWT.parse(token);
            var claim = (String) signedJWT.getJWTClaimsSet().getClaim(claimName);
            return Optional.ofNullable(claim);
        } catch (ParseException pex) {
            log.error("Token parsing error: ", pex);
        }
        return Optional.empty();
    }

    @Override
    @SneakyThrows
    public boolean verify(String token) {
        SignedJWT signedJWT;

        try {
            signedJWT = SignedJWT.parse(token);
        } catch (ParseException pex) {
            throw new TokenNotValidException();
        }

        return signedJWT.verify(verifier);
    }
}
