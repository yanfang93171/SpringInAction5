package tacos.resources;

import org.springframework.hateoas.ResourceSupport;

import org.springframework.hateoas.core.Relation;

import lombok.Getter;
import tacos.model.Ingredient;
import tacos.model.Ingredient.Type;

@Relation(value = "ingredient", collectionRelation = "ingredients")
public class IngredientResource extends ResourceSupport {

	@Getter
	private final String name;

	@Getter
	private final Type type;

	public IngredientResource(Ingredient ingredient) {
		this.name = ingredient.getName();
		this.type = ingredient.getType();
	}
}
