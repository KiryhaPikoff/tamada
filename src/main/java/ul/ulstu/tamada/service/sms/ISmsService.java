package ul.ulstu.tamada.service.sms;

import ul.ulstu.tamada.rest.dto.SmsDto;

public interface ISmsService {

    void sendSms(SmsDto smsDto);
}
