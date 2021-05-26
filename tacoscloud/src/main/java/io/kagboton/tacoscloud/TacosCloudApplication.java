package io.kagboton.tacoscloud;

import io.kagboton.tacoscloud.domain.Ingredient;
import io.kagboton.tacoscloud.domain.Ingredient.Type;
import io.kagboton.tacoscloud.domain.Taco;
import io.kagboton.tacoscloud.domain.User;
import io.kagboton.tacoscloud.repository.IngredientRepository;
import io.kagboton.tacoscloud.repository.TacoRepository;
import io.kagboton.tacoscloud.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@SpringBootApplication
public class TacosCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacosCloudApplication.class, args);
    }


    // to avoid 404s when using Angular HTML 5 routing
    @Bean
    ErrorViewResolver supportPathBasedLocationStrategyWithoutHashes() {
        return new ErrorViewResolver() {
            @Override
            public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
                return status == HttpStatus.NOT_FOUND
                        ? new ModelAndView("index.html", Collections.<String, Object>emptyMap(), HttpStatus.OK)
                        : null;
            }
        };
    }


    // create dummy content for development purpose
    @Profile("!prod")
    @Bean
    public CommandLineRunner dataLoader(UserRepository userRepo, IngredientRepository ingredientRepo, PasswordEncoder encoder, TacoRepository tacoRepo) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

                // some ingredients
                Ingredient flourTortilla = new Ingredient("FLTO", "Flour Tortilla", Type.WRAP);
                Ingredient cornTortilla = new Ingredient("COTO", "Corn Tortilla", Type.WRAP);
                Ingredient groundBeef = new Ingredient("GRBF", "Ground Beef", Type.PROTEIN);
                Ingredient carnitas = new Ingredient("CARN", "Carnitas", Type.PROTEIN);
                Ingredient tomatoes = new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES);
                Ingredient lettuce = new Ingredient("LETC", "Lettuce", Type.VEGGIES);
                Ingredient cheddar = new Ingredient("CHED", "Cheddar", Type.CHEESE);
                Ingredient jack = new Ingredient("JACK", "Monterrey Jack", Type.CHEESE);
                Ingredient salsa = new Ingredient("SLSA", "Salsa", Type.SAUCE);
                Ingredient sourCream = new Ingredient("SRCR", "Sour Cream", Type.SAUCE);
                ingredientRepo.save(flourTortilla);
                ingredientRepo.save(cornTortilla);
                ingredientRepo.save(groundBeef);
                ingredientRepo.save(carnitas);
                ingredientRepo.save(tomatoes);
                ingredientRepo.save(lettuce);
                ingredientRepo.save(cheddar);
                ingredientRepo.save(jack);
                ingredientRepo.save(salsa);
                ingredientRepo.save(sourCream);

                // our development user
                userRepo.save(new User("toto", encoder.encode("toto"), "Craig Walls", "123 North Street", "Cross Roads", "TX",
                        "76227", "123-123-1234"));

                // our created tacos
                Taco taco1 = new Taco();
                taco1.setName("Carnivore");
                taco1.setIngredients(Arrays.asList(flourTortilla, groundBeef, carnitas, sourCream, salsa, cheddar));
                tacoRepo.save(taco1);

                Taco taco2 = new Taco();
                taco2.setName("Bovine Bounty");
                taco2.setIngredients(Arrays.asList(cornTortilla, groundBeef, cheddar, jack, sourCream));
                tacoRepo.save(taco2);

                Taco taco3 = new Taco();
                taco3.setName("Veg-Out");
                taco3.setIngredients(Arrays.asList(flourTortilla, cornTortilla, tomatoes, lettuce, salsa));
                tacoRepo.save(taco3);

            }
        };
    }
}
