package ul.ulstu.tamada.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "animators")
public class AnimatorScheduleProperties {

    private Integer scheduleDaysAhead;
}
