package ul.ulstu.tamada.configuration.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "fileservice")
public class FileServiceProperties {

    private String filesStoragePath;

    @Value("#{'${fileservice.allowed-file-types}'.split(',')}")
    private List<String> allowedFileTypes;
}
