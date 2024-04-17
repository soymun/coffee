package com.example.demo.view;

import com.example.demo.gateways.CoffeeGateway;
import com.example.demo.models.CommandDto;
import com.example.demo.models.DcCommandDto;
import com.example.demo.models.Void;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Route(value = "/history", layout = HeaderView.class)
public class HistoryView extends VerticalLayout {

    private ProgressBar progressBar = new ProgressBar();

    public HistoryView(@Autowired CoffeeGateway coffeeGateway) {
        Button button = new Button("Сгенерировать отчёт");
        Dialog dialogDownload = new Dialog();
        progressBar.setWidth("15em");
        progressBar.setIndeterminate(true);
        progressBar.setVisible(true);
        Text text = new Text("Ожидайте загрузки");
        dialogDownload.add(text, progressBar);
        add(dialogDownload);
        button.addClickListener(buttonClickEvent -> {
            UI ui = UI.getCurrent();
            dialogDownload.open();

            CompletableFuture<String> execute = new CompletableFuture<>();
            execute.completeAsync(() -> {
                try {
                    byte[] file = coffeeGateway.getReport(new Void());
                    String fileName = "report.odt";
                    ui.access(() -> {
                        Dialog dialog = new Dialog();
                        Anchor anchor = new Anchor();
                        dialog.add(new Text("Отчёт скачен"));
                        anchor.setHref(generateReportResource(file, fileName));
                        anchor.setVisible(false);
                        dialog.add(anchor);
                        dialog.open();
                        ui.getPage().open(anchor.getHref(), "_blank");

                    });
                } catch (Exception ignore) {

                }
                return "true";
            });

            execute.whenCompleteAsync((a, b) -> {
                ui.access(dialogDownload::close);
            });

        });
        Map<Long, String> dictionary = coffeeGateway.getDcCommand(new Void()).getData().stream().collect(Collectors.toMap(DcCommandDto::getId, DcCommandDto::getName));
        Grid<CommandDto> grid = new Grid<>(CommandDto.class);
        grid.addClassNames("info-grid");
        grid.addColumn(obj -> dictionary.get((long) obj.getCommandId())).setHeader("Команда");
        grid.addColumn(object -> object.getTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))).setHeader("Дата");
        grid.addColumn(CommandDto::getMessage).setHeader("Сообщение");
        grid.addColumn(CommandDto::getMachine).setHeader("Кофе машина");
        grid.addColumn(this::getCoffeeLogName).setHeader("Тип кофе");

        grid.setHeightFull();

        grid.removeColumnByKey("id");
        grid.removeColumnByKey("machine");
        grid.removeColumnByKey("message");
        grid.removeColumnByKey("commandId");
        grid.removeColumnByKey("time");
        grid.removeColumnByKey("coffeeLog");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.setDataProvider(new ListDataProvider<>(coffeeGateway.getCommand(new Void()).getData()));

        setHeightFull();
        add(button, grid);
    }

    private String getCoffeeLogName(CommandDto object) {
        if (object.getCoffeeLog() != null) {
            return object.getCoffeeLog().getName();
        } else {
            return "";
        }
    }

    private com.vaadin.flow.server.StreamResource generateReportResource(byte[] file, String fileName) {
        return new com.vaadin.flow.server.StreamResource(fileName, () -> new ByteArrayInputStream(file));
    }
}
