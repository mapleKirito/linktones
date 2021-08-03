package com.linktones.mapledemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MapleDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MapleDemoApplication.class, args);
    }

}
