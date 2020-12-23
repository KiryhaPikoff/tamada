package ul.ulstu.tamada.service;

import ul.ulstu.tamada.rest.dto.auth.*;

public interface IAuthenticationFacade {

    RegistrationResponse register(RegistrationRequest registrationRequest);

    TokenPairDto checkCode(CheckOtpDto checkOtpDto);

    TokenPairDto getTokenPair(CredentialsDto credentials);

    TokenPairDto getTokenPairByRefresh(String refreshToken);
}
