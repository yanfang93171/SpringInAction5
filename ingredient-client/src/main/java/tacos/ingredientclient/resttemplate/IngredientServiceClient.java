package tacos.ingredientclient.resttemplate;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tacos.ingredientclient.domain.Ingredient;

@Service
@Conditional(NotFeignAndNotWebClientCondition.class)
public class IngredientServiceClient {

	private static final String SERVICE_REGISTRY = "http://ingredient-service/ingredients";
	private RestTemplate rest;

	@Autowired
	public IngredientServiceClient(@LoadBalanced RestTemplate rest) {

		this.rest = rest;
	}

	public Ingredient getIngredientById(String id) {
		return this.rest.getForObject(SERVICE_REGISTRY + "/{id}", Ingredient.class, id);
	}

	public Iterable<Ingredient> getAllIngredients() {
		Ingredient[] ingredients = this.rest.getForObject(SERVICE_REGISTRY, Ingredient[].class);
		return Arrays.asList(ingredients);
	}

}
