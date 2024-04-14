package com.example.demo.view;

import com.example.demo.gateways.CoffeeGateway;
import com.example.demo.models.CommandDto;
import com.example.demo.models.DcCommandDto;
import com.example.demo.models.Void;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

@Route(value = "/history", layout = HeaderView.class)
public class HistoryView extends VerticalLayout {

    public HistoryView(@Autowired CoffeeGateway coffeeGateway) {
        Map<Long, String> dictionary = coffeeGateway.getDcCommand(new Void()).getData().stream().collect(Collectors.toMap(DcCommandDto::getId, DcCommandDto::getName));
        Grid<CommandDto> grid = new Grid<>(CommandDto.class);
        grid.addClassNames("info-grid");
        grid.addColumn(obj -> dictionary.get((long) obj.getCommandId())).setHeader("Команда");
        grid.addColumn(object -> object.getTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))).setHeader("Дата");
        grid.addColumn(CommandDto::getMessage).setHeader("Сообщение");
        grid.addColumn(this::getCoffeeLogName).setHeader("Тип кофе");

        grid.setHeightFull();

        grid.removeColumnByKey("id");
        grid.removeColumnByKey("message");
        grid.removeColumnByKey("commandId");
        grid.removeColumnByKey("time");
        grid.removeColumnByKey("coffeeLog");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.setItems(coffeeGateway.getCommand(new Void()).getData());

        setHeightFull();
        add(grid);
    }

    private String getCoffeeLogName(CommandDto object) {
        if (object.getCoffeeLog() != null) {
            return object.getCoffeeLog().getName();
        } else {
            return "";
        }
    }
}
