package ul.ulstu.tamada.configuration.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Log4j2
@ControllerAdvice
@RequiredArgsConstructor
public class ResponseLoggingInterceptor implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object responseObject,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        var isServletHttpResponse = serverHttpResponse instanceof ServletServerHttpResponse;
        if (isServletHttpResponse) {
            try {
                var contentType = mediaType.toString();
                var isJsonOrText = contentType.contains("json") || contentType.contains("text");
                log.info(System.lineSeparator() +
                        "[RESPONSE]" + System.lineSeparator() +
                        "[CONTENT_TYPE] " + mediaType + System.lineSeparator() +
                        "[" + serverHttpRequest.getMethod() + "] " + serverHttpRequest.getURI() + System.lineSeparator() +
                        (isJsonOrText ? "[RESPONSE_BODY] " + objectMapper.writeValueAsString(responseObject) : ""));
            } catch (Exception exception) {
                log.error("Couldn't write response body, cause:", exception);
            }
        }

        return responseObject;
    }
}
