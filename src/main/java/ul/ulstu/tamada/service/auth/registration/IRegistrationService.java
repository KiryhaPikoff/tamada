package ul.ulstu.tamada.service.auth.registration;

import ul.ulstu.tamada.rest.dto.RegistrationRequest;
import ul.ulstu.tamada.rest.dto.RegistrationResponse;

public interface IRegistrationService {

    RegistrationResponse register(RegistrationRequest registrationRequest);
}
