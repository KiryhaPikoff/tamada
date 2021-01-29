package ul.ulstu.tamada.configuration.resttemplate.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Log4j2
@Component
@RequiredArgsConstructor
public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {

    private final ObjectMapper objectMapper;

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

        log.info(System.lineSeparator() + "[CLIENT_REQUEST]" + System.lineSeparator() +
                "["+ httpRequest.getMethod() + "] " + httpRequest.getURI() + System.lineSeparator() +
                "[HEADERS] "+httpRequest.getHeaders().toString() + System.lineSeparator() +
                "[BODY] " + new String(bytes, StandardCharsets.UTF_8));

        ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);

        log.info(System.lineSeparator() + "[CLIENT_RESPONSE]"+ System.lineSeparator() +
                "[HEADERS]" + response.getHeaders() + System.lineSeparator() +
                "[BODY] " + new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8));


        return response;
    }
}
