package ul.ulstu.tamada.service.auth.otp;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ul.ulstu.tamada.exception.NoAvailableCheckOtpAttemptsException;
import ul.ulstu.tamada.exception.OtpNotFoundException;
import ul.ulstu.tamada.exception.WrongOtpCodeException;
import ul.ulstu.tamada.model.Otp;
import ul.ulstu.tamada.repository.IOtpRepository;
import ul.ulstu.tamada.rest.dto.auth.CheckOtpDto;
import ul.ulstu.tamada.rest.dto.auth.OtpDto;
import ul.ulstu.tamada.service.auth.otp.generator.IOtpGenerator;

import java.time.Duration;

@Service
public class OtpService implements IOtpService {

    private final IOtpGenerator otpGenerator;
    private final IOtpRepository otpRepository;

    private static final Integer ATTEMPTS = 5;

    public OtpService(IOtpGenerator otpGenerator,
                      IOtpRepository otpRepository) {
        this.otpGenerator = otpGenerator;
        this.otpRepository = otpRepository;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public OtpDto getOtp(String phone) {
        var code = otpGenerator.generateOtp();

        var otp = new Otp();
        otp.setAvailableAttempts(ATTEMPTS);
        otp.setCode(code);
        otp.setPhone(phone);

        otpRepository.saveAndFlush(otp);

        var otpDto = new OtpDto();
        otpDto.setCode(code);
        otpDto.setOtpId(otp.getId());
        return otpDto;
    }

    @Override
    public void checkOtp(CheckOtpDto checkOtpDto) {
        var otp = otpRepository.findById(checkOtpDto.getCodeId())
                .orElseThrow(OtpNotFoundException::new);

        if (otp.getAvailableAttempts() == 0) {
            throw new NoAvailableCheckOtpAttemptsException();
        }

        if (!otp.getCode().equals(checkOtpDto.getCode())) {
            otp.setAvailableAttempts(
                    otp.getAvailableAttempts() - 1
            );
            otpRepository.saveAndFlush(otp);
            throw new WrongOtpCodeException();
        }
    }
}
