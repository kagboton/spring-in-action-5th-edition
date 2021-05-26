package io.kagboton.tacoscloud.utils.resources;

import io.kagboton.tacoscloud.controller.IngredientController;
import io.kagboton.tacoscloud.domain.Ingredient;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class IngredientResourceAssembler extends ResourceAssemblerSupport<Ingredient, IngredientResource> {

    public IngredientResourceAssembler() {
        super(IngredientController.class, IngredientResource.class);
    }

    @Override
    protected IngredientResource instantiateResource(Ingredient ingredient) {
        return new IngredientResource(ingredient);
    }

    @Override
    public IngredientResource toResource(Ingredient ingredient) {
        return createResourceWithId(ingredient.getId(), ingredient);
    }
}
