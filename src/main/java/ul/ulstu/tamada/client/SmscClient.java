package ul.ulstu.tamada.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ul.ulstu.tamada.configuration.properties.SmsServiceProperties;
import ul.ulstu.tamada.rest.dto.SmsDto;

import java.util.HashMap;

@Component
public class SmscClient implements ISmscClient {

    private final SmsServiceProperties smsServiceProperties;
    private final RestTemplate restTemplate;

    public SmscClient(
            SmsServiceProperties smsServiceProperties,
            @Qualifier("smscRestTemplate") RestTemplate restTemplate) {
        this.smsServiceProperties = smsServiceProperties;
        this.restTemplate = restTemplate;
    }

    @Override
    public void sendSms(SmsDto smsDto) {
        HashMap<String, String> requestMap = new HashMap<>();

        requestMap.put("phone", smsDto.getPhone());
        requestMap.put("message", smsDto.getMessage());

        restTemplate.postForObject(smsServiceProperties.getSmscApiUrl(), HttpEntity.EMPTY, String.class, requestMap);
    }
}
