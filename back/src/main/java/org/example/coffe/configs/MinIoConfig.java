package org.example.coffe.configs;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinIoConfig {

    @Value("${minio.endpoint}")
    public String endpoint;

    @Value("${minio.accessKey}")
    public String accessKey;

    @Value("${minio.privateKey}")
    public String privateKey;

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, privateKey)
                .build();
    }
}
