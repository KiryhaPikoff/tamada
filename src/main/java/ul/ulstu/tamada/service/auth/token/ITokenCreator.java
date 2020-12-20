package ul.ulstu.tamada.service.auth.token;

import ul.ulstu.tamada.configuration.parameters.TokenParameters;

public interface ITokenCreator {

    String createToken(TokenParameters parameters);
}
