package org.example.coffee.model.typeCoffe;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.coffee.model.ingredients.Ingredients;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class CoffeeRecipe extends Ingredients {
    protected int timeToMake;

    public abstract CoffeeType getType();
}
