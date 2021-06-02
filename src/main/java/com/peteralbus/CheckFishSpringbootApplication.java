package com.peteralbus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@MapperScan("com.peteralbus.mapper")
@SpringBootApplication
public class CheckFishSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckFishSpringbootApplication.class, args);
    }

}
