package org.example.coffe;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.vertx.core.Vertx;
import org.example.coffe.sockets.SocketContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
@OpenAPIDefinition
public class CoffeApplication implements InitializingBean {

    @Autowired
    private SocketContext context;

    public static void main(String[] args) {
        SpringApplication.run(CoffeApplication.class, args);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(context);
    }
}
