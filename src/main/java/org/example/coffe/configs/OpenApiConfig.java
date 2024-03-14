package org.example.coffe.configs;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

        @Bean
        public GroupedOpenApi api() {
                return GroupedOpenApi.builder()
                        .group("REST API")
                        .pathsToMatch("/api/**")
                        .build();
        }
}
