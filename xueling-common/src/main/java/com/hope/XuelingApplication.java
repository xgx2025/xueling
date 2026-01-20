package com.hope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * XuelingApplication主应用程序类
 * @author 谢光湘
 * @date 2026/1/20
 */

/*
 * 指定根包，覆盖所有子模块的包路径
 */
@SpringBootApplication(scanBasePackages = {"com.hope.xueling"})
public class XuelingApplication {
    public static void main(String[] args) {
        SpringApplication.run(XuelingApplication.class, args);
    }
}
