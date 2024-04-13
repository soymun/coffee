package org.example.coffe.model.typeCoffe;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.example.coffe.model.ingredients.Ingredients;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class CoffeeRecipe extends Ingredients {
    protected int timeToMake;

    public abstract CoffeeType getType();
}
