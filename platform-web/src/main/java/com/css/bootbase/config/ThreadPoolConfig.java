package com.css.bootbase.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

/**
 * 异步线程池配置类
 * Created by jiming.jing on 2021/8/16
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(
        prefix = "spring.task.execution.pool"
)
@Component("threadPoolConfig")
public class ThreadPoolConfig implements AsyncConfigurer {

    private static final String THREAD_PREFIX = "async-";

    private int corePoolSize;

    private int maxPoolSize;

    private int queueCapacity;

    private int keepAliveSeconds;


    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setThreadNamePrefix(THREAD_PREFIX);
        // 设置核心线程数
        threadPool.setCorePoolSize(corePoolSize);
        // 设置最大线程数
        threadPool.setMaxPoolSize(maxPoolSize);
        // 线程池所使用的缓冲队列
        threadPool.setQueueCapacity(queueCapacity);
        // 等待任务在关机时完成-表明等待所有线程执行完
        threadPool.setWaitForTasksToCompleteOnShutdown(true);
        threadPool.setKeepAliveSeconds(keepAliveSeconds);
        // 线程池的工作线程空闲后（指大于核心又小于max的那部分线程），保持存活的时间
        threadPool.setAwaitTerminationSeconds(60);
        // 饱和策略默认是直接抛弃任务
        // 初始化线程
        threadPool.initialize();
        return threadPool;
    }
}
