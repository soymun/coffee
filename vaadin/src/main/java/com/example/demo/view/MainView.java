package com.example.demo.view;

import com.example.demo.context.InfoContext;
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
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import java.util.List;

@Route(value = "/main", layout = HeaderView.class)
public class MainView extends VerticalLayout {

    private final List<String> coffeeType = List.of("ESPRESSO", "LATTE", "CAPPUCCINO");

    public MainView(@Autowired InfoContext infoContext,
                    @Autowired @Qualifier("requestChannelMakeCoffee") MessageChannel createCoffee,
                    @Autowired @Qualifier("requestChannelStatus") MessageChannel statusCoffee,
                    @Autowired @Qualifier("requestChannelInfo") MessageChannel infoCoffee,
                    @Autowired @Qualifier("requestChannelCleanCoffee") MessageChannel cleanCoffee,
                    @Autowired @Qualifier("requestChannelStopCoffee") MessageChannel stopCoffee,
                    @Autowired @Qualifier("requestChannelRestartCoffee") MessageChannel restartCoffee) {
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        Image image = new Image("https://home-appliances.philips/cdn/shop/files/5400_42fd3476-fc80-4787-a7bd-4b66beade3ea.jpg?v=1695302330", "Not found");
        image.setHeight("600px");
        image.setWidth("500px");
        image.addClickListener(listener -> {
            GenericMessage<Void> message = new GenericMessage<>(new Void());
            infoCoffee.send(message);
            statusCoffee.send(message);
            Dialog dialog = new Dialog();
            dialog.setHeaderTitle("Информация");

            Grid<InfoCoffee> grid = new Grid<>(InfoCoffee.class);
            grid.setItems(infoContext.getInfoCoffee()); // assuming infoContext.getInfoCoffee() returns a List<InfoCoffee>
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
            Text text = new Text("Статус - " + infoContext.getStatus());
            dialog.add(grid, text);
            dialog.open();
        });
        add(image);;
        createCoffeeLayout(createCoffee);
        createButtons(cleanCoffee, stopCoffee, restartCoffee);
    }

    private void createCoffeeLayout(MessageChannel createCoffee) {
        ComboBox<String> comboBox = new ComboBox<>("Тип кофе");
        comboBox.setItems(coffeeType);
        Button createCoffeeButton = new Button("Приготовить");
        createCoffeeButton.addClickListener(buttonClickEvent -> {
            String type = comboBox.getValue();
            GenericMessage<String> message = new GenericMessage<>(type);
            createCoffee.send(message);
        });
        HorizontalLayout createCoffeeLayout = new HorizontalLayout(comboBox, createCoffeeButton);
        createCoffeeLayout.setAlignItems(Alignment.CENTER);
        createCoffeeLayout.setVerticalComponentAlignment(Alignment.END, comboBox, createCoffeeButton);
        add(createCoffeeLayout);
    }

    public void createButtons(MessageChannel cleanCoffee,
                              MessageChannel stopCoffee,
                              MessageChannel restartCoffee) {
        Button cleanMachineButton = new Button("Очистить");
        cleanMachineButton.addClickListener(listener -> {
            GenericMessage<Void> message = new GenericMessage<>(new Void());
            cleanCoffee.send(message);
        });
        Button offMachine = new Button("Выключить");
        offMachine.addClickListener(listener -> {
            GenericMessage<Void> message = new GenericMessage<>(new Void());
            stopCoffee.send(message);
        });
        Button restartButton = new Button("Перезагрузить");
        restartButton.addClickListener(listener -> {
            GenericMessage<Void> message = new GenericMessage<>(new Void());
            restartCoffee.send(message);
        });
        HorizontalLayout anotherButtonCoffeeLayout = new HorizontalLayout(cleanMachineButton, restartButton, offMachine);
        anotherButtonCoffeeLayout.setAlignItems(Alignment.CENTER);
        add(anotherButtonCoffeeLayout);
    }
}
