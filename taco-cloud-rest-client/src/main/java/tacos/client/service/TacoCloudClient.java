package tacos.client.service;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import tacos.model.Ingredient;

@Service
@Slf4j
public class TacoCloudClient {

	private static final String PATH_BY_ID = "http://localhost:9090/ingredients/{id}";
	private static final String PATH = "http://localhost:9090/ingredients/";
	private RestTemplate restTemplate;

	@Autowired
	public TacoCloudClient(RestTemplate restTemplate) {

		this.restTemplate = restTemplate;
	}

	public Ingredient getIngredientById(String ingredientId) {
		// 1st method
		 return this.restTemplate.getForObject(PATH_BY_ID,
		 Ingredient.class,ingredientId);
		// 2st method
		// Map<String,String> urlVariables = new HashMap<String,String>();
		// urlVariables.put("ingredientId", ingredientId);
		// return this.restTemplate.getForObject(PATH_BY_ID,
		// Ingredient.class,urlVariables);
		// 3th method
//		Map<String, String> urlVariables = new HashMap<String, String>();
//		urlVariables.put("ingredientId", ingredientId);
//		URI url = UriComponentsBuilder.fromHttpUrl(PATH_BY_ID).build(urlVariables);
//		return this.restTemplate.getForObject(url, Ingredient.class);
	}

	public List<Ingredient> getAllIngredients() {
		return this.restTemplate
				.exchange(PATH, HttpMethod.GET, null, new ParameterizedTypeReference<List<Ingredient>>() {
				}).getBody();
	}

	public Ingredient getIngredientEntityById(String ingredientId) {
		ResponseEntity<Ingredient> entity = this.restTemplate.getForEntity(PATH_BY_ID, Ingredient.class, ingredientId);
		log.info("Fetch time: " + entity.getHeaders().getDate());
		return entity.getBody();
	}

	public void updateIngredient(Ingredient ingredient) {
		this.restTemplate.put(PATH_BY_ID, ingredient, ingredient.getId());
	}

	public void deleteIngredient(Ingredient ingredient) {
		this.restTemplate.delete(PATH_BY_ID, ingredient.getId());
	}

	public Ingredient createIngredient(Ingredient ingredient) {
		return this.restTemplate.postForObject(PATH, ingredient, Ingredient.class);

	}

	public URI createIngredientAndReturnURI(Ingredient ingredient) {
		return this.restTemplate.postForLocation(PATH, ingredient);
	}

	public Ingredient createIngredientAndReturnEntity(Ingredient ingredient) {
		ResponseEntity<Ingredient> responseEntity = this.restTemplate.postForEntity(PATH, ingredient, Ingredient.class);

		log.info("New resource created at " + responseEntity.getHeaders().getLocation());
		return responseEntity.getBody();
	}

}
