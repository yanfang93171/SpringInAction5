package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;
import tacos.client.service.TacoCloudClient;
import tacos.model.Ingredient;
import tacos.model.Taco;

@SpringBootApplication
@Slf4j
public class TacoCloudRestClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoCloudRestClientApplication.class, args);
	}

	@Bean
	public CommandLineRunner traversonGetIngredients(TacoCloudClient client) {
		return args -> {
			Iterable<Ingredient> ingredients = client.getAllIngredients();
			log.info("----------------------- GET INGREDIENTS WITH TRAVERSON -------------------------");
			for (Ingredient ingredient : ingredients) {
				log.info("   -  " + ingredient);
			}
		};
	}

	@Bean
	public CommandLineRunner traversonSaveIngredient(TacoCloudClient client) {
		return args -> {
			Ingredient pico = client.addIngredient(new Ingredient("PICO", "Pico de Gallo", Ingredient.Type.SAUCE));
			log.info("saved pico=" + pico);
			Iterable<Ingredient> allIngredients = client.getAllIngredients();
			log.info("----------------------- ALL INGREDIENTS AFTER SAVING PICO -------------------------");
			for (Ingredient ingredient : allIngredients) {
				log.info("   -  " + ingredient);
			}
			client.deleteIngredient(pico);
		};
	}

	@Bean
	public CommandLineRunner traversonRecentTacos(TacoCloudClient client) {
		return args -> {
			Iterable<Taco> recentTacos = client.getRecentTacos();
			log.info("----------------------- GET RECENT TACOS WITH TRAVERSON -------------------------");
			for (Taco taco : recentTacos) {
				log.info("   -  " + taco);
			}
		};
	}

}
