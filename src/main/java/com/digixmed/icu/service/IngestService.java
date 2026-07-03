package com.digixmed.icu.service;

import com.digixmed.icu.config.MetricsConfig;
import java.util.concurrent.ThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步数据处理服务 —— 在独立线程池中执行重逻辑
 */
@Service
public class IngestService {
    private static final Logger log = LoggerFactory.getLogger(IngestService.class);

    @Autowired
    private DataHandlerService dataHandlerService;

    @Autowired
    private ThreadPoolTaskExecutor ingestExecutor;

    @Async("ingestExecutor")
    public void processAsync(String data) {
        try {
            // 队列使用率告警
            ThreadPoolExecutor pool = ingestExecutor.getThreadPoolExecutor();
            int queueSize = pool.getQueue().size();
            int queueCapacity = 2000;
            double usage = (double) queueSize / queueCapacity;
            if (usage > 0.8) {
                log.warn("⚠️ ingest 队列使用率 {:.1f}% ({}/{})", usage * 100, queueSize, queueCapacity);
            }
            MetricsConfig.updateQueueDepth(queueSize);

            // 执行实际业务处理
            dataHandlerService.handleData(data);
            MetricsConfig.incrementProcessed();
        } catch (Exception e) {
            log.error("异步处理数据异常: {}", e.getMessage(), e);
            MetricsConfig.incrementFailed();
        }
    }
}
