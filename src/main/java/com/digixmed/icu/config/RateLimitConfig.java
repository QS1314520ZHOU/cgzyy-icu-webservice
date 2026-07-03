package com.digixmed.icu.config;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 限流组件 —— 基于 Semaphore 的滑动窗口限流
 * 超阈值返回 429，让调用方退避重试
 */
@Component
public class RateLimitConfig {
    private static final Logger log = LoggerFactory.getLogger(RateLimitConfig.class);

    /** 最大并发处理许可数 */
    private static final int MAX_PERMITS = 200;

    private final Semaphore semaphore = new Semaphore(MAX_PERMITS);

    private final AtomicLong rejectedCount = new AtomicLong(0);

    @PostConstruct
    public void init() {
        log.info("限流组件初始化: maxPermits={}", MAX_PERMITS);
    }

    /**
     * 尝试获取许可，获取失败表示需要限流
     * @return true=放行, false=需要拒绝(429)
     */
    public boolean tryAcquire() {
        boolean acquired = semaphore.tryAcquire();
        if (!acquired) {
            rejectedCount.incrementAndGet();
            MetricsConfig.incrementRejected();
        }
        return acquired;
    }

    /**
     * 释放许可（处理完成后调用）
     */
    public void release() {
        semaphore.release();
    }

    public long getRejectedCount() {
        return rejectedCount.get();
    }

    public int availablePermits() {
        return semaphore.availablePermits();
    }
}
