package ul.ulstu.tamada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan("ul.ulstu.tamada.configuration.properties")
public class TamadaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TamadaApplication.class, args);
    }

}
