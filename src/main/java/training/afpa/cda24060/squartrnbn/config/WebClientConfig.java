package training.afpa.cda24060.squartrnbn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(CustomProperty property) {
        return WebClient.builder()
                .baseUrl(property.getApiURL())
                .build();
    }
}
