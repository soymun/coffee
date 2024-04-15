package org.example.coffe.service;

import org.example.coffe.model.InfoCoffee;
import org.example.coffe.model.Status;

import java.util.List;

public interface InfoService {

    Status getStatus(String machine) throws InterruptedException;

    InfoCoffee getInfo(String machine) throws InterruptedException;

    List<String> machine();
}
