package ul.ulstu.tamada.service.auth.jwtextractor;

import java.util.Optional;

public interface IJwtExtractor {

    Optional<String> getId(String token);

    Optional<String> getSubject(String token);

    Optional<String> getRole(String token);

    Optional<String> getClaim(String token, String claimName);

    boolean verify(String token);
}
