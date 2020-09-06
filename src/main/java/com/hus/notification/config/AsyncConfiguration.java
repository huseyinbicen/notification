package com.hus.notification.config;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.logging.Logger;

@Configuration
@EnableAsync
public class AsyncConfiguration {
    @Bean(name = "mailAsyncTaskExecutor")
    public Executor mailAsyncThreadPoolTaskExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(1000);
        executor.setKeepAliveSeconds(6000);
        executor.initialize();
        return executor;
    }
}