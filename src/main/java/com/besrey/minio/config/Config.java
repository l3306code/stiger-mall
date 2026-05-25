package com.besrey.minio.config;


import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {


    //单例，没有线程安全问题
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint("http://192.168.92.128:9000")
                .credentials("minioadmin", "minioadmin")
                .build();
    }
}
