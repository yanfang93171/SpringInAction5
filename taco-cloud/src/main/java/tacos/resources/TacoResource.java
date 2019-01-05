package tacos.resources;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import tacos.model.Taco;

public class TacoResource extends ResourceSupport {
	private static final IngredientResourceAssembler assembler = new IngredientResourceAssembler();
	@Getter
	private final String name;
	@Getter
	private final Date createdAt;
	@Getter
	private final List<IngredientResource> ingredients;

	public TacoResource(Taco taco) {
		this.name = taco.getName();
		this.createdAt = taco.getCreatedAt();
		this.ingredients = assembler.toResources(taco.getIngredients());
	}
}
