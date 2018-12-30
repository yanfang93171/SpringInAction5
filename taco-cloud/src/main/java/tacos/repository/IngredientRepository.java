package tacos.repository;

import tacos.model.Ingredient;

public interface IngredientRepository {

	Iterable<Ingredient> findAll();

	Ingredient findOne(String id);

	Ingredient save(Ingredient ingredient);
}
