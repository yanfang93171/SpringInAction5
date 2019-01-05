package tacos.client.config;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public Traverson traverson() {
		Traverson traverson = new Traverson(URI.create("http://localhost:9090/api"), MediaTypes.HAL_JSON);
		return traverson;
	}

}
