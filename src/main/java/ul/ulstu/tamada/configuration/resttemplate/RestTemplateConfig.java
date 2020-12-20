package ul.ulstu.tamada.configuration.resttemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import ul.ulstu.tamada.configuration.resttemplate.interceptor.RestTemplateLoggingInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    private final RestTemplateLoggingInterceptor restTemplateLoggingInterceptor;

    public RestTemplateConfig(
            RestTemplateLoggingInterceptor restTemplateLoggingInterceptor
    ) {
        this.restTemplateLoggingInterceptor = restTemplateLoggingInterceptor;
    }

    @Bean
    public RestTemplate smscRestTemplate() {
        var restTemplate = new RestTemplate();

        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        stringHttpMessageConverter.setWriteAcceptCharset(true);

        for (int i = 0; i < restTemplate.getMessageConverters().size(); i++) {
            if (restTemplate.getMessageConverters().get(i) instanceof StringHttpMessageConverter) {
                restTemplate.getMessageConverters().remove(i);
                restTemplate.getMessageConverters().add(i, stringHttpMessageConverter);
                break;
            }
        }

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(restTemplateLoggingInterceptor);
        restTemplate.setInterceptors(interceptors);

        var requestFactory = new HttpComponentsClientHttpRequestFactory();
        var bufferingRequestFactory = new BufferingClientHttpRequestFactory(requestFactory);

        restTemplate.setRequestFactory(bufferingRequestFactory);

        return restTemplate;
    }
}
