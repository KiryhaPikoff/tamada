package ul.ulstu.tamada.configuration.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ul.ulstu.tamada.configuration.logging.httpcachedbody.CachedBodyHttpServletRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Order(1)
@Component
public class WrappingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        var wrappedRequest = new CachedBodyHttpServletRequest((HttpServletRequest) servletRequest);

        filterChain.doFilter(wrappedRequest, servletResponse);
    }
}
