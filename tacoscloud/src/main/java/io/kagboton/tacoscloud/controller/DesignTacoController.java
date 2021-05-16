package io.kagboton.tacoscloud.controller;

import io.kagboton.tacoscloud.domain.Ingredient;
import io.kagboton.tacoscloud.domain.Ingredient.Type;
import io.kagboton.tacoscloud.domain.Order;
import io.kagboton.tacoscloud.domain.Taco;
import io.kagboton.tacoscloud.domain.User;
import io.kagboton.tacoscloud.repository.IngredientRepository;
import io.kagboton.tacoscloud.repository.TacoRepository;
import io.kagboton.tacoscloud.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private TacoRepository tacoRepository;
    private UserRepository userRepository;


    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository, UserRepository userRepository) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
        this.userRepository = userRepository;
    }

    @ModelAttribute(name = "order")
    public Order order(){
        return new Order();
    }

    @ModelAttribute(name = "design")
    public Taco taco(){
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model, Principal principal){

        log.info("   ===> Designing taco");

        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(i -> ingredients.add(i));

        Type [] types = Ingredient.Type.values();
        for(Type type : types){
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }

        // get the current user and add him to the model
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);

        return "design";
    }


    @PostMapping
    public String processDesign(@Valid Taco taco, Errors errors, @ModelAttribute Order order){

        log.info("   ===> Saving taco");

        if (errors.hasErrors()){
            return "design";
        }

        // save taco design
        Taco saved = tacoRepository.save(taco);

        // update order object with the design created
        order.addDesign(saved);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type){

        return  ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
