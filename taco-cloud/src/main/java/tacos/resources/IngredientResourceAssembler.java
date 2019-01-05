package tacos.resources;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import tacos.controller.IngredientController;
import tacos.model.Ingredient;

public class IngredientResourceAssembler extends ResourceAssemblerSupport<Ingredient, IngredientResource> {

	public IngredientResourceAssembler() {
		super(IngredientController.class, IngredientResource.class);

	}

	@Override
	public IngredientResource instantiateResource(Ingredient ingredient) {
		return new IngredientResource(ingredient);
	}

	@Override
	public IngredientResource toResource(Ingredient ingredient) {
		return this.createResourceWithId(ingredient.getId(), ingredient);

	}

}
