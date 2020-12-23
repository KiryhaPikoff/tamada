package ul.ulstu.tamada.client;

import ul.ulstu.tamada.rest.dto.auth.SmsDto;

public interface ISmscClient {

    void sendSms(SmsDto smsDto);
}
