package ul.ulstu.tamada.configuration.beans;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ul.ulstu.tamada.configuration.enums.UserRole;
import ul.ulstu.tamada.configuration.parameters.TokenParameters;
import ul.ulstu.tamada.configuration.properties.TokenProperties;

import java.util.Collections;

@Component
public class AdminTokenParametersFactory implements ITokenParametersFactory {

    private final TokenProperties tokenProperties;

    public AdminTokenParametersFactory(
            @Qualifier("adminTokenProperties") TokenProperties tokenProperties
    ) {
        this.tokenProperties = tokenProperties;
    }

    @Override
    public TokenParameters accessTokenParameters() {
        return TokenParameters.builder()
                .role(UserRole.Names.CUSTOMER)
                .expiredInMinutes(tokenProperties.getAccessTokenValidMinutes())
                .claims(Collections.emptyMap())
                .build();
    }

    @Override
    public TokenParameters refreshTokenParameters() {
        return TokenParameters.builder()
                .role(UserRole.Names.CUSTOMER)
                .expiredInMinutes(tokenProperties.getRefreshTokenValidMinutes())
                .claims(Collections.emptyMap())
                .build();
    }
}
