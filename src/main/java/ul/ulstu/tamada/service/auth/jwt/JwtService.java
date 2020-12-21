package ul.ulstu.tamada.service.auth.jwt;

import lombok.Builder;
import lombok.SneakyThrows;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ul.ulstu.tamada.configuration.beans.ITokenParametersFactory;
import ul.ulstu.tamada.exception.AuthenticationException;
import ul.ulstu.tamada.exception.TokenNotValidException;
import ul.ulstu.tamada.model.User;
import ul.ulstu.tamada.model.UserType;
import ul.ulstu.tamada.repository.IUserRepository;
import ul.ulstu.tamada.rest.dto.CredentialsDto;
import ul.ulstu.tamada.rest.dto.TokenPairDto;
import ul.ulstu.tamada.service.auth.jwtextractor.IJwtExtractor;
import ul.ulstu.tamada.service.auth.token.ITokenCreator;

import java.util.Optional;

@Builder
public class JwtService implements IJwtService {

    private final IJwtExtractor jwtExtractor;
    private final ITokenParametersFactory tokenParametersFactory;
    private final ITokenCreator tokenCreator;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConversionService conversionService;

    public JwtService(
            IJwtExtractor jwtExtractor,
            ITokenParametersFactory tokenParametersFactory,
            ITokenCreator tokenCreator,
            IUserRepository userRepository,
            PasswordEncoder passwordEncoder,
            ConversionService conversionService
    ) {
        this.jwtExtractor = jwtExtractor;
        this.tokenParametersFactory = tokenParametersFactory;
        this.tokenCreator = tokenCreator;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.conversionService = conversionService;
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
    @SneakyThrows
    public TokenPairDto getTokenPairByCreds(CredentialsDto credentials) {

        Optional<User> userOpt = userRepository
                .findByLogin(
                        credentials.getLogin(),
                        conversionService.convert(credentials.getRole(), UserType.class)
                );

        if (userOpt.isEmpty()) {
            throw new AuthenticationException();
        }

        var user = userOpt.get();
        var ident = user.getLogin();

        if (!passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            throw new AuthenticationException();
        }

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
