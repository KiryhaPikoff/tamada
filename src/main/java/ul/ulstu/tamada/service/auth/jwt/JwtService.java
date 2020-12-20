package ul.ulstu.tamada.service.auth.jwt;

import lombok.Builder;
import ul.ulstu.tamada.configuration.beans.ITokenParametersFactory;
import ul.ulstu.tamada.exception.TokenNotValidException;
import ul.ulstu.tamada.rest.dto.CredentialsDto;
import ul.ulstu.tamada.rest.dto.TokenPairDto;
import ul.ulstu.tamada.service.auth.jwtextractor.IJwtExtractor;
import ul.ulstu.tamada.service.auth.token.ITokenCreator;

@Builder
public class JwtService implements IJwtService {

    private final IJwtExtractor jwtExtractor;
    private final ITokenParametersFactory tokenParametersFactory;
    private final ITokenCreator tokenCreator;

    public JwtService(
            IJwtExtractor jwtExtractor,
            ITokenParametersFactory tokenParametersFactory,
            ITokenCreator tokenCreator) {
        this.jwtExtractor = jwtExtractor;
        this.tokenParametersFactory = tokenParametersFactory;
        this.tokenCreator = tokenCreator;
    }

    @Override
    public TokenPairDto getTokenPairByIdent(String ident) {
        var accessParams = tokenParametersFactory.accessTokenParameters();
        accessParams.setIdent(ident);
        var access = tokenCreator.createToken(accessParams);

        var refreshParams = tokenParametersFactory.refreshTokenParameters();
        refreshParams.setIdent(ident);
        var refresh = tokenCreator.createToken(refreshParams);

        var response = new TokenPairDto();

        response.setAccess(access);
        response.setRefresh(refresh);

        return response;
    }

    @Override
    public TokenPairDto getTokenPairByCreds(CredentialsDto credentials) {
        return null;
    }

    @Override
    public TokenPairDto getTokenPairByRefresh(String refreshToken) {
        var isVerificated = jwtExtractor.verify(refreshToken);

        if (!isVerificated) {
            throw new TokenNotValidException();
        }

        var ident = jwtExtractor.getSubject(refreshToken)
                .orElseThrow(TokenNotValidException::new);

        var accessParams = tokenParametersFactory.accessTokenParameters();
        accessParams.setIdent(ident);
        var access = tokenCreator.createToken(accessParams);

        var refreshParams = tokenParametersFactory.refreshTokenParameters();
        refreshParams.setIdent(ident);
        var refresh = tokenCreator.createToken(refreshParams);

        var response = new TokenPairDto();

        response.setAccess(access);
        response.setRefresh(refresh);

        return response;
    }
}
