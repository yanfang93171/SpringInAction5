package tacos.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.model.Ingredient;
import tacos.model.Ingredient.Type;
import tacos.model.Order;
import tacos.model.Taco;
import tacos.repository.IngredientRepository;
import tacos.repository.TacoRepository;;

@Controller
@RequestMapping("/design")
@Slf4j
@SessionAttributes("order")
public class DesignTacoController {

	private final IngredientRepository ingredientRepo;
	private final TacoRepository tacoRepo;

	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepo) {
		this.ingredientRepo = ingredientRepo;
		this.tacoRepo = tacoRepo;
	}

	@ModelAttribute
	public void addIngredientsToMode(Model model) {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredientRepo.findAll().forEach(ingredient -> ingredients.add(ingredient));
		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}
	}

	@ModelAttribute(name = "order")
	public Order order() {
		return new Order();
	}

	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}

	@GetMapping
	public String showDesignForm(Model model) {
		return "design";
	}

	// This @ModalAttribute Order -- indicate that its value should come from
	// model and that Spring MVC shouldn't attemp to bind request parameters to
	// it
	@PostMapping
	public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {

		if (errors.hasErrors()) {
			log.info("errors" + errors);
			return "design";
		}

		log.info("Processing design:" + design);
		Taco saved = tacoRepo.save(design);
		order.addDesign(saved);
		return "redirect:/orders/current";
	}

	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		// TODO Auto-generated method stub
		return ingredients.stream().filter(ingredient -> ingredient.getType().equals(type))
				.collect(Collectors.toList());

	}
}
