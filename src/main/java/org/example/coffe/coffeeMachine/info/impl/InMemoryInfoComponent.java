package org.example.coffe.coffeeMachine.info.impl;

import lombok.ToString;
import org.example.coffe.coffeeMachine.info.Info;
import org.example.coffe.coffeeMachine.info.InfoCoffee;
import org.example.coffe.model.ingredients.Ingredients;
import org.example.coffe.model.typeCoffe.CoffeeRecipe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@ToString(callSuper = true)
@Primary
public final class InMemoryInfoComponent extends Ingredients implements Info {

    protected int cups;

    public InMemoryInfoComponent(@Value("${coffee-machine.cups}") int cups,
                                 @Value("${coffee-machine.water}") int water,
                                 @Value("${coffee-machine.milk}") int milk,
                                 @Value("${coffee-machine.beans}") int beans) {
        this.cups = cups;
        this.water = water;
        this.milk = milk;
        this.beans = beans;
    }

    @Override
    public void appendCups(int water) {
        this.water = water;
    }

    @Override
    public void appendWater(int cups) {
        this.cups = cups;
    }

    @Override
    public void appendMilk(int milk) {
        this.milk = milk;
    }

    @Override
    public void appendBeans(int beans) {
        this.beans = beans;
    }

    @Override
    public void allocate(CoffeeRecipe coffeeRecipe) {
        this.water -= coffeeRecipe.getWater();
        this.milk -= coffeeRecipe.getMilk();
        this.beans -= coffeeRecipe.getBeans();
        this.cups -= 1;
    }

    @Override
    public boolean isEnoughFor(CoffeeRecipe coffeeRecipe) {
        if (this.water - coffeeRecipe.getWater() < 0) {
            return false;
        }
        if (this.milk - coffeeRecipe.getMilk() < 0) {
            return false;
        }
        if (this.beans - coffeeRecipe.getBeans() < 0) {
            return false;
        }
        if (this.cups - 1 < 0) {
            return false;
        }
        return true;
    }

    @Override
    public InfoCoffee getInfo() {
        return new InfoCoffee(cups, water, milk, beans);
    }
}
