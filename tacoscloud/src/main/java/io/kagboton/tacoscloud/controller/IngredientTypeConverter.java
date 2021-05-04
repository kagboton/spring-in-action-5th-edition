package io.kagboton.tacoscloud.controller;

import io.kagboton.tacoscloud.domain.Ingredient;

import javax.persistence.AttributeConverter;

public class IngredientTypeConverter implements AttributeConverter<Ingredient.Type, String> {

    @Override
    public String convertToDatabaseColumn(Ingredient.Type type) {
        return type.toString();
    }

    @Override
    public Ingredient.Type convertToEntityAttribute(String s) {
        return Ingredient.Type.valueOf(s);
    }
}
