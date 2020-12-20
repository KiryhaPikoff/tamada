package ul.ulstu.tamada.configuration.beans;

import ul.ulstu.tamada.configuration.parameters.TokenParameters;

public interface ITokenParametersFactory {

    TokenParameters accessTokenParameters();
    TokenParameters refreshTokenParameters();
}
