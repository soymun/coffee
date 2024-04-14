package com.example.demo.gateways;

import com.example.demo.models.CommandListDto;
import com.example.demo.models.DcCommandListDto;
import com.example.demo.models.InfoCoffee;
import com.example.demo.models.Void;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface CoffeeGateway {
    @Gateway(requestChannel = "requestChannelMakeCoffee")
    void makeCoffee(String coffeeType);

    @Gateway(requestChannel = "requestCommand", replyChannel = "responseCommand")
    CommandListDto getCommand(Void v);

    @Gateway(requestChannel = "requestDcCommand", replyChannel = "responseDcCommand")
    DcCommandListDto getDcCommand(Void v);

    @Gateway(requestChannel = "requestChannelCleanCoffee")
    void cleanCoffee(Void v);

    @Gateway(requestChannel = "requestChannelStopCoffee")
    void stopCoffee(Void v);

    @Gateway(requestChannel = "requestChannelRestartCoffee")
    void restartCoffee(Void v);

    @Gateway(requestChannel = "requestInfo", replyChannel = "responseInfo")
    InfoCoffee getInfo(Void v);

    @Gateway(requestChannel = "requestStatus", replyChannel = "responseStatus")
    String getStatus(Void v);
}
