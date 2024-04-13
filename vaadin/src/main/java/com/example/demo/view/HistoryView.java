package com.example.demo.view;

import com.example.demo.context.InfoContext;
import com.example.demo.models.CommandDto;
import com.example.demo.models.Void;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import java.time.format.DateTimeFormatter;

@Route(value = "/history", layout = HeaderView.class)
public class HistoryView extends VerticalLayout {

    public HistoryView(@Autowired InfoContext infoContext,
                       @Autowired @Qualifier("requestChannelDictionary") MessageChannel requestChannel,
                       @Autowired @Qualifier("requestChannelCommand") MessageChannel requestChannelData) {
        initDictionaries(requestChannel);
        getInfo(requestChannelData);
        Grid<CommandDto> grid = new Grid<>(CommandDto.class);
        grid.addClassNames("info-grid");
        grid.addColumn(obj -> infoContext.getDictionary().get((long) obj.getCommandId())).setHeader("Команда");
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

        if (infoContext.getData() != null) {
            grid.setItems(infoContext.getData());
        }

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

    public void initDictionaries(MessageChannel requestChannel) {
        GenericMessage<Void> message = new GenericMessage<>(new Void());
        requestChannel.send(message);
    }

    public void getInfo(MessageChannel requestChannel) {
        GenericMessage<Void> message = new GenericMessage<>(new Void());
        requestChannel.send(message);
    }
}
