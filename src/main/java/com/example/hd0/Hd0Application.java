package com.example.hd0;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.hd0.mapper")
public class Hd0Application {

    public static void main(String[] args) {

        SpringApplication.run(Hd0Application.class, args);
    }

}
