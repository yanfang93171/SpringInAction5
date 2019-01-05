package tacos;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;
import tacos.client.service.TacoCloudClient;
import tacos.model.Ingredient;

@SpringBootApplication
@Slf4j
public class TacoCloudRestClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoCloudRestClientApplication.class, args);
	}

	@Bean
	public CommandLineRunner fetchIngredients(TacoCloudClient client) {
		return args -> {
			log.info("---------------GET INGREDIENTS------------");
			log.info("GETING INGREDIENT BY ID");
			log.info("Ingredient:" + client.getIngredientById("CHED"));
			log.info("GETING ALL INGREDIENTS");
			List<Ingredient> ingredients = client.getAllIngredients();
			log.info("All ingredients:");
			for (Ingredient ingredient : ingredients) {
				log.info("   -" + ingredient);
			}
		};
	}

	@Bean
	public CommandLineRunner putAnIngredient(TacoCloudClient client) {
		return args -> {
			log.info("----------------------- PUT -------------------------");
			Ingredient before = client.getIngredientById("LETC");
			log.info("BEFORE: " + before);
			client.updateIngredient(new Ingredient("LETC", "Shredded Lettuce", Ingredient.Type.VEGGIES));
			Ingredient after = client.getIngredientById("LETC");
			log.info("AFTER : " + after);
		};
	}

	@Bean
	public CommandLineRunner addAnIngredient(TacoCloudClient tacoCloudClient) {
		return args -> {
			log.info("----------------------- POST -------------------------");
			Ingredient chix = new Ingredient("CHIX", "Shredded Chicken", Ingredient.Type.PROTEIN);
			Ingredient chixAfter = tacoCloudClient.createIngredient(chix);
			log.info("AFTER=1:  " + chixAfter);
		};
	}

	@Bean
	public CommandLineRunner deleteAnIngredient(TacoCloudClient client) {
		return args -> {
			log.info("----------------------- DELETE -------------------------");
			Ingredient beffFajita = new Ingredient("BFFJ", "Beef Fajita", Ingredient.Type.PROTEIN);
			client.createIngredient(beffFajita);
			Ingredient shrimp = new Ingredient("SHMP", "Shrimp", Ingredient.Type.PROTEIN);
			client.createIngredient(shrimp);

			Ingredient before = client.getIngredientById("CHIX");
			log.info("BEFORE:  " + before);
			client.deleteIngredient(before);
			Ingredient after = client.getIngredientById("CHIX");
			log.info("AFTER:  " + after);
			before = client.getIngredientById("BFFJ");
			log.info("BEFORE:  " + before);
			client.deleteIngredient(before);
			after = client.getIngredientById("BFFJ");
			log.info("AFTER:  " + after);
			before = client.getIngredientById("SHMP");
			log.info("BEFORE:  " + before);
			client.deleteIngredient(before);
			after = client.getIngredientById("SHMP");
			log.info("AFTER:  " + after);
		};
	}

}
