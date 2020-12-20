package ul.ulstu.tamada.rest.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ul.ulstu.tamada.rest.dto.CheckCodeRequest;
import ul.ulstu.tamada.rest.dto.RegistrationRequest;
import ul.ulstu.tamada.rest.dto.RegistrationResponse;
import ul.ulstu.tamada.service.IAuthenticationFacade;

@Log4j2
@RestController
@RequestMapping("${base_uri}/auth/v1")
public class AuthController {

    private final IAuthenticationFacade authenticationFacade;

    public AuthController(
            IAuthenticationFacade authenticationFacade
    ) {
        this.authenticationFacade = authenticationFacade;
    }

    @PostMapping("/register")
    @ApiOperation("Регистрация пользователя в системе")
    public ResponseEntity<RegistrationResponse> register(
            @RequestBody RegistrationRequest registrationRequest
    ) {
        var response = authenticationFacade.register(registrationRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/check-code")
    @ApiOperation("Проверка ОТП кода")
    public ResponseEntity<?> checkCode(
            @RequestBody CheckCodeRequest checkCodeRequest
    ) {
        return ResponseEntity.ok().build();
    }
}
