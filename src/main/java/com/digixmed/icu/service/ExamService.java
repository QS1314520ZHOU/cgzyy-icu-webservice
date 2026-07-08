package com.digixmed.icu.service;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.digixmed.icu.constant.Cache;
import com.digixmed.icu.constant.DocumentTypeEnum;
import com.digixmed.icu.constant.InterfaceCode;
import com.digixmed.icu.constant.MyConstants;
import com.digixmed.icu.dao.MongoDao;
import com.digixmed.icu.pojo.CustomElement;
import com.digixmed.icu.util.DataUtil;
import com.digixmed.icu.util.HttpUtil;
import com.digixmed.icu.viform.*;

import java.util.List;
import java.util.Optional;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class ExamService extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(ExamService.class);

    @Autowired
    MongoDao mongoDao;

    @Value("${digixmed.is-enable:false}")
    boolean isEnable;

    @Value("${digixmed.is-user-test-data:false}")
    boolean isUserTestData;

    @Value("${digixmed.soap-address:}")
    String soapAddress;

    public List<Element> documentSearch(String patientId, String visitNumber) {
        Document document;
        CustomElement requestElement = CustomElement.getInstance();
        requestElement.addHeaderElement("深医重症", "");
        requestElement.addBodyElement("DocumentRetrievalRt");
        Element reqBodyElement = requestElement.getBodyElement().element("DocumentRetrievalRt");
        requestElement.addElement(reqBodyElement, "PATPatientID", patientId);
        requestElement.addElement(reqBodyElement, "PAADMVisitNumber", visitNumber);
        requestElement.addElement(reqBodyElement, "DocumentType", DocumentTypeEnum.EXAM_REPORT.getCode());
        requestElement.addElement(reqBodyElement, "StartDate", "");
        requestElement.addElement(reqBodyElement, "EndDate", "");
        requestElement.addElement(reqBodyElement, "DocumentFormat", "xml");
        String xml = null;
        if (this.isEnable) {
            xml = requestData(requestElement.getElementAsXmlStr(), InterfaceCode.DOCUMENT_SEARCH.getCode());
            log.info("获取大项接口返回值：" + xml);
        } else if (this.isUserTestData) {
            xml = Cache.documentSearch;
        }
        if (!StringUtils.isEmpty(xml) && xml.contains("<HIPMessageServerResult>") && xml.contains("</HIPMessageServerResult")) {
            int startIndex = xml.indexOf("<HIPMessageServerResult>");
            int endIndex = xml.indexOf("</HIPMessageServerResult>") + "</HIPMessageServerResult>".length();
            xml = xml.substring(startIndex, endIndex);
        }
        Document document2 = parseDocument(xml, InterfaceCode.DOCUMENT_SEARCH);
        if (document2 == null || (document = parseDocument(document2.getRootElement().getText(), null)) == null) {
            return null;
        }
        Element body = document.getRootElement().element(CustomElement.BODY_ELE_NAME);
        String resultCode = getElementText(body, "ResultCode");
        if (!"0".equals(resultCode)) {
            String resultContent = getElementText(body, "ResultContent");
            log.error("返回失败原因:" + resultContent);
            return null;
        }
        Element bodyElement = document.getRootElement().element(CustomElement.BODY_ELE_NAME);
        Element documentRetrievalRp = getElement(bodyElement, "DocumentRetrievalRp");
        return getElements(documentRetrievalRp, "Documents", "Document");
    }

    public void documentInfoRequest(String patientId, String visitNumber, String documentId, InHospitalInfo patDocument) {
        Document document;
        String resultXml;
        CustomElement requestElement = CustomElement.getInstance();
        requestElement.addHeaderElement("", "");
        requestElement.addBodyElement("DocumentSearchRt");
        Element bodyElement = requestElement.getBodyElement().element("DocumentSearchRt");
        requestElement.addElement(bodyElement, "PATPatientID", patientId);
        requestElement.addElement(bodyElement, "PAADMVisitNumber", visitNumber);
        requestElement.addElement(bodyElement, "DocumentType", DocumentTypeEnum.EXAM_REPORT.getCode());
        requestElement.addElement(bodyElement, "DocumentID", documentId);
        String xml = null;
        if (this.isEnable) {
            xml = requestData(requestElement.getElementAsXmlStr(), InterfaceCode.DOCUMENT_READ.getCode());
        }
        if (!StringUtils.isEmpty(xml) && xml.contains("<HIPMessageServerResult>") && xml.contains("</HIPMessageServerResult")) {
            int startIndex = xml.indexOf("<HIPMessageServerResult>");
            int endIndex = xml.indexOf("</HIPMessageServerResult>") + "</HIPMessageServerResult>".length();
            xml = xml.substring(startIndex, endIndex);
        }
        log.info("文档调阅返回值：" + xml);
        if (StringUtils.isEmpty(xml)) {
            resultXml = "";
        } else {
            Document document2 = parseDocument(xml, InterfaceCode.DOCUMENT_READ);
            if (document2 == null || (document = parseDocument(document2.getRootElement().getText(), null)) == null) {
                return;
            }
            Element body = document.getRootElement().element(CustomElement.BODY_ELE_NAME);
            String resultCode = getElementText(body, "ResultCode");
            if (!"0".equals(resultCode)) {
                String resultContent = getElementText(body, "ResultContent");
                log.error("返回失败原因:" + resultContent);
                return;
            }
            Element documentSearchRp = getElement(body, "DocumentSearchRp");
            Element documents = getElement(documentSearchRp, "Documents");
            if (documents == null) {
                return;
            }
            Element document1 = getElement(documents, "Document");
            String xml2 = getElementText(document1, "DocumentContent");
            resultXml = Base64.decodeStr(xml2);
        }
        Document document3 = parseDocument(resultXml, InterfaceCode.DOCUMENT_READ);
        if (document3 == null) {
            return;
        }
        Element structureBody = document3.getRootElement().element("structuredBody");
        List<Element> sectionList = getElements(structureBody, "section");
        log.info("文档调阅返回值 section节点数量：" + sectionList.size());
        createOrSaveExam(sectionList, patientId, patDocument);
    }

    private void createOrSaveExam(List<Element> sectionList, String patientId, InHospitalInfo patDocument) {
        if (sectionList == null || sectionList.isEmpty()) {
            return;
        }
        Optional<Element> first = sectionList.stream().filter(section -> {
            return "S0061".equals(section.attribute("code").getValue());
        }).findFirst();
        if (first.isPresent()) {
            Element resultElement = first.get();
            log.info("大项解密后内容：" + resultElement.getText());
            String OrderID = getElementText(resultElement, "E25");
            if (null != this.mongoDao.getExamByOrderID(OrderID)) {
                return;
            }
            Exam exam = new Exam();
            exam.setHisPid(patientId);
            exam.setReportID(getElementText(resultElement, "E25"));
            exam.setOrderID(getElementText(resultElement, "E25"));
            exam.setCode(getElementText(resultElement, "E01"));
            exam.setName(getElementText(resultElement, "E02"));
            exam.setShortName(getElementText(resultElement, "E02"));
            exam.setOrderDept(getElementText(resultElement, "E27"));
            exam.setOrderDeptCode(getElementText(resultElement, "E26"));
            exam.setOrderDoctor(getElementText(resultElement, "E09"));
            exam.setOrderDeptCode(getElementText(resultElement, "E10"));
            exam.setSpecID(getElementText(resultElement, "E07"));
            exam.setSpecName(getElementText(resultElement, "E06"));
            exam.setCollectTime(getDateTime(resultElement, "E03", "E04"));
            exam.setRecvTime(getDateTime(resultElement, "E03", "E04"));
            exam.setAuthTime(getDateTime(resultElement, "E17", "E18"));
            exam.setAuthDoctor(getElementText(resultElement, "E20"));
            if (patDocument != null) {
                exam.setMrn(patDocument.getMrn());
            }
            this.mongoDao.save(exam);
            List<Element> examItemDetailEleList = getElements(resultElement, "section");
            for (Element elementItem : examItemDetailEleList) {
                log.info("小项解密后内容：" + elementItem.getText());
                ExamItem examItem = new ExamItem();
                examItem.setHisPid(patientId);
                examItem.setExamID(exam.getReportID());
                examItem.setReportID(exam.getReportID());
                examItem.setOrderID(exam.getOrderID());
                examItem.setItemCode(getElementText(elementItem, "E01"));
                examItem.setItemName(getElementText(elementItem, "E02"));
                examItem.setItemShortName(getElementText(elementItem, "E02"));
                examItem.setResult(getElementText(elementItem, "E03"));
                examItem.setSeriousFlag(getElementText(elementItem, "E07"));
                examItem.setUnit(getElementText(elementItem, "E04"));
                examItem.setPrintRange(getElementText(elementItem, "E06"));
                examItem.setNotes(getElementText(elementItem, "E10"));
                examItem.setAuthDoctor(exam.getAuthDoctor());
                examItem.setAuthTime(exam.getAuthTime());
                this.mongoDao.save(examItem);
            }
        }
    }

    public Document parseDocument(String xml, InterfaceCode interfaceCode) {
        if (StringUtils.isEmpty(xml)) {
            return null;
        }
        if (xml.contains("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")) {
            xml = xml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n", "");
        }
        try {
            return DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            if (interfaceCode != null) {
                log.error("解析报错接口名称:" + interfaceCode.getDesc() + ">>>" + xml);
            }
            e.printStackTrace();
            return null;
        }
    }

    private String requestData(String message, String serviceCode) {
        String messageBody = MyConstants.messageBody.replaceAll("messageBody", message).replaceAll("ServiceCode", serviceCode);
        try {
            if (StringUtils.isEmpty(this.soapAddress)) {
                this.soapAddress = MyConstants.backAddress;
            }
            log.info("soapAddress=" + this.soapAddress);
            log.info("获取大项接口入参：" + messageBody);
            String xml = HttpUtil.doPostSoap1_1(messageBody, this.soapAddress);
            if (xml.contains("<HIPMessageServerResult>") && xml.contains("</HIPMessageServerResult")) {
                int startIndex = xml.indexOf("<HIPMessageServerResult>");
                int endIndex = xml.indexOf("</HIPMessageServerResult>") + "</HIPMessageServerResult>".length();
                xml = xml.substring(startIndex, endIndex);
            }
            return xml;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void handlerExamRequest(Element bodyElement, List<String> deptCodeList) throws Exception {
        String value;
        String value2;
        List<Element> examRequest = bodyElement.elements("Component");
        if (CollectionUtils.isEmpty(examRequest)) {
            log.error("检验数据异常");
            return;
        }
        String pid = "";
        String deptCode = "";
        Exam exam = new Exam();
        for (Element element : examRequest) {
            String ComponentName = element.attribute("Name").getValue();
            if (StrUtil.isEmpty(ComponentName)) continue;
            ComponentName = ComponentName.toLowerCase();
            if ("tb_yjjy_jybg".equals(ComponentName)) {
                List<Element> elements = element.element("Entry").elements("Field");
                for (Element entry : elements) {
                    String name = entry.attribute("Name").getValue();
                    value2 = entry.attribute("Value").getValue();
                    if (StrUtil.isBlank(value2) || value2.contains("NULL")) continue;
                    switch (name) {
                        case "CBQBM":
                            deptCode = value2;
                            break;
                        case "CHZBS":
                            exam.setHisPid(value2);
                            pid = value2;
                            break;
                        case "CJYBGDBH":
                            exam.setReportID(value2);
                            exam.setOrderID(value2);
                            break;
                        case "CJYXMMC":
                            exam.setName(value2);
                            exam.setShortName(value2);
                            break;
                        case "CJYXMBM":
                            exam.setCode(value2);
                            break;
                        case "CBGKSMC":
                            exam.setOrderDept(value2);
                            break;
                        case "CBGKSDM":
                            exam.setOrderDeptCode(value2);
                            break;
                        case "CJYYSXM":
                            exam.setOrderDoctor(value2);
                            exam.setAuthDoctor(value2);
                            break;
                        case "CJYYSBM":
                            exam.setOrderDoctorID(value2);
                            break;
                        case "CJYBBH":
                            exam.setSpecID(value2);
                            break;
                        case "DBBCYRQSJ":
                            exam.setCollectTime(DataUtil.stringToDate(value2, "yyyy-MM-dd HH:mm:ss"));
                            break;
                        case "DJSBBRQSJ":
                            exam.setRecvTime(DataUtil.stringToDate(value2, "yyyy-MM-dd HH:mm:ss"));
                            break;
                        case "DJYRQ":
                            exam.setAuthTime(DataUtil.stringToDate(value2, "yyyy-MM-dd HH:mm:ss"));
                            break;
                        case "CZYH":
                            exam.setMrn(value2);
                            break;
                        case "CHZXM":
                            exam.setPatName(value2);
                            break;
                        case "CXBMC":
                            exam.setGender(value2);
                            break;
                    }
                }
                if (deptCodeList.contains(deptCode) && this.mongoDao.findIcuPatientInfoByPid(pid) != null) {
                    this.mongoDao.save(exam);
                }
            }
            if (!deptCodeList.contains(deptCode) || this.mongoDao.findIcuPatientInfoByPid(pid) == null) {
                continue;
            }
            if ("tb_yjjy_jyjg".equals(ComponentName)) {
                List<Element> elementItem = element.elements("Entry");
                if (CollectionUtils.isEmpty(elementItem)) {
                    log.error("数据异常,没有检验小项");
                    return;
                }
                for (Element item : elementItem) {
                    List<Element> fields = item.elements("Field");
                    ExamItem examItem = new ExamItem();
                    examItem.setHisPid(pid);
                    for (Element field : fields) {
                        String name2 = field.attribute("Name").getValue();
                        value = field.attribute("Value").getValue();
                        if (StrUtil.isBlank(value) || value.contains("NULL")) continue;
                        switch (name2) {
                            case "CJYBGDBH":
                                examItem.setTestItemID(value);
                                examItem.setExamID(value);
                                examItem.setReportID(value);
                                examItem.setOrderID(value);
                                break;
                            case "CJYXMBZBM":
                                examItem.setItemCode(value);
                                break;
                            case "CJYXMMC":
                                examItem.setItemName(value);
                                break;
                            case "CJYXMJC":
                                examItem.setItemShortName(value);
                                break;
                            case "CJYDLJG":
                                examItem.setResult(value);
                                break;
                            case "CJYJGBSF":
                                examItem.setAlmFlag(value);
                                examItem.setResultStatus(value);
                                break;
                            case "CJYDLJGJLDW":
                                examItem.setUnit(value);
                                break;
                            case "CCKZXX":
                                examItem.setReflow(value);
                                break;
                            case "CCKZSX":
                                examItem.setRefHigh(value);
                                break;
                            case "CWJZXX":
                                examItem.setSeriousLow(value);
                                break;
                            case "CWJZSX":
                                examItem.setSeriousHigh(value);
                                break;
                            case "DJYRQ":
                                examItem.setAuthTime(DataUtil.stringToDate(value, "yyyy-MM-dd HH:mm:ss"));
                                break;
                        }
                        this.mongoDao.save(examItem);
                    }
                }
            }
        }
    }

    public void handleReport(Element bodyElement, List<String> deptCodeList) {
        List<Element> examRequest = bodyElement.elements("Component");
        if (CollectionUtils.isEmpty(examRequest)) {
            log.error("检查数据异常");
            return;
        }
        String pid = null;
        String deptCode = null;
        String orderId = "";
        Report report = new Report();
        for (Element element : examRequest) {
            String ComponentName = element.attribute("Name").getValue();
            if (StrUtil.isEmpty(ComponentName)) continue;
            ComponentName = ComponentName.toLowerCase();
            if ("tb_yjjc_jcbg".equals(ComponentName)||ComponentName.contains("tb_yjjc_jcbg")) {
                List<Element> elements = element.element("Entry").elements("Field");
                if (CollectionUtils.isEmpty(elements)) {
                    log.error("数据异常,没有检查tb_yjjc_jcbg数据");
                    return;
                }
                for (Element field : elements) {
                    String name = field.attribute("Name").getValue();
                    String value = field.attribute("Value").getValue();
                    if (StrUtil.isBlank(value) || value.contains("NULL")) continue;
                    switch (name) {
                        case "CHZBS":
                            pid = value;
                            report.setPid(pid);
                            break;
                        case "CBQBM":
                            deptCode = value;
                            report.setPatDept(deptCode);
                            report.setOrderDept(deptCode);
                            break;
                        case "CDZSQDBH":
                            orderId = value;
                            report.setReportID(value);
                            report.setOrderID(value);
                            break;
                        case "CYXZD":
                            report.setDiagnose(value);
                            break;
                        case "CJCJGMC":
                            report.setConclusion(value);
                            break;
                        case "CJCBGJG_ZGTS":
                            //CJCBGJG_ZGTS 检查报告结果-主观提示    CJCBGJG_KGSJ  检査报告结果-客观所见
                            report.setReportDesc(value);
                            break;
                        case "CJCYSXM":
                            report.setExamDoctor(value);
                            break;
                        case "CJCYSBM":
                            report.setExamDoctorID(value);
                            break;
                        case "DJCBGRQ":
                            report.setReportTime(DataUtil.stringToDate(value, "yyyy-MM-dd HH:mm:ss"));
                            break;
                        case "DJCRQ":
                            report.setExamTime(DataUtil.stringToDate(value, "yyyy-MM-dd HH:mm:ss"));
                            break;
                        case "DJCBFHCRQ":
                            report.setRoom(value);
                            break;
                    }
                }

            }
            if (pid == null || deptCode == null) {
                return;
            }
            InHospitalInfo patient = this.mongoDao.findIcuPatientInfoByPid(pid);
            if (patient == null) {
                return;
            }
            if (!deptCodeList.contains(deptCode)) {
                return;
            }
            if ("tb_yjjc_bljg".equals(ComponentName)||ComponentName.contains("tb_yjjc_bljg")) {
                List<Element> elements = element.element("Entry").elements("Field");
                if (CollectionUtils.isEmpty(elements)) {
                    log.error("数据异常,没有检查tb_yjjc_bljg数据");
                    return;
                }
                for (Element field : elements) {
                    String name = field.attribute("Name").getValue();
                    String value = field.attribute("Value").getValue();
                    if (StrUtil.isBlank(value) || value.contains("NULL")) continue;
                    switch (name) {
                        case "CJCBWMC":
                            report.setBodyParts(value);
                            break;
                    }
                }

            }
        }
        Report report1 = this.mongoDao.findReport(pid, orderId);
        //有申请才能有检查结果
        if (report1 != null) {
            report.setBed(report1.getBed());
            report.setTitle(report1.getTitle());
            report.setExamName(report1.getExamName());
            report.setExamCode(report1.getExamCode());
            report.setOrderDoctor(report1.getOrderDoctor());
            report.setOrderDoctorID(report1.getOrderDoctorID());
            this.mongoDao.save(report);
        }
    }

    public void handleReportOpertion(Element message, List<String> deptCodeList) {
        List<Element> examRequest = message.elements("Component");
        if (CollectionUtils.isEmpty(examRequest)) {
            log.error("检查申请数据异常");
            return;
        }
        String pid = null;
        String deptCode = null;
        String orderId = "";
        String status = "";
        Report report = new Report();
        for (Element element : examRequest) {
            String ComponentName = element.attribute("Name").getValue();
            if (StrUtil.isEmpty(ComponentName)) continue;
            ComponentName = ComponentName.toLowerCase();
            if ("tb_sqd_jcsqd".equals(ComponentName)) {
                List<Element> elements = element.element("Entry").elements("Field");
                for (Element entry : elements) {
                    String name = entry.attribute("Name").getValue();
                    String value = entry.attribute("Value").getValue();
                    if (StrUtil.isBlank(value) || value.contains("NULL")) continue;
                    switch (name) {
                        case "CHZBS":
                            pid = value;
                            report.setPid(pid);
                            break;
                        case "CBQBM":
                            deptCode = value;
                            report.setPatDept(deptCode);
                            report.setOrderDept(deptCode);
                            break;
                        case "CBCH":
                            report.setBed(value);
                            break;
                        case "CDZSQDBH":
                            report.setReportID(value);
                            report.setOrderID(value);
                            orderId = value;
                            break;
                        case "CJCXMMC":
                            report.setTitle(value);
                            report.setExamName(value);
                            break;
                        case "CJCXMBM":
                            report.setExamCode(value);
                            break;
                        case "CSQYSBM":
                            report.setOrderDoctor(value);
                            break;
                        case "CSQYSXM":
                            report.setOrderDoctorID(value);
                            break;
                        case "BSJZT":
                            status = value;
                            break;
                    }
                }
            }
        }
        if (pid == null || deptCode == null) {
            return;
        }
        InHospitalInfo patient = this.mongoDao.findIcuPatientInfoByPid(pid);
        if (patient == null) {
            return;
        }
        if (!deptCodeList.contains(deptCode)) {
            return;
        }
        Report report1 = this.mongoDao.findReport(pid, orderId);
        if (report1 != null) {
            //申请取消
            if ("0".equals(status)) {
                this.mongoDao.deleteReport(report1.getId());
            } else {
                report.setId(report1.getId());
                this.mongoDao.save(report);
            }
        } else {
            if ("1".equals(status)) {
                this.mongoDao.save(report);
            }
        }

    }

    public void handleReportWJZDJ(Element message, List<String> deptCodeList) {
        List<Element> examRequest = message.elements("Component");
        if (CollectionUtils.isEmpty(examRequest)) {
            log.error("危急值数据异常");
            return;
        }
        String pid = null;
        String status = "";
        String id = "";
        VIICUEMERG viicuemerg = new VIICUEMERG();
        for (Element element : examRequest) {
            String ComponentName = element.attribute("Name").getValue();
            if (StrUtil.isEmpty(ComponentName)) continue;
            ComponentName = ComponentName.toLowerCase();
            if ("tb_ylzl_wjzdj".equals(ComponentName)) {
                List<Element> elements = element.element("Entry").elements("Field");
                for (Element entry : elements) {
                    String name = entry.attribute("Name").getValue();
                    String value = entry.attribute("Value").getValue();
                    if (StrUtil.isBlank(value) || value.contains("NULL")) continue;
                    switch (name) {
                        case "CHZBS":
                            pid = value;
                            viicuemerg.setHisPid(pid);
                            break;
                        case "CBQDM":
                            if (!deptCodeList.contains(value)) {
                                return;
                            }
                            viicuemerg.setDeptCode(value);
                            break;
                        case "CBQMC":
                            viicuemerg.setDeptName(value);
                            break;
                        case "CJCJYBGDH":
                            viicuemerg.setDataID(value);
                            viicuemerg.setTestItemID(value);
                            viicuemerg.setOrderID(value);
                            id=value;
                            break;
                        case "CHZXM":
                           viicuemerg.setPatName(value);
                            break;
                        case "DBGRQSJ":
                            viicuemerg.setRepTime(DataUtil.stringToDate(value, "yyyy-MM-dd HH:mm:ss"));
                            break;
                        case "CJYGS":
                            viicuemerg.setMsg(value);
                            break;
                        case "CJCXMMC":
                           viicuemerg.setBigItemName(value);
                            break;
                        case "BSJZT":
                            status = value;
                            break;
                        case "CWJZZTDM":
                            viicuemerg.setSeriousFlag(value);
                            break;
                        case "CFXYSXM":
                            viicuemerg.setReqName(value);
                            break;
                        case "CFXYSBM":
                            viicuemerg.setReqDr(value);
                            break;
                    }
                }
            }
            if ("tb_ylzl_wjzdjmx".equals(ComponentName)) {
                List<Element> elements = element.element("Entry").elements("Field");
                for (Element entry : elements) {
                    String name = entry.attribute("Name").getValue();
                    String value = entry.attribute("Value").getValue();
                    if (StrUtil.isBlank(value) || value.contains("NULL")) continue;
                    switch (name) {
                        case "CWJZJCJG":
                            viicuemerg.setResult(value);
                            break;
                        case "CWJZJCXMMC":
                            viicuemerg.setItemName(value);
                            break;
                        case "CWJZXX":
                            viicuemerg.setSeriousLow(value);
                            break;
                        case "CWJZSX":
                            viicuemerg.setSeriousHigh(value);
                            break;
                    }
                }
            }
        }
        if (pid == null) {
            return;
        }
        InHospitalInfo patient = this.mongoDao.findIcuPatientInfoByPid(pid);
        if (patient == null) {
            return;
        }
        VIICUEMERG viicuemerg1 = this.mongoDao.findVIICUEMERG(pid, id);
        if("1".equals(status)){
            if(viicuemerg1!=null){
                viicuemerg.setId(viicuemerg1.getId());
            }
            this.mongoDao.save(viicuemerg);

        }else {
            if(viicuemerg1!=null){
                this.mongoDao.deleteVIICUEMERG(viicuemerg1.getId());
            }
        }
    }
}