package com.peteralbus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.peteralbus.mapper")
@SpringBootApplication
public class CheckFishSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckFishSpringbootApplication.class, args);
    }

}
