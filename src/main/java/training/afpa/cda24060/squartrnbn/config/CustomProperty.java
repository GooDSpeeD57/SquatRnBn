package training.afpa.cda24060.squartrnbn.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "training.afpa.cda24060.squartnbn")
@Data
public class CustomProperty {
    private String apiURL;
}