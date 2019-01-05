package tacos.resources;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import lombok.Getter;
import tacos.model.Taco;

@Relation(value = "taco", collectionRelation = "tacos")
public class TacoResource extends ResourceSupport {
	@Getter
	private final String name;

	private static final IngredientResourceAssembler ingredientAssember = new IngredientResourceAssembler();
	@Getter
	private final Date createdAt;

	@Getter
	private final List<IngredientResource> ingredients;

	public TacoResource(Taco taco) {
		this.name = taco.getName();
		this.createdAt = taco.getCreatedAt();

		this.ingredients = ingredientAssember.toResources(taco.getIngredients());

	}
}
