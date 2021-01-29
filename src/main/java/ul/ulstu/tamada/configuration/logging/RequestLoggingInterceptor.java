package ul.ulstu.tamada.configuration.logging;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import ul.ulstu.tamada.configuration.logging.httpcachedbody.CachedBodyHttpServletRequest;
import ul.ulstu.tamada.service.auth.jwtextractor.IJwtExtractor;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

@Order(1)
@Log4j2
@Component
@RequiredArgsConstructor
public class RequestLoggingInterceptor implements Filter {

    private final IJwtExtractor jwtExtractor;
    private final ObjectMapper objectMapper;

    private static final String USER_ID = "user_id";
    private static final String ROLE = "role";

    private static final String ANONYMOUS = "Anonymous";

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

        var token = wrappedRequest.getHeader("Authorization");

        String userId = ANONYMOUS;
        String role = ANONYMOUS;

        if (token != null && !token.isBlank()) {
            token = token.split(" ")[1];

            userId = jwtExtractor.getSubject(token)
                    .orElse("NotParsedSub");

            role = jwtExtractor.getRole(token)
                    .orElse("NotParsedRole");
        }

        MDC.put(USER_ID, userId);
        MDC.put(ROLE, role);

        var requestBody = StreamUtils.copyToString(wrappedRequest.getInputStream(), Charset.defaultCharset());

        log.info(System.lineSeparator() +
                "[" + wrappedRequest.getMethod() + "]" + wrappedRequest.getRequestURI() + System.lineSeparator() +
                "[HEADERS]" + System.lineSeparator() + headers +
                (isJsonOrText ? "[REQUEST_BODY]" + objectMapper.writeValueAsString(requestBody) : ""));

        filterChain.doFilter(wrappedRequest, servletResponse);

        MDC.remove(USER_ID);
        MDC.remove(ROLE);
    }
}
