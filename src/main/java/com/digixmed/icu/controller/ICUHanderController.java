package com.digixmed.icu.controller;

import com.digixmed.icu.config.DedupConfig;
import com.digixmed.icu.config.RateLimitConfig;
import com.digixmed.icu.service.DataHandlerService;
import com.digixmed.icu.service.DictionaryService;
import com.digixmed.icu.service.IngestService;
import com.digixmed.icu.util.YhylResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping({"/ICU"})
@RestController
public class ICUHanderController {
    private static final Logger log = LoggerFactory.getLogger(ICUHanderController.class);

    /** 请求体大小上限: 1MB */
    private static final int MAX_BODY_SIZE = 1024 * 1024;

    @Autowired
    DataHandlerService dataHandlerService;

    @Autowired
    DictionaryService dictionaryService;

    @Autowired
    IngestService ingestService;

    @Autowired
    RateLimitConfig rateLimitConfig;

    @Autowired
    DedupConfig dedupConfig;

    /**
     * 重症交互数据同步 —— 快速返回，异步处理
     * 1. 解析 MessageId
     * 2. 请求体大小校验 → 413
     * 3. 限流检查 → 429
     * 4. 去重检查 → 丢弃重复
     * 5. 入队异步处理 → 202
     */
    @PostMapping(value = "/synData", produces = MediaType.APPLICATION_XML_VALUE)
    @ApiOperation("重症交互数据同步")
    public ResponseEntity<String> synData(@RequestBody String data) {
        // 解析请求 MessageId（快速路径，不影响限流/去重流程）
        String messageId = "";
        try {
            Document doc = DocumentHelper.parseText(data);
            messageId = doc.getRootElement().attributeValue("MessageId", "");
        } catch (Exception ignored) {
        }
        log.info("收到推送请求, MessageId={}", messageId);

        // 1. 请求体大小校验
        if (data != null && data.length() > MAX_BODY_SIZE) {
            log.warn("请求体超过大小限制: {} bytes > {} bytes", data.length(), MAX_BODY_SIZE);
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body(YhylResponseUtil.buildYhylResponse(messageId, "FAILED", "PAYLOAD_TOO_LARGE", "请求体超过1MB限制"));
        }

        // 2. 限流检查
        if (!rateLimitConfig.tryAcquire()) {
            log.warn("请求被限流拒绝, 可用许可: {}", rateLimitConfig.availablePermits());
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(YhylResponseUtil.buildYhylResponse(messageId, "REJECTED", "RATE_LIMITED", "服务繁忙，请稍后重试"));
        }

        try {
            // 3. 去重检查
            if (dedupConfig.isDuplicate(data)) {
                log.info("重复请求已丢弃, MessageId={}", messageId);
                return ResponseEntity.accepted()
                        .body(YhylResponseUtil.buildYhylResponse(messageId, "SUCCESS", "DUPLICATE", "重复请求已忽略"));
            }

            // 4. 异步处理（入队后立即返回）
            ingestService.processAsync(data);
            return ResponseEntity.accepted()
                    .body(YhylResponseUtil.buildYhylResponse(messageId, "SUCCESS", "ACCEPTED", "数据已接收，异步处理中"));
        } catch (Exception e) {
            log.error("数据入队异常: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(YhylResponseUtil.buildYhylResponse(messageId, "UNKNOWN_ERR", "UNKNOWN_ERR", e.getMessage()));
        } finally {
            // 释放限流许可（入队完成即释放，不等处理完成）
            rateLimitConfig.release();
        }
    }

    @GetMapping({"/test"})
    @ApiOperation("重症字典数据同步测试")
    public String test() {
        this.dictionaryService.synDictData();
        log.info("222222222222222222");
        return "测试成功";
    }

    @GetMapping({"/test2"})
    @ApiOperation("重症患者信息请求测试")
    public String test2() {
        this.dictionaryService.synPatientData();
        return "测试成功";
    }

}
