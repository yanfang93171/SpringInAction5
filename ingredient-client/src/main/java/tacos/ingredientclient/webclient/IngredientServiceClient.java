package tacos.ingredientclient.webclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.ingredientclient.domain.Ingredient;

@Service
@Profile("webclient")
@Slf4j
public class IngredientServiceClient {

	private static final String INGREIDENT_SERVICE_PATH = "http://ingredient-service/ingredients/";
	private WebClient.Builder wcBuilder;

	@Autowired
	public IngredientServiceClient(@LoadBalanced WebClient.Builder wcBuilder) {
		this.wcBuilder = wcBuilder;
	}

	public Mono<Ingredient> getIngredientById(String id) {
		return this.wcBuilder.build().get().uri(INGREIDENT_SERVICE_PATH + "/{id}", id).retrieve()
				.bodyToMono(Ingredient.class);
	}

	public Flux<Ingredient> getAllIngredients() {
		return this.wcBuilder.build().get().uri(INGREIDENT_SERVICE_PATH).retrieve().bodyToFlux(Ingredient.class);
	}
}
