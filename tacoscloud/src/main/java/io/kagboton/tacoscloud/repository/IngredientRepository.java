package io.kagboton.tacoscloud.repository;

import io.kagboton.tacoscloud.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
