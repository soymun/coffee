package com.example.demo.integrations;

import com.example.demo.context.InfoContext;
import com.example.demo.models.CommandListDto;
import com.example.demo.models.DcCommandDto;
import com.example.demo.models.DcCommandListDto;
import com.example.demo.models.InfoCoffee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import java.util.stream.Collectors;

@Configuration
public class IntegrationConfig {

    @Autowired
    private InfoContext infoContext;

    @Value("${coffee.url}")
    private String url;

    @Bean
    public IntegrationFlow fetchCommandMapFlow() {
        return IntegrationFlow.from(requestChannelCommand())
                .handle(Http.outboundGateway("http://" + url + "/api/v1/command")
                        .expectedResponseType(String.class)
                        .httpMethod(HttpMethod.GET))
                .transform(Transformers.fromJson(CommandListDto.class))
                .handle(message -> {
                    infoContext.setData(((CommandListDto) message.getPayload()).getData());
                })
                .get();
    }

    @Bean
    public IntegrationFlow sendCreateCoffee() {
        return IntegrationFlow.from(requestChannelMakeCoffee())
                .handle(Http.outboundGateway("http://" + url + "/api/v1/coffee/make?coffeeType={type}")
                        .expectedResponseType(String.class)
                        .httpMethod(HttpMethod.POST)
                        .uriVariable("type", Message::getPayload))
                .get();
    }

    @Bean
    public IntegrationFlow sendCleanCoffee() {
        return IntegrationFlow.from(requestChannelCleanCoffee())
                .handle(Http.outboundGateway("http://" + url + "/api/v1/coffee/clean")
                        .expectedResponseType(String.class)
                        .httpMethod(HttpMethod.POST))
                .handle(message -> {
                })
                .get();
    }

    @Bean
    public IntegrationFlow sendStopCoffee() {
        return IntegrationFlow.from(requestChannelStopCoffee())
                .handle(Http.outboundGateway("http://" + url + "/api/v1/coffee/stop")
                        .expectedResponseType(String.class)
                        .httpMethod(HttpMethod.POST))
                .handle(message -> {
                })
                .get();
    }

    @Bean
    public IntegrationFlow sendRestartCoffee() {
        return IntegrationFlow.from(requestChannelRestartCoffee())
                .handle(Http.outboundGateway("http://" + url + "/api/v1/coffee/restart")
                        .expectedResponseType(String.class)
                        .httpMethod(HttpMethod.POST))
                .handle(message -> {
                })
                .get();
    }

    @Bean
    public IntegrationFlow fetchCommandsFlow() {
        return IntegrationFlow.from(requestChannelDictionary())
                .handle(Http.outboundGateway("http://" + url + "/api/v1/dc-command")
                        .expectedResponseType(String.class)
                        .httpMethod(HttpMethod.GET))
                .transform(Transformers.fromJson(DcCommandListDto.class))
                .handle(object -> {
                    infoContext.setDictionary(((DcCommandListDto) object.getPayload()).getData().stream().collect(Collectors.toMap(DcCommandDto::getId, DcCommandDto::getName)));
                })
                .get();
    }

    @Bean
    public IntegrationFlow fetchInfoMachine() {
        return IntegrationFlow.from(requestChannelInfo())
                .handle(Http.outboundGateway("http://" + url + "/api/v1/info/info")
                        .expectedResponseType(String.class)
                        .httpMethod(HttpMethod.GET))
                .transform(Transformers.fromJson(InfoCoffee.class))
                .handle(object -> {
                    infoContext.setInfoCoffee(((InfoCoffee) object.getPayload()));
                })
                .get();
    }

    @Bean
    public IntegrationFlow fetchStatusMachine() {
        return IntegrationFlow.from(requestChannelStatus())
                .handle(Http.outboundGateway("http://" + url + "/api/v1/info/status")
                        .expectedResponseType(String.class)
                        .httpMethod(HttpMethod.GET))
                .handle(object -> {
                    infoContext.setStatus((String) object.getPayload());
                })
                .get();
    }

    @Bean
    public MessageChannel requestChannelCommand() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel requestChannelDictionary() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel requestChannelMakeCoffee() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel requestChannelCleanCoffee() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel requestChannelStopCoffee() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel requestChannelRestartCoffee() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel requestChannelInfo() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel requestChannelStatus() {
        return new DirectChannel();
    }
}
