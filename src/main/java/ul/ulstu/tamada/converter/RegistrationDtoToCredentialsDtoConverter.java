package ul.ulstu.tamada.converter;

import org.springframework.stereotype.Component;
import ul.ulstu.tamada.configuration.enums.UserRole;
import ul.ulstu.tamada.rest.dto.CredentialsDto;
import ul.ulstu.tamada.rest.dto.RegistrationRequest;

@Component
public class RegistrationDtoToCredentialsDtoConverter extends BaseConverter<RegistrationRequest, CredentialsDto> {

    @Override
    public CredentialsDto convert(RegistrationRequest registrationRequest) {
        var credentials = new CredentialsDto();

        credentials.setLogin(registrationRequest.getPhone());
        credentials.setPassword(registrationRequest.getPassword());
        credentials.setRole(UserRole.CUSTOMER);

        return credentials;
    }
}
