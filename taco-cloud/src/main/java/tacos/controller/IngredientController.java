package tacos.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import tacos.model.Ingredient;
import tacos.repository.IngredientRepository;

@RestController
@RequestMapping(path = "/ingredients", produces = "application/json")
@CrossOrigin(origins = "*")
@Slf4j
public class IngredientController {

	private IngredientRepository repo;

	@Autowired
	public IngredientController(IngredientRepository repo) {
		this.repo = repo;
	}

	@GetMapping
	public Iterable<Ingredient> allIngredients() {
		return this.repo.findAll();
	}

	@GetMapping("/{id}")
	public Optional<Ingredient> byId(@PathVariable String id) {
		log.info("Getting id=" + id);
		return this.repo.findById(id);
	}

	@PutMapping("/{id}")
	public void updateIngredient(@PathVariable String id, @RequestBody Ingredient req) {
		if (!req.getId().equals(id)) {
			throw new IllegalStateException("Given ingredient's ID doesn't match the ID in the path.");
		}
		this.repo.save(req);
	}

	@PostMapping
	public ResponseEntity<Ingredient> postIngredient(@RequestBody Ingredient ingredient) {
		Ingredient saved = this.repo.save(ingredient);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("http://localhost:9090/ingredients/" + saved.getId()));
		return new ResponseEntity<>(saved, headers, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public void deleteIngredient(@PathVariable String id) {
		this.repo.deleteById(id);
	}
}
