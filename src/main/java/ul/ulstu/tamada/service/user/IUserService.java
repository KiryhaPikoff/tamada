package ul.ulstu.tamada.service.user;

import ul.ulstu.tamada.rest.dto.CredentialsDto;

public interface IUserService {

    boolean verifyCredentials(CredentialsDto credentials);
}
