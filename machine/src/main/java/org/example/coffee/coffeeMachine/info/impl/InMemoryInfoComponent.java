package org.example.coffee.coffeeMachine.info.impl;

import lombok.ToString;
import org.example.coffee.coffeeMachine.info.Info;
import org.example.coffee.coffeeMachine.info.InfoCoffee;
import org.example.coffee.model.ingredients.Ingredients;
import org.example.coffee.model.typeCoffe.CoffeeRecipe;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

@Component
@ToString(callSuper = true)
@Primary
public final class InMemoryInfoComponent extends Ingredients implements Info {

    protected int cups;

    protected int sugar;

    File file = new File("machine/resourses.yml");

    public InMemoryInfoComponent() throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        this.water = Integer.parseInt(scanner.nextLine());
        this.milk = Integer.parseInt(scanner.nextLine());
        this.beans = Integer.parseInt(scanner.nextLine());
        this.cups = Integer.parseInt(scanner.nextLine());
        this.sugar = Integer.parseInt(scanner.nextLine());
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
    public void allocate(CoffeeRecipe coffeeRecipe, boolean milk, int count, int sugar) {
        this.water -= coffeeRecipe.getWater() * count;
        if (milk) {
            this.milk -= coffeeRecipe.getMilk() * count;
        }
        this.beans -= coffeeRecipe.getBeans() * count;
        this.sugar -= sugar;
        this.cups -= 1;
    }

    @Override
    public boolean isEnoughFor(CoffeeRecipe coffeeRecipe, boolean milk, int count, int sugar) {
        if (this.water - (coffeeRecipe.getWater() * count) < 0) {
            return false;
        }
        if (milk && this.milk - (coffeeRecipe.getMilk() * count) < 0) {
            return false;
        }
        if (this.beans - (coffeeRecipe.getBeans() * count) < 0) {
            return false;
        }
        if (this.sugar - sugar < 0) {
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

    public void saveSettings() throws IOException {
        String setting = water + "\n" +
                milk + "\n" +
                beans + "\n" +
                cups + "\n" +
                sugar;
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(setting.getBytes());
        }
    }
}
