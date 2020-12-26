package ul.ulstu.tamada.service.auth.registration;

import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ul.ulstu.tamada.exception.UserAlreadyExistsException;
import ul.ulstu.tamada.model.Customer;
import ul.ulstu.tamada.model.enums.CustomerStatus;
import ul.ulstu.tamada.repository.IUserRepository;
import ul.ulstu.tamada.rest.dto.auth.RegistrationRequest;
import ul.ulstu.tamada.rest.dto.auth.RegistrationResponse;
import ul.ulstu.tamada.rest.dto.auth.SmsDto;
import ul.ulstu.tamada.service.auth.otp.IOtpService;
import ul.ulstu.tamada.service.sms.ISmsService;

@Service
public class CustomerRegistrationService implements IRegistrationService {

    private final IUserRepository userRepository;
    private final ConversionService conversionService;
    private final IOtpService otpService;
    private final ISmsService smsService;
    private final PasswordEncoder passwordEncoder;

    public CustomerRegistrationService(
            IUserRepository userRepository,
            ConversionService conversionService,
            IOtpService otpService,
            ISmsService smsService,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.conversionService = conversionService;
        this.otpService = otpService;
        this.smsService = smsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public RegistrationResponse register(RegistrationRequest registrationRequest) {
        var customer = conversionService.convert(registrationRequest, Customer.class);

        if (customer.isAlreadyExists(userRepository)) {
            throw new UserAlreadyExistsException(registrationRequest.getPhone());
        }

        customer.setPassword(
                passwordEncoder.encode(registrationRequest.getPassword())
        );

        customer.setStatus(CustomerStatus.REGISTER_REQUEST);

        userRepository.save(customer);

        var otp = otpService.getOtp(registrationRequest.getPhone());

        var smsDto = new SmsDto();
        smsDto.setPhone(registrationRequest.getPhone());
        smsDto.setMessage(String.format("Ваш код для регистрации в Tamada: %s", otp.getCode()));

        smsService.sendSms(smsDto);

        var response = new RegistrationResponse();
        response.setCodeId(otp.getOtpId());
        response.setPhone(registrationRequest.getPhone());

        return response;
    }
}
