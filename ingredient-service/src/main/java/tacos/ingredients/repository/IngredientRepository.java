package tacos.ingredients.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import tacos.ingredients.domain.Ingredient;

@CrossOrigin(origins = "*")
public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
