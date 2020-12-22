package ul.ulstu.tamada.service.auth.otp;

import org.springframework.stereotype.Service;
import ul.ulstu.tamada.exception.WrongOtpCodeException;
import ul.ulstu.tamada.rest.dto.CheckOtpDto;
import ul.ulstu.tamada.rest.dto.OtpDto;

@Service
public class OtpService implements IOtpService {

    @Override
    public OtpDto getOtp(String phone) {
        var otp = new OtpDto();
        otp.setCode("1111");
        otp.setOtpId(555L);
        return otp;
    }

    @Override
    public void checkOtp(CheckOtpDto checkOtpDto) {
        if (checkOtpDto.getCode().equals("1111")) {
            return;
        }

        throw new WrongOtpCodeException();
    }
}
