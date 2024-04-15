package com.example.demo.view;

import com.example.demo.gateways.CoffeeGateway;
import com.example.demo.models.InfoCoffee;
import com.example.demo.models.Void;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
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
public class MainView extends VerticalLayout {

    private final List<String> coffeeType = List.of("ESPRESSO", "LATTE", "CAPPUCCINO");

    public MainView(@Autowired CoffeeGateway coffeeGateway) {
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        ComboBox<String> machines = new ComboBox<>();
        machines.setItems(coffeeGateway.getMachines(new Void()).getData());

        Image image = new Image("https://home-appliances.philips/cdn/shop/files/5400_42fd3476-fc80-4787-a7bd-4b66beade3ea.jpg?v=1695302330", "Not found");
        image.setHeight("600px");
        image.setWidth("500px");
        image.addClickListener(listener -> {
            Dialog dialog = new Dialog();
            dialog.setHeaderTitle("Информация");

            Grid<InfoCoffee> grid = new Grid<>(InfoCoffee.class);
            grid.setDataProvider(new ListDataProvider<>(List.of(coffeeGateway.getInfo(machines.getValue()))));
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
            Text text = new Text("Статус - " + coffeeGateway.getStatus(machines.getValue()));
            dialog.add(grid, text);
            dialog.open();
        });
        add(machines);
        add(image);
        createCoffeeLayout(coffeeGateway);
        createButtons(coffeeGateway, machines);
    }

    private void createCoffeeLayout(CoffeeGateway coffeeGateway) {
        ComboBox<String> comboBox = new ComboBox<>("Тип кофе");
        comboBox.setItems(coffeeType);
        Button createCoffeeButton = new Button("Приготовить");
        createCoffeeButton.addClickListener(buttonClickEvent -> {
            try {
                coffeeGateway.makeCoffee(comboBox.getValue());
            } catch (Exception e) {
                Dialog dialog = new Dialog();
                dialog.setHeaderTitle("Ошибка");
                dialog.add(e.getMessage());
                dialog.open();
            }
        });
        HorizontalLayout createCoffeeLayout = new HorizontalLayout(comboBox, createCoffeeButton);
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
