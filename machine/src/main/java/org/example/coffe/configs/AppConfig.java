package org.example.coffe.configs;

import org.example.coffe.model.typeCoffe.CoffeeRecipe;
import org.example.coffe.model.typeCoffe.CoffeeType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class AppConfig {

    @Bean
    public Map<CoffeeType, CoffeeRecipe> coffeeFactory(List<CoffeeRecipe> coffeeRecipeList) {
        return coffeeRecipeList.stream().collect(Collectors.toMap(CoffeeRecipe::getType, Function.identity()));
    }
}
