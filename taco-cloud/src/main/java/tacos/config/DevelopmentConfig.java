package tacos.config;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import tacos.model.Ingredient;
import tacos.model.Ingredient.Type;
import tacos.model.Taco;
import tacos.model.User;
import tacos.repository.IngredientRepository;
import tacos.repository.TacoRepository;
import tacos.repository.UserRepository;

@Profile("!prod")
@Configuration
public class DevelopmentConfig {

	@Bean
	public CommandLineRunner CommandLineRunner(IngredientRepository repo, UserRepository userRepo,
			TacoRepository tacoRepo, PasswordEncoder encoder) {
		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {
				// TODO Auto-generated method stub
				repo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
				repo.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
				repo.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
				repo.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
				repo.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
				repo.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
				repo.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
				repo.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
				repo.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
				repo.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));

				userRepo.save(new User("habuma", encoder.encode("password"), "Craig Walls", "123 North Street",
						"Cross Roads", "TX", "76227", "123-123-1234"));

				Taco taco = new Taco();
				taco.setName("taco01");
				taco.setIngredients(
						StreamSupport.stream(repo.findAll().spliterator(), false).collect(Collectors.toList()));
				tacoRepo.save(taco);
			}

		};
	}
}
