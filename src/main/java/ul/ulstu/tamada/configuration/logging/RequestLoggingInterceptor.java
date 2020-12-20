package ul.ulstu.tamada.configuration.logging;


import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

@Log4j2
@Component
public class RequestLoggingInterceptor extends HandlerInterceptorAdapter {

    @SneakyThrows
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        var contentType = request.getHeader("Content-type");
        var isJsonOrText = contentType != null && (contentType.contains("json") || contentType.contains("text"));

        var headers = new StringBuilder();
        request.getHeaderNames().asIterator()
                .forEachRemaining(
                        headerName -> headers.append(
                                String.format("%s : %s\n", headerName, request.getHeader(headerName))
                        )
                );

        log.info(System.lineSeparator() +
                "[" + request.getMethod() + "]" + request.getRequestURI() + System.lineSeparator() +
                "[HEADERS]" + System.lineSeparator() + headers +
                (isJsonOrText ? "[REQUEST_BODY]" + request.getReader().lines().collect(Collectors.joining()) : ""));

        return true;
    }

    @SneakyThrows
    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {
    }
}
