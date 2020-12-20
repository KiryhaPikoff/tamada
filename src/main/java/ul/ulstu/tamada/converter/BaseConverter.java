package ul.ulstu.tamada.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
public abstract class BaseConverter<S, R> implements Converter<S, R> {

    @Autowired
    private ConversionService conversionService;

    @PostConstruct
    private void register() {
        if (conversionService instanceof GenericConversionService) {
            ((GenericConversionService) conversionService).addConverter(this);
        }
    }
}
