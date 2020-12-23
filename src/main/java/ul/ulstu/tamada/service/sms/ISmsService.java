package ul.ulstu.tamada.service.sms;

import ul.ulstu.tamada.rest.dto.auth.SmsDto;

public interface ISmsService {

    void sendSms(SmsDto smsDto);
}
