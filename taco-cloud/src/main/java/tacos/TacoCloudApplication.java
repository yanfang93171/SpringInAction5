package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import tacos.model.Ingredient;
import tacos.model.Ingredient.Type;
import tacos.model.RegistrationForm;
import tacos.repository.IngredientRepository;
import tacos.repository.UserRepository;

@SpringBootApplication
public class TacoCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(IngredientRepository repo, UserRepository userRepo,
			PasswordEncoder pwdEncoder) {
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

				RegistrationForm registerform = new RegistrationForm();
				registerform.setUsername("user1");
				registerform.setPassword("user1");
				registerform.setCity("city1");
				registerform.setFullname("fullname1");
				registerform.setPhone("phone1");
				registerform.setState("state1");
				registerform.setZip("zip1");
				registerform.setStreet("street1");

				//
				userRepo.save(registerform.toUser(pwdEncoder));
			}

		};

	}

}
