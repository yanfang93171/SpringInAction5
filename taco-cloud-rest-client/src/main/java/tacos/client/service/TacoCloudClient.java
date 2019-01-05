package tacos.client.service;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import tacos.model.Ingredient;
import tacos.model.Taco;

@Service
@Slf4j
public class TacoCloudClient {

	private static final String PATH_BY_ID = "http://localhost:9090/ingredients/{id}";
	private static final String PATH = "http://localhost:9090/ingredients/";
	private Traverson traverson;
	private RestTemplate rest;

	@Autowired
	public TacoCloudClient(Traverson traverson, RestTemplate rest) {

		this.traverson = traverson;
		this.rest = rest;
	}

	public void deleteIngredient(Ingredient ingredient) {
		rest.delete("http://localhost:9090/ingredients/{id}", ingredient.getId());
	}

	public Iterable<Ingredient> getAllIngredients() {
		ParameterizedTypeReference<Resources<Ingredient>> ingredientType = new ParameterizedTypeReference<Resources<Ingredient>>() {
		};
		Resources<Ingredient> ingredientRes = traverson.follow("ingredients").toObject(ingredientType);
		Collection<Ingredient> ingredients = ingredientRes.getContent();
		return ingredients;
	}

	public Ingredient addIngredient(Ingredient ingredient) {
		String ingredientsUrl = traverson.follow("ingredients").asLink().getHref();
		return rest.postForObject(ingredientsUrl, ingredient, Ingredient.class);
	}

	public Iterable<Taco> getRecentTacos() {
		ParameterizedTypeReference<Resources<Taco>> tacoType = new ParameterizedTypeReference<Resources<Taco>>() {
		};

		Resources<Taco> tacoRes = traverson.follow("tacos").follow("recents").toObject(tacoType);
		return tacoRes.getContent();
	}

}
