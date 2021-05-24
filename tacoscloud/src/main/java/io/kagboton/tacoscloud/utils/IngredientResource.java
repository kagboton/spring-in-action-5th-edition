package io.kagboton.tacoscloud.utils;

import io.kagboton.tacoscloud.domain.Ingredient;
import io.kagboton.tacoscloud.domain.Ingredient.Type;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;


public class IngredientResource extends ResourceSupport {

    @Getter
    private String name;
    @Getter
    private Type type;

    public IngredientResource(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }
}
