package ul.ulstu.tamada.rest.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ul.ulstu.tamada.rest.dto.auth.*;
import ul.ulstu.tamada.service.auth.IAuthenticationFacade;

import javax.validation.Valid;

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
            @RequestBody @Valid RegistrationRequest registrationRequest
    ) {
        var response = authenticationFacade.register(registrationRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/check-code")
    @ApiOperation("Проверка ОТП кода с получением пары токенов")
    public ResponseEntity<TokenPairDto> checkCode(
            @RequestBody @Valid CheckOtpDto checkOtpDto
    ) {
        var tokens = authenticationFacade.checkCode(checkOtpDto);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/tokens")
    @ApiOperation("Получение пары токенов по логину/паролю с указанием необходимой роли")
    public ResponseEntity<TokenPairDto> getTokensByCreds(
            @RequestBody @Valid CredentialsDto credentialsDto
    ) {
        var tokens = authenticationFacade.getTokenPair(credentialsDto);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refresh")
    @ApiOperation("Получение пары токенов по refresh токену")
    public ResponseEntity<TokenPairDto> getTokensByRefresh(
            @RequestBody @Valid RefreshTokenDto refreshTokenDto
    ) {
        var refreshToken = refreshTokenDto.getRefresh();
        var tokens = authenticationFacade.getTokenPairByRefresh(refreshToken);
        return ResponseEntity.ok(tokens);
    }
}
