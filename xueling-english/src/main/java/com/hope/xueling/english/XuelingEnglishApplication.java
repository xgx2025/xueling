package com.hope.xueling.english;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.hope.xueling"})
@MapperScan({"com.hope.xueling.english.mapper", "com.hope.xueling.common.mapper"})
public class XuelingEnglishApplication {

    public static void main(String[] args) {
        SpringApplication.run(XuelingEnglishApplication.class, args);
    }

}
