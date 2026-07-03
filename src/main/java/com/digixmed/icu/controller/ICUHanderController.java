package com.digixmed.icu.controller;

import com.digixmed.icu.service.DataHandlerService;
import com.digixmed.icu.service.DictionaryService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping({"/ICU"})
@RestController
public class ICUHanderController {
    private static final Logger log = LoggerFactory.getLogger(ICUHanderController.class);

    @Autowired
    DataHandlerService dataHandlerService;

    @Autowired
    DictionaryService dictionaryService;

    @PostMapping({"/synData"})
    @ApiOperation("重症交互数据同步")
    public String synData(@RequestBody String data) {
        String str;
        try {
            str = this.dataHandlerService.handleData(data);
        } catch (Exception e) {
            log.error(e.getMessage());
            str = e.getMessage();
        }
        return str;
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
