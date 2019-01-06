package tacos.ingredientclient.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import tacos.ingredientclient.domain.Ingredient;

@Profile("feign")
@FeignClient("ingredient-service")
public interface IngredientServiceClient {

	@GetMapping("/ingredients/{id}")
	Ingredient getIngredient(@PathVariable("id") String id);

	@GetMapping("/ingredients")
	Iterable<Ingredient> getAllIngredients();
}
