package ul.ulstu.tamada.configuration.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    public JwtServiceConfiguration(
            IJwtExtractor jwtExtractor,
            ITokenParametersFactory customerTokenParametersFactory,
            ITokenParametersFactory adminTokenParametersFactory,
            ITokenCreator tokenCreator
    ) {
        this.jwtExtractor = jwtExtractor;
        this.customerTokenParametersFactory = customerTokenParametersFactory;
        this.adminTokenParametersFactory = adminTokenParametersFactory;
        this.tokenCreator = tokenCreator;
    }

    @Bean
    public IJwtService customerJwtService() {
        return JwtService.builder()
                .jwtExtractor(jwtExtractor)
                .tokenCreator(tokenCreator)
                .tokenParametersFactory(customerTokenParametersFactory)
                .build();
    }

    @Bean
    public IJwtService adminJwtService() {
        return JwtService.builder()
                .jwtExtractor(jwtExtractor)
                .tokenCreator(tokenCreator)
                .tokenParametersFactory(adminTokenParametersFactory)
                .build();
    }
}
