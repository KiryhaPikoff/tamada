package ul.ulstu.tamada.client;

import ul.ulstu.tamada.rest.dto.SmsDto;

public interface ISmscClient {

    void sendSms(SmsDto smsDto);
}
