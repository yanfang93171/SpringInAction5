package tacos.ingredients.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tacos.ingredients.domain.Ingredient;
import tacos.ingredients.repository.IngredientRepository;

@RestController
@RequestMapping(path = "/ingredients", produces = "application/json")
public class IngredientController {

	private IngredientRepository repo;

	private Environment env;

	@Autowired
	public IngredientController(IngredientRepository repo, Environment env) {
		this.repo = repo;
		this.env = env;
	}

	@GetMapping
	public Iterable<Ingredient> allIngredients() {
		return this.repo.findAll();
	}

	@GetMapping("/{id}")
	public Optional<Ingredient> byId(@PathVariable String id) {
		return this.repo.findById(id);
	}

	@PutMapping("/{id}")
	public void updateIngredient(@PathVariable String id, @RequestBody Ingredient ingredient) {
		if (!ingredient.getId().equals(id)) {
			throw new IllegalStateException("Given ingredient's ID doesn't match the ID in the path.");
		}
		this.repo.save(ingredient);
	}

	@PostMapping
	public ResponseEntity<Ingredient> postIngredient(@RequestBody Ingredient ingredient) {
		Ingredient saved = this.repo.save(ingredient);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("http://localhost:" +env.getProperty("server.port")+ "/ingredients/" + saved.getId()));
		return new ResponseEntity<>(saved, headers, HttpStatus.CREATED);
	}

}
