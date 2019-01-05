package tacos.resources;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import tacos.model.Ingredient;
import tacos.model.Ingredient.Type;

public class IngredientResource extends ResourceSupport {
	@Getter
	private String name;
	@Getter
	private Type type;

	public IngredientResource(Ingredient ingredient) {
		this.name = ingredient.getName();
		this.type = ingredient.getType();
	}
}
