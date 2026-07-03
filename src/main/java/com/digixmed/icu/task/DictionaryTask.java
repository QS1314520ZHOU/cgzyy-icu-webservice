package com.digixmed.icu.task;

import com.digixmed.icu.dao.MongoDao;
import com.digixmed.icu.service.BaseService;
import com.digixmed.icu.service.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DictionaryTask extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(DictionaryTask.class);

    @Autowired
    MongoDao mongoDao;

    @Autowired
    DictionaryService dictionaryService;

    @Value("${digixmed.isTask:}")
    Boolean isTask;
    @Value("${digixmed.isPatient:}")
    Boolean isPatient;
    @Value("${digixmed.isSynPatient:}")
    Boolean isSynPatient;

    @Scheduled(cron = "${digixmed.dicti-corn:0 0/30 * * * ?}")
    public void requestExamData() {
        if (this.isTask.booleanValue()) {
            log.info("开始同步字典数据!");
            try {
                this.dictionaryService.synDictData();
                log.info("字典数据同步完成!");
                return;
            } catch (Exception e) {
                log.error("字典数据同步异常：" + e.getMessage());
                return;
            }
        }
        log.info("未开启同步字典数据!");
    }

    @Scheduled(cron = "${digixmed.dicti-patient:0 0/30 * * * ?}")
    public void requestPatinetData() {
        if (this.isPatient.booleanValue()) {
            log.info("开始同步患者信息数据!");
            try {
                this.dictionaryService.synPatientData();
                log.info("患者信息数据同步完成!");
                return;
            } catch (Exception e) {
                log.error("患者信息数据同步异常：" + e.getMessage());
                return;
            }
        }
        log.info("未开启同步患者信息数据!");
    }

}