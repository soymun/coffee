package com.example.demo.gateways;

import com.example.demo.models.*;
import com.example.demo.models.Void;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface CoffeeGateway {
    @Gateway(requestChannel = "requestChannelMakeCoffee")
    void makeCoffee(CreateCoffeeDto coffeeType);

    @Gateway(requestChannel = "requestCommand", replyChannel = "responseCommand")
    CommandListDto getCommand(Void v);

    @Gateway(requestChannel = "requestDcCommand", replyChannel = "responseDcCommand")
    DcCommandListDto getDcCommand(Void v);

    @Gateway(requestChannel = "requestMachine", replyChannel = "responseMachine")
    MachineDataList getMachines(Void v);

    @Gateway(requestChannel = "requestChannelCleanCoffee")
    void cleanCoffee(String machine);

    @Gateway(requestChannel = "requestChannelStopCoffee")
    void stopCoffee(String machine);

    @Gateway(requestChannel = "requestChannelRestartCoffee")
    void restartCoffee(String machine);

    @Gateway(requestChannel = "requestInfo", replyChannel = "responseInfo")
    InfoCoffee getInfo(String machine);

    @Gateway(requestChannel = "requestStatus", replyChannel = "responseStatus")
    String getStatus(String machine);

    @Gateway(requestChannel = "requestReport", replyChannel = "responseReport")
    byte[] getReport(Void v);
}
