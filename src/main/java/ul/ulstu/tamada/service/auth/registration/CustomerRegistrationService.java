package ul.ulstu.tamada.service.auth.registration;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ul.ulstu.tamada.exception.UserAlreadyExistsException;
import ul.ulstu.tamada.model.Customer;
import ul.ulstu.tamada.repository.IUserRepository;
import ul.ulstu.tamada.rest.dto.RegistrationRequest;
import ul.ulstu.tamada.rest.dto.RegistrationResponse;
import ul.ulstu.tamada.rest.dto.SmsDto;
import ul.ulstu.tamada.service.auth.otp.IOtpService;
import ul.ulstu.tamada.service.sms.ISmsService;

@Service
public class CustomerRegistrationService implements IRegistrationService {

    private final IUserRepository userRepository;
    private final ConversionService conversionService;
    private final IOtpService otpService;
    private final ISmsService smsService;

    private final static String MESSAGE_TEMPLATE = "Ваш код для регистрации в Tamada: %s";

    public CustomerRegistrationService(
            IUserRepository userRepository,
            ConversionService conversionService,
            IOtpService otpService,
            ISmsService smsService) {
        this.userRepository = userRepository;
        this.conversionService = conversionService;
        this.otpService = otpService;
        this.smsService = smsService;
    }

    @Override
    @Transactional
    public RegistrationResponse register(RegistrationRequest registrationRequest) {
        var customer = conversionService.convert(registrationRequest, Customer.class);

        if (customer.isAlreadyExists(userRepository)) {
            throw new UserAlreadyExistsException(registrationRequest.getPhone());
        }

        userRepository.save(customer);

        var otp = otpService.getOtp(registrationRequest.getPhone());

        var smsDto = new SmsDto();
        smsDto.setPhone(registrationRequest.getPhone());
        smsDto.setMessage(String.format(MESSAGE_TEMPLATE, otp.getCode()));

        smsService.sendSms(smsDto);

        var response = new RegistrationResponse();
        response.setCodeId(otp.getOtpId());
        response.setPhone(registrationRequest.getPhone());

        return response;
    }
}
