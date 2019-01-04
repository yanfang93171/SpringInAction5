package tacos.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tacos.model.Ingredient;
import tacos.repository.IngredientRepository;
import tacos.resources.IngredientResource;
import tacos.resources.IngredientResourceAssembler;

@RestController
@RequestMapping(path = "/ingredientsx", produces = "application/json")
@CrossOrigin(origins = "*")
public class IngredientController {

	private IngredientRepository repo;

	@Autowired
	public IngredientController(IngredientRepository repo) {
		this.repo = repo;
	}

	@GetMapping
	public Resources<IngredientResource> allIngredients() {
		Iterable<Ingredient> ingredients = this.repo.findAll();
		IngredientResourceAssembler assembler = new IngredientResourceAssembler();

		List<IngredientResource> ingreRes = assembler.toResources(ingredients);

		Resources<IngredientResource> resources = new Resources<IngredientResource>(ingreRes);

		resources.add(linkTo(methodOn(IngredientController.class).allIngredients()).withRel("allingredients"));
		return resources;
	}

}
