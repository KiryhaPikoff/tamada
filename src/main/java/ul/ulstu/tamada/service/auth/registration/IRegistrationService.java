package ul.ulstu.tamada.service.auth.registration;

import ul.ulstu.tamada.rest.dto.auth.RegistrationRequest;
import ul.ulstu.tamada.rest.dto.auth.RegistrationResponse;

public interface IRegistrationService {

    RegistrationResponse register(RegistrationRequest registrationRequest);
}
