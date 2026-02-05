package com.hope.xueling.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {

    @Bean("myExecutor") // 定义 Bean 名称
    @ConfigurationProperties(prefix = "thread-pool.config") // 自动读取 yml 里的前缀
    public ThreadPoolTaskExecutor myExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // 这里不需要手动 setCorePoolSize(5) 了
        // 只要 yml 里的字段名 (core-pool-size) 和 ThreadPoolTaskExecutor 里的 setter 方法名 对应上
        // Spring 会自动帮你注入！

        executor.initialize(); // 初始化
        return executor;
    }
}

