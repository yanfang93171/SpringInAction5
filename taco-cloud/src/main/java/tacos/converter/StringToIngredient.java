package tacos.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.model.Ingredient;
import tacos.repository.IngredientRepository;

@Component
public class StringToIngredient implements Converter<String, Ingredient> {

	private IngredientRepository ingredientRepo;

	@Autowired
	public StringToIngredient(IngredientRepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}

	@Override
	public Ingredient convert(String id) {
		// TODO Auto-generated method stub
		return ingredientRepo.findOne(id);
	}

}
