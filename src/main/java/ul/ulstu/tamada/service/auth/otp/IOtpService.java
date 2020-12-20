package ul.ulstu.tamada.service.auth.otp;

import ul.ulstu.tamada.rest.dto.CheckOtpDto;
import ul.ulstu.tamada.rest.dto.OtpDto;

public interface IOtpService {

    OtpDto getOtp(String phone);

    void checkOtp(CheckOtpDto checkOtpDto);
}
