package ul.ulstu.tamada.service.sms;

import org.springframework.stereotype.Service;
import ul.ulstu.tamada.client.ISmscClient;
import ul.ulstu.tamada.rest.dto.SmsDto;

@Service
public class SmscService implements ISmsService {

    private final ISmscClient smscClient;

    public SmscService(ISmscClient smscClient) {
        this.smscClient = smscClient;
    }

    @Override
    public void sendSms(SmsDto smsDto) {
        smscClient.sendSms(smsDto);
    }
}
