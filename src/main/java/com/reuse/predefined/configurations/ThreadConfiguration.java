package com.reuse.predefined.configurations;

import java.util.concurrent.Executors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;

@Configuration
@ConditionalOnProperty(value = "spring.thread.executor", havingValue = "virtual")
public class ThreadConfiguration {

    @Bean
    public AsyncTaskExecutor apAsyncTaskExecutor() {
        return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
    }

    @Bean
    public TomcatProtocolHandlerCustomizer<?> protocolHandlerCustomizer() {
        return protocalHandler -> {
            protocalHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        };
    }
}
