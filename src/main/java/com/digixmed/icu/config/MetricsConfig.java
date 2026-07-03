package com.digixmed.icu.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 可观测性配置 —— 暴露 ingest 队列相关指标到 Actuator
 */
@Configuration
public class MetricsConfig {

    private static final AtomicLong queueDepth = new AtomicLong(0);
    private static Counter processedCounter;
    private static Counter failedCounter;
    private static Counter rejectedCounter;

    @Autowired
    private MeterRegistry meterRegistry;

    @PostConstruct
    public void init() {
        // 入队处理计数
        processedCounter = Counter.builder("icu.ingest.processed")
                .description("成功处理的消息数")
                .register(meterRegistry);

        // 处理失败计数
        failedCounter = Counter.builder("icu.ingest.failed")
                .description("处理失败的消息数")
                .register(meterRegistry);

        // 限流拒绝计数
        rejectedCounter = Counter.builder("icu.ingest.rejected")
                .description("被限流拒绝的请求数")
                .register(meterRegistry);

        // 队列深度（实时）
        Gauge.builder("icu.ingest.queue.depth", queueDepth, AtomicLong::get)
                .description("当前 ingest 队列深度")
                .register(meterRegistry);
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("app", "icu-webservice");
    }

    public static void incrementProcessed() {
        if (processedCounter != null) processedCounter.increment();
    }

    public static void incrementFailed() {
        if (failedCounter != null) failedCounter.increment();
    }

    public static void incrementRejected() {
        if (rejectedCounter != null) rejectedCounter.increment();
    }

    public static void updateQueueDepth(long depth) {
        queueDepth.set(depth);
    }
}
