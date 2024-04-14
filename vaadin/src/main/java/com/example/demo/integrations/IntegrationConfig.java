package com.example.demo.integrations;

import com.example.demo.models.CommandListDto;
import com.example.demo.models.DcCommandListDto;
import com.example.demo.models.InfoCoffee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.Message;

@Configuration
public class IntegrationConfig {

    @Value("${coffee.url}")
    private String url;

    @Bean
    public IntegrationFlow fetchCommandMapFlow() {
        return IntegrationFlow.from("requestCommand")
                .handle(Http.outboundGateway("http://" + url + "/api/v1/command")
                        .expectedResponseType(String.class)
                        .httpMethod(HttpMethod.GET))
                .transform(Transformers.fromJson(CommandListDto.class))
                .channel("responseCommand")
                .get();
    }

    @Bean
    public IntegrationFlow sendCreateCoffee() {
        return IntegrationFlow.from("requestChannelMakeCoffee")
                .handle(Http.outboundGateway("http://" + url + "/api/v1/coffee/make?coffeeType={type}")
                        .expectedResponseType(String.class)
                        .httpMethod(HttpMethod.POST)
                        .uriVariable("type", Message::getPayload))
                .get();
    }

    @Bean
    public IntegrationFlow sendCleanCoffee() {
        return IntegrationFlow.from("requestChannelCleanCoffee")
                .handle(Http.outboundGateway("http://" + url + "/api/v1/coffee/clean")
                        .expectedResponseType(String.class)
                        .httpMethod(HttpMethod.POST))
                .handle(message -> {
                })
                .get();
    }

    @Bean
    public IntegrationFlow sendStopCoffee() {
        return IntegrationFlow.from("requestChannelStopCoffee")
                .handle(Http.outboundGateway("http://" + url + "/api/v1/coffee/stop")
                        .expectedResponseType(String.class)
                        .httpMethod(HttpMethod.POST))
                .handle(message -> {
                })
                .get();
    }

    @Bean
    public IntegrationFlow sendRestartCoffee() {
        return IntegrationFlow.from("requestChannelRestartCoffee")
                .handle(Http.outboundGateway("http://" + url + "/api/v1/coffee/restart")
                        .expectedResponseType(String.class)
                        .httpMethod(HttpMethod.POST))
                .handle(message -> {
                })
                .get();
    }

    @Bean
    public IntegrationFlow fetchCommandsFlow() {
        return IntegrationFlow.from("requestDcCommand")
                .handle(Http.outboundGateway("http://" + url + "/api/v1/dc-command")
                        .expectedResponseType(String.class)
                        .httpMethod(HttpMethod.GET))
                .transform(Transformers.fromJson(DcCommandListDto.class))
                .channel("responseDcCommand")
                .get();
    }

    @Bean
    public IntegrationFlow fetchInfoMachine() {
        return IntegrationFlow.from("requestInfo")
                .handle(Http.outboundGateway("http://" + url + "/api/v1/info/info")
                        .expectedResponseType(String.class)
                        .httpMethod(HttpMethod.GET))
                .transform(Transformers.fromJson(InfoCoffee.class))
                .channel("responseInfo")
                .get();
    }

    @Bean
    public IntegrationFlow fetchStatusMachine() {
        return IntegrationFlow.from("requestStatus")
                .handle(Http.outboundGateway("http://" + url + "/api/v1/info/status")
                        .expectedResponseType(String.class)
                        .httpMethod(HttpMethod.GET))
                .channel("responseStatus")
                .get();
    }
}
