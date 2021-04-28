package io.kagboton.tacoscloud.repository;

import io.kagboton.tacoscloud.domain.Ingredient;

public interface IngredientRepository {

    Iterable<Ingredient> findAll();
    Ingredient findOne(String id);
    Ingredient save(Ingredient ingredient);

}
