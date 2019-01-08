package tacos.ingredientclient.resttemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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

	@HystrixCommand(fallbackMethod = "getDefaultIngredients",
				commandProperties={
						@HystrixProperty(
								name="execution.isolation.thread.timeoutInMilliseconds",
								value="500"
								),
						@HystrixProperty(
								name="circuitBreaker.requestVolumeThreshold",
								value="30"
								),
						@HystrixProperty(
								name="circuitBreaker.errorThresholdPercentage",
								value="25"
								),
						@HystrixProperty(
								name="metrics.rollingStats.timeInMilliseconds",
								value="2000"
								),
						@HystrixProperty(
								name="circuitBreaker.sleepWindowInMilliseconds",
								value="60000"
								)
// no latency protection
//						@HystrixPropery(
//								name="execution.timeout.enabled",
//								value="false"
//								)
				})
	public Iterable<Ingredient> getAllIngredients() {
		Ingredient[] ingredients = this.rest.getForObject(SERVICE_REGISTRY, Ingredient[].class);
		return Arrays.asList(ingredients);
	}

	private Iterable<Ingredient> getDefaultIngredients() {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredients.add(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
		ingredients.add(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
		ingredients.add(new Ingredient("CHED", "Shredded Cheddar", Ingredient.Type.CHEESE));
		return ingredients;
	}

}
