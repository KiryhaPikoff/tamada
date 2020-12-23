package ul.ulstu.tamada.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ul.ulstu.tamada.configuration.enums.UserRole;
import ul.ulstu.tamada.exception.TokenNotValidException;
import ul.ulstu.tamada.rest.dto.auth.*;
import ul.ulstu.tamada.service.auth.jwt.IJwtService;
import ul.ulstu.tamada.service.auth.jwtextractor.IJwtExtractor;
import ul.ulstu.tamada.service.auth.otp.IOtpService;
import ul.ulstu.tamada.service.auth.registration.IRegistrationService;

@Log4j2
@Service
public class AuthenticationFacade implements IAuthenticationFacade {

    private final IRegistrationService registrationService;
    private final IJwtService customerJwtService;
    private final IJwtService adminJwtService;
    private final ConversionService conversionService;
    private final IJwtExtractor jwtExtractor;
    private final IOtpService otpService;

    public AuthenticationFacade(
            IRegistrationService registrationService,
            IJwtService customerJwtService,
            IJwtService adminJwtService,
            ConversionService conversionService,
            IJwtExtractor jwtExtractor,
            IOtpService otpService
    ) {
        this.registrationService = registrationService;
        this.customerJwtService = customerJwtService;
        this.adminJwtService = adminJwtService;
        this.conversionService = conversionService;
        this.jwtExtractor = jwtExtractor;
        this.otpService = otpService;
    }

    @Override
    public RegistrationResponse register(RegistrationRequest registrationRequest) {
        var registrationResponse = registrationService.register(registrationRequest);
        return registrationResponse;
    }

    @Override
    public TokenPairDto checkCode(CheckOtpDto checkOtpDto) {
        otpService.checkOtp(checkOtpDto);
        var tokens = customerJwtService.getTokenPairByIdent(checkOtpDto.getPhone());
        return tokens;
    }

    @Override
    public TokenPairDto getTokenPair(CredentialsDto credentials) {
        var role = credentials.getRole();

        TokenPairDto tokens = null;

        switch (role) {
            case CUSTOMER: {
                tokens = customerJwtService.getTokenPairByCreds(credentials);
                break;
            }

            case ADMIN: {
                tokens = adminJwtService.getTokenPairByCreds(credentials);
                break;
            }
        }

        return tokens;
    }

    @Override
    public TokenPairDto getTokenPairByRefresh(String refreshToken) {
        var roleOpt = jwtExtractor.getRole(refreshToken);

        if (roleOpt.isEmpty()) {
            throw new TokenNotValidException();
        }

        var role = roleOpt.get();
        var userRole = conversionService.convert(role, UserRole.class);

        TokenPairDto tokens = null;

        switch (userRole) {
            case CUSTOMER: {
                tokens = customerJwtService.getTokenPairByRefresh(refreshToken);
                break;
            }

            case ADMIN: {
                tokens = adminJwtService.getTokenPairByRefresh(refreshToken);
                break;
            }
        }

        return tokens;
    }
}
