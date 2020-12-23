package ul.ulstu.tamada.service.auth.otp;

import ul.ulstu.tamada.rest.dto.auth.CheckOtpDto;
import ul.ulstu.tamada.rest.dto.auth.OtpDto;

public interface IOtpService {

    OtpDto getOtp(String phone);

    void checkOtp(CheckOtpDto checkOtpDto);
}
