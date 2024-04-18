package com.example.demo.view;

import com.example.demo.elements.MyElement;
import com.example.demo.gateways.CoffeeGateway;
import com.example.demo.models.CreateCoffeeDto;
import com.example.demo.models.InfoCoffee;
import com.example.demo.models.Void;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "/main", layout = HeaderView.class)
@JsModule(value = "./src/main-view-timer.js")
public class MainView extends VerticalLayout {

    private final List<String> coffeeType = List.of("ESPRESSO", "LATTE", "CAPPUCCINO");

    private final List<Integer> intValue = List.of(1, 2, 3);

    private ComboBox<String> machine;

    private Text status;

    public MainView(@Autowired CoffeeGateway coffeeGateway) {
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        machine = new ComboBox<>();
        machine.setItems(coffeeGateway.getMachines(new Void()).getData());

        Image image = new Image("https://home-appliances.philips/cdn/shop/files/5400_42fd3476-fc80-4787-a7bd-4b66beade3ea.jpg?v=1695302330", "Not found");
        image.setHeight("600px");
        image.setWidth("500px");
        image.addClickListener(listener -> {
            Dialog dialog = new Dialog();
            dialog.setHeaderTitle("Информация");

            Grid<InfoCoffee> grid = new Grid<>(InfoCoffee.class);
            grid.setDataProvider(new ListDataProvider<>(List.of(coffeeGateway.getInfo(machine.getValue()))));
            grid.addColumn(InfoCoffee::getBean).setHeader("Кофе");
            grid.addColumn(InfoCoffee::getWater).setHeader("Вода");
            grid.addColumn(InfoCoffee::getMilk).setHeader("Молоко");
            grid.addColumn(InfoCoffee::getCups).setHeader("Кружки");
            grid.setHeight("90%");

            grid.removeColumnByKey("bean");
            grid.removeColumnByKey("milk");
            grid.removeColumnByKey("water");
            grid.removeColumnByKey("cups");

            dialog.setHeight("30%");
            dialog.setWidth("30%");
            status = new Text("Статус - " + coffeeGateway.getStatus(machine.getValue()));
            dialog.add(grid, status);
            dialog.open();
        });
        MyElement myElement = new MyElement(machine, coffeeGateway);
        add(machine, myElement);
        add(image);
        createCoffeeLayout(coffeeGateway);
        createButtons(coffeeGateway, machine);
    }

    private void createCoffeeLayout(CoffeeGateway coffeeGateway) {
        ComboBox<String> comboBox = new ComboBox<>("Тип кофе");
        Checkbox milk = new Checkbox("Молоко");
        ComboBox<Integer> sugar = new ComboBox<>("Сахар");
        ComboBox<Integer> portion = new ComboBox<>("Порция");
        sugar.setItems(intValue);
        portion.setItems(intValue);
        comboBox.setItems(coffeeType);
        Button createCoffeeButton = new Button("Приготовить");
        createCoffeeButton.addClickListener(buttonClickEvent -> {
            try {
                coffeeGateway.makeCoffee(new CreateCoffeeDto(comboBox.getValue(), milk.getValue(), portion.getValue(), sugar.getValue()));
            } catch (Exception e) {
                Dialog dialog = new Dialog();
                dialog.setHeaderTitle("Ошибка");
                dialog.add(e.getMessage());
                dialog.open();
            }
        });
        HorizontalLayout createCoffeeLayout = new HorizontalLayout(comboBox, milk, sugar, portion, createCoffeeButton);
        createCoffeeLayout.setAlignItems(Alignment.CENTER);
        createCoffeeLayout.setVerticalComponentAlignment(Alignment.END, comboBox, createCoffeeButton);
        add(createCoffeeLayout);
    }

    public void createButtons(CoffeeGateway coffeeGateway, ComboBox<String> machines) {
        Button cleanMachineButton = new Button("Очистить");
        cleanMachineButton.addClickListener(listener -> {
            try {
                coffeeGateway.cleanCoffee(machines.getValue());
            } catch (Exception e) {
                Dialog dialog = new Dialog();
                dialog.setHeaderTitle("Ошибка");
                dialog.add(e.getMessage());
                dialog.open();
            }
        });
        Button offMachine = new Button("Выключить");
        offMachine.addClickListener(listener -> {
            coffeeGateway.stopCoffee(machines.getValue());
        });
        Button restartButton = new Button("Перезагрузить");
        restartButton.addClickListener(listener -> {
            coffeeGateway.restartCoffee(machines.getValue());
        });
        HorizontalLayout anotherButtonCoffeeLayout = new HorizontalLayout(cleanMachineButton, restartButton, offMachine);
        anotherButtonCoffeeLayout.setAlignItems(Alignment.CENTER);
        add(anotherButtonCoffeeLayout);
    }
}
