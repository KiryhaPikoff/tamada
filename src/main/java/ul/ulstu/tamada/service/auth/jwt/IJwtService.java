package ul.ulstu.tamada.service.auth.jwt;

import ul.ulstu.tamada.rest.dto.CredentialsDto;
import ul.ulstu.tamada.rest.dto.TokenPairDto;

public interface IJwtService {

    TokenPairDto getTokenPairByIdent(String ident);

    TokenPairDto getTokenPairByCreds(CredentialsDto credentials);

    TokenPairDto getTokenPairByRefresh(String refreshToken);
}
