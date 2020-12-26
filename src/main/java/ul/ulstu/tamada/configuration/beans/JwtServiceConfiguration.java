package ul.ulstu.tamada.configuration.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ul.ulstu.tamada.repository.IRefreshTokenRepository;
import ul.ulstu.tamada.repository.IUserRepository;
import ul.ulstu.tamada.service.auth.jwt.IJwtService;
import ul.ulstu.tamada.service.auth.jwt.JwtService;
import ul.ulstu.tamada.service.auth.jwtextractor.IJwtExtractor;
import ul.ulstu.tamada.service.auth.token.ITokenCreator;

@Configuration
public class JwtServiceConfiguration {

    private final IJwtExtractor jwtExtractor;
    private final ITokenParametersFactory customerTokenParametersFactory;
    private final ITokenParametersFactory adminTokenParametersFactory;
    private final ITokenCreator tokenCreator;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConversionService conversionService;
    private final IRefreshTokenRepository refreshTokenRepository;

    public JwtServiceConfiguration(
            IJwtExtractor jwtExtractor,
            ITokenParametersFactory customerTokenParametersFactory,
            ITokenParametersFactory adminTokenParametersFactory,
            ITokenCreator tokenCreator,
            IUserRepository userRepository,
            PasswordEncoder passwordEncoder,
            ConversionService conversionService,
            IRefreshTokenRepository refreshTokenRepository) {
        this.jwtExtractor = jwtExtractor;
        this.customerTokenParametersFactory = customerTokenParametersFactory;
        this.adminTokenParametersFactory = adminTokenParametersFactory;
        this.tokenCreator = tokenCreator;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.conversionService = conversionService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Bean
    public IJwtService customerJwtService() {
        return JwtService.builder()
                .jwtExtractor(jwtExtractor)
                .tokenCreator(tokenCreator)
                .tokenParametersFactory(customerTokenParametersFactory)
                .userRepository(userRepository)
                .passwordEncoder(passwordEncoder)
                .conversionService(conversionService)
                .refreshTokenRepository(refreshTokenRepository)
                .build();
    }

    @Bean
    public IJwtService adminJwtService() {
        return JwtService.builder()
                .jwtExtractor(jwtExtractor)
                .tokenCreator(tokenCreator)
                .tokenParametersFactory(adminTokenParametersFactory)
                .userRepository(userRepository)
                .passwordEncoder(passwordEncoder)
                .conversionService(conversionService)
                .refreshTokenRepository(refreshTokenRepository)
                .build();
    }
}
