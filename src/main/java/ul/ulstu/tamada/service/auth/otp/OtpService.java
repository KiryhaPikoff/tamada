package ul.ulstu.tamada.service.auth.otp;

import org.springframework.stereotype.Service;
import ul.ulstu.tamada.rest.dto.CheckOtpDto;
import ul.ulstu.tamada.rest.dto.OtpDto;

@Service
public class OtpService implements IOtpService {

    @Override
    public OtpDto getOtp(String phone) {
        return null;
    }

    @Override
    public void checkOtp(CheckOtpDto checkOtpDto) {

    }
}
