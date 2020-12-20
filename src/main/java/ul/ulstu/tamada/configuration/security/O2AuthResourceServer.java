package ul.ulstu.tamada.configuration.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class O2AuthResourceServer extends WebSecurityConfigurerAdapter {

    private final JwtDecoder jwtDecoder;

    public O2AuthResourceServer(
            @Qualifier("jwtDecoder") JwtDecoder jwtDecoder
    ) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .oauth2ResourceServer(oauth2ResourceServer ->
                oauth2ResourceServer
                    .jwt(jwt ->
                        jwt.decoder(jwtDecoder)
                )
            ).csrf().disable();
    }
}
