package ul.ulstu.tamada.service.auth.otp.generator;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Random;

@Log4j2
@Service
public class RandomOtpGenerator implements IOtpGenerator {

    @Override
    public String generateOtp() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            otp.append(new Random().nextInt(9));
        }
        log.info("Generated OTP {}", otp.toString());
        return otp.toString();
    }
}
