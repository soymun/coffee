package org.example.coffee;

import org.example.coffee.model.typeCoffe.CoffeeRecipe;
import org.example.coffee.model.typeCoffe.CoffeeType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootApplication
public class MachineApplication {

    public static void main(String[] args) {
        SpringApplication.run(MachineApplication.class, args);
    }

    @Bean
    public Map<CoffeeType, CoffeeRecipe> coffeeFactory(List<CoffeeRecipe> coffeeRecipeList) {
        return coffeeRecipeList.stream().collect(Collectors.toMap(CoffeeRecipe::getType, Function.identity()));
    }
}
