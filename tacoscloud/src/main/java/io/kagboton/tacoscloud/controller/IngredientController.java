package io.kagboton.tacoscloud.controller;

import io.kagboton.tacoscloud.domain.Ingredient;
import io.kagboton.tacoscloud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/ingredientsx", produces="application/json")
@CrossOrigin(origins="*")
public class IngredientController {

  private IngredientRepository ingredientRepository;

  @Autowired
  public IngredientController(IngredientRepository repo) {
    this.ingredientRepository = repo;
  }

  @GetMapping
  public Iterable<Ingredient> allIngredients() {
    return ingredientRepository.findAll();
  }
  
}
