package ul.ulstu.tamada.configuration.logging;


import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ul.ulstu.tamada.configuration.logging.httpcachedbody.CachedBodyHttpServletRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

@Order(1)
@Log4j2
@Component
public class RequestLoggingInterceptor implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var wrappedRequest = new CachedBodyHttpServletRequest((HttpServletRequest) servletRequest);

        var contentType = wrappedRequest.getHeader("Content-type");
        var isJsonOrText = contentType != null && (contentType.contains("json") || contentType.contains("text"));

        var headers = new StringBuilder();
        wrappedRequest.getHeaderNames().asIterator()
                .forEachRemaining(
                        headerName -> headers.append(
                                String.format("%s : %s\n", headerName, wrappedRequest.getHeader(headerName))
                        )
                );

        log.info(System.lineSeparator() +
                "[" + wrappedRequest.getMethod() + "]" + wrappedRequest.getRequestURI() + System.lineSeparator() +
                "[HEADERS]" + System.lineSeparator() + headers +
                (isJsonOrText ? "[REQUEST_BODY]" + wrappedRequest.getReader().lines().collect(Collectors.joining()) : ""));

        filterChain.doFilter(wrappedRequest, servletResponse);
    }
}
