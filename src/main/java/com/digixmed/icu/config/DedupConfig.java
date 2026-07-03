package com.digixmed.icu.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 请求去重组件 —— 基于 Caffeine 缓存，60s 内相同内容的推送会被丢弃
 */
@Component
public class DedupConfig {
    private static final Logger log = LoggerFactory.getLogger(DedupConfig.class);

    private Cache<String, Boolean> dedupCache;

    @PostConstruct
    public void init() {
        dedupCache = Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .maximumSize(10000)
                .build();
        log.info("去重缓存初始化: ttl=60s, maxSize=10000");
    }

    /**
     * 检查请求是否重复
     * @param data 请求体内容
     * @return true=重复（应丢弃）, false=首次（应处理）
     */
    public boolean isDuplicate(String data) {
        String hash = sha256(data);
        Boolean existing = dedupCache.getIfPresent(hash);
        if (existing != null) {
            log.info("检测到重复请求, hash={}", hash.substring(0, 8));
            return true;
        }
        dedupCache.put(hash, Boolean.TRUE);
        return false;
    }

    private String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // SHA-256 在所有 JVM 中都可用，这里不会触发
            return String.valueOf(input.hashCode());
        }
    }
}
