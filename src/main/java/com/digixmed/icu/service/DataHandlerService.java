package com.digixmed.icu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DataHandlerService {
    private static final Logger log = LoggerFactory.getLogger(DataHandlerService.class);

    @Autowired
    ViPatInfoService patientInfoService;

    @Autowired
    OrderService orderService;

    @Autowired
    ExamService examService;

    @Autowired
    PatientService patientService;

    @Value("${digixmed.dept-code:}")
    String deptCode;
    List<String> deptCodeList = new ArrayList();

    public String handleData(String data) {
        Document messageData=null;
        String serviceCode="";
        Element bodyElement;
        String messageId = "";
        String status = "SUCCESS";
        String message = "[{query=[{count=1}]}]";
        log.info("数据内容：[[[" + data + "]]");
        List<String> deptCodeList = getDeptCodeList();
        log.info("deptCodeList=" + deptCodeList.toString());
        try {
            messageData = DocumentHelper.parseText(data);
            messageId = messageData.getRootElement().attribute("MessageId").getValue();
            serviceCode = messageData.getRootElement().attribute("Action").getValue();
            serviceCode=serviceCode.toLowerCase();
            log.info("接口编号:" + serviceCode);
            if ("yjjy_jybg".equals(serviceCode)) {
                this.examService.handlerExamRequest(messageData.getRootElement().element("Message"), deptCodeList);
                return getReturnResult(messageId, status, message);
            }
            //检查申请
            if ("sqd_jcsqd".equals(serviceCode)) {
                this.examService.handleReportOpertion(messageData.getRootElement().element("Message"), deptCodeList);
                return getReturnResult(messageId, status, message);
            }
            //检查结果
            if ("yjjc_jcbg".equals(serviceCode)) {
                this.examService.handleReport(messageData.getRootElement().element("Message"), deptCodeList);
                return getReturnResult(messageId, status, message);
            }
            //危急值
            if ("ylzl_wjzdj".equals(serviceCode)) {
                this.examService.handleReportWJZDJ(messageData.getRootElement().element("Message"), deptCodeList);
                return getReturnResult(messageId, status, message);
            }
            bodyElement = messageData.getRootElement().element("Message").element("Component").element("Entry");
            switch (serviceCode) {
                case "zy_rydj":
                    log.info("病人基本信息-先从入院登记中 记录一手病人基本信息");
                    this.patientInfoService.handlePatientInfo(bodyElement);
                    log.info("住院登记开始同步");
                    this.patientInfoService.inPatientRegister(bodyElement, deptCodeList);
                    break;
                case "zy_rkdjxx":
                    log.info("入科登记信息");
                    this.patientInfoService.inDept(bodyElement, deptCodeList);
                    break;
                case "zy_zyzdmx":
                    log.info("诊断同步");
                    this.patientInfoService.transZhenDuan(bodyElement, deptCodeList);
                    break;
                case "ylyw_zkjl":
                    log.info("转科");
                    this.patientInfoService.transferDeptOrBed(bodyElement, deptCodeList);
                    break;
                case "zybz_cwsyjl":
                    log.info("转床");
                    this.patientInfoService.transferBed(bodyElement, deptCodeList);
                    break;
                case "zy_cydj":
                    log.info("患者出院开始同步");
                    this.patientInfoService.outPatient(bodyElement, deptCodeList);
                    break;
                case "zy_zyyz":
                    log.info("医嘱开始同步");
                    this.orderService.handleOrder(bodyElement);
                    break;
                case "zy_yzztgxjl":
                    //根本没用
                    log.info("医嘱状态更新");
                    this.orderService.handleOrderStateChange(bodyElement);
                    break;
                case "sm_ssjl":
                    log.info("手术信息同步");
                    this.patientInfoService.handleSyOperations(bodyElement, deptCodeList);
                    break;
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            status = "UNKNOWN_ERR";
            message = e.getMessage();
        }
        return getReturnResult(messageId, status, message);
    }

    private String getReturnResult(String messageId, String status, String message) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<YHYL MessageId=\"" + messageId + "\">\n\t<Result createTime=\"" + new Date().getTime() + "\" message=\"" + message + "\" status=\"" + status + "\"/>\n</YHYL>\n";
    }

    public List<String> getDeptCodeList() {
        if (StringUtils.isEmpty(this.deptCode) || !this.deptCodeList.isEmpty()) {
            return this.deptCodeList;
        }
        if (this.deptCode.contains("、")) {
            String[] split = this.deptCode.split("、");
            for (String s : split) {
                if (!StringUtils.isEmpty(s)) {
                    this.deptCodeList.add(s);
                }
            }
        } else {
            this.deptCodeList.add(this.deptCode);
        }
        return this.deptCodeList;
    }
}
