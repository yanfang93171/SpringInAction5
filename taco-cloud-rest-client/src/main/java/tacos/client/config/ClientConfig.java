package tacos.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.Traverson;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfig {

	@Bean
	public Traverson traverson() {
		return new Traverson("http://localhost:9090");
	}

}
