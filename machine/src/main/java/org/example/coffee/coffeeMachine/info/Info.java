package org.example.coffee.coffeeMachine.info;


import org.example.coffee.model.typeCoffe.CoffeeRecipe;

import java.io.IOException;

public interface Info {

    /**
     * Добавление круэек
     * @param cups количество кружен
     */
    void appendCups(int cups);

    /**
     * Добавление воды
     * @param water колчество воды
     */
    void appendWater(int water);

    /**
     * Добавление молока
     * @param milk количество молока
     */
    void appendMilk(int milk);

    /**
     * Добавление кофе
     * @param beans количесвто кофе
     */
    void appendBeans(int beans);

    /**
     * Уменьщение ингредиентов
     * @param coffeeRecipe тип кофе
     */
    void allocate(CoffeeRecipe coffeeRecipe, boolean milk, int count, int sugar);

    /**
     * Проверить на доступность выполнения
     * @param coffeeRecipe тип кофе
     * @return Можно ли выполнить задачу
     */
    boolean isEnoughFor(CoffeeRecipe coffeeRecipe, boolean milk, int count, int sugar);

    /**
     * Получить информацию об ингредиентах
     * @return ингредиенты
     */
    InfoCoffee getInfo();

    /**
     * Сохранить настройки в файл
     */
    void saveSettings() throws IOException;
}
