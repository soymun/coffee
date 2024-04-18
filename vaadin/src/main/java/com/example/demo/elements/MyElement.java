package com.example.demo.elements;

import com.example.demo.gateways.CoffeeGateway;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

@Tag("my-element")
public class MyElement extends VerticalLayout {

    private ComboBox<String> comboBox;
    private CoffeeGateway coffeeGateway;
    private String statusPrev;

    public MyElement(ComboBox<String> comboBox, CoffeeGateway coffeeGateway) {
        this.comboBox = comboBox;
        this.coffeeGateway = coffeeGateway;
    }

    @ClientCallable
    public void getCurrentStatus(String ignore) {
        if (coffeeGateway != null) {
            if (comboBox.getValue() != null) {
                getUI().get().access(() -> {
                    if(statusPrev == null){
                        statusPrev = coffeeGateway.getStatus(comboBox.getValue());
                    } else {
                        String status = coffeeGateway.getStatus(comboBox.getValue());
                        if (!status.equals(statusPrev)) {
                            Notification notification = new Notification();
                            notification.add("Статус - " + status);
                            statusPrev = status;
                            notification.open();
                            add(notification);
                        }
                    }

                });
            }
        }
    }
}
