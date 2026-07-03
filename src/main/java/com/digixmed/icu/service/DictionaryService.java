package com.digixmed.icu.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.digixmed.icu.constant.InterfaceCode;
import com.digixmed.icu.dao.MongoDao;
import com.digixmed.icu.util.DataUtil;
import com.digixmed.icu.util.HttpUtil;
import com.digixmed.icu.viform.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DictionaryService {
    private static final Logger log = LoggerFactory.getLogger(DictionaryService.class);

    @Autowired
    MongoDao mongoDao;
    @Autowired
    ExamService examService;

    @Value("${digixmed.jiGou-code:}")
    String dictCode;

    @Value("${digixmed.dept-code:}")
    String deptCode;

    @Value("${digixmed.urlHead:}")
    String URL;

    @Value("${digixmed.yhipSender:}")
    String yhipSender;
    @Value("${digixmed.deptUrl:}")
    String deptUrl;
    @Value("${digixmed.staffUrl:}")
    String staffUrl;
    @Value("${digixmed.freqUrl:}")
    String freqUrl;
    @Value("${digixmed.methodUrl:}")
    String methodUrl;
    @Value("${digixmed.patientUrl:}")
    String patientUrl;
    @Value("${digixmed.icd10Url:}")
    String icd10Url;

    @Value("${digixmed.urlPC:}")
    String URLPC;
    List<String> dictionaryList = new ArrayList(Arrays.asList("321012468946766", "321012468953472"));
    List<String> deptList = new ArrayList(Arrays.asList("2219", "2006"));

    public void synDictData() {
        this.dictionaryList = getDictionaryList();
        log.info("部门数量：" + this.dictionaryList.size());
        log.info("    " + this.dictionaryList.toString());
        this.deptList = getDeptList();
        log.info("科室数量：" + this.deptList.size());
        log.info("    " + this.deptList.toString());
        for (String dict : this.dictionaryList) {
            log.info("开始同步部门 " + dict + "人员信息表");
            try {
                synStaffData(dict);
            } catch (Exception e) {
                log.info("部门 " + dict + "人员信息表同步异常！  " + e.getMessage());
            }
            log.info("开始同步部门 " + dict + "科室信息表");
            try {
                synDeptData(dict);
            } catch (Exception e2) {
                log.info("部门 " + dict + "科室信息表同步异常！  " + e2.getMessage());
            }
        }
        try {
            log.info("开始同步用药频次表");
            synPinCi();
        } catch (Exception e3) {
            log.info("频次表同步异常！  " + e3.getMessage());
        }
        try {
            log.info("开始同步药品用法");
            synYongFa();
        } catch (Exception e4) {
            log.info("药品用法同步异常！  " + e4.getMessage());
        }
        try {
            log.info("开始同步ICD10");
            synICD10();
        } catch (Exception e4) {
            log.info("ICD10同步异常！  " + e4.getMessage());
        }
    }

    private void synICD10() {
        String response = responseXML(1);
        String staffUrl = this.URL + this.icd10Url;
        log.info("response:" + response);
        log.info("staffUrl:" + staffUrl);
        String request = HttpUtil.doPostXML(response, staffUrl,this.yhipSender);
        if (request==null){
            log.error(InterfaceCode.ICD10+"请求数据为空");
            return;
        }
        List<Icd10> icd10All=this.mongoDao.findICD10All();
        Map<String, Icd10> icd10Map=null;
        if(CollectionUtil.isNotEmpty(icd10All)){
            icd10Map = icd10All.stream()
                    .collect(Collectors.toMap(
                            Icd10::getIcdCode,       // 1. 提取 key (假设 pid 是 String 类型)
                            Function.identity(),          // 2. value 就是对象本身
                            (existing, replacement) -> existing // 3. 遇到重复时的合并策略：保留旧值
                    ));
        }
        try {
            Document messageData = examService.parseDocument(request, InterfaceCode.ICD10);
            Element bodyElement = messageData.getRootElement().element("Result");
            String count = bodyElement.attribute("count").getValue();
            log.info(InterfaceCode.ICD10+"count:" + count);
            Integer size=0;
            try {
                size=Integer.parseInt(count);
            }catch (Exception e){
                size=50;
            }
            log.info(InterfaceCode.ICD10+"size:" + size);
            int page=1;
            while (page<=(int)Math.ceil((double)size/15)) {
                String responseWhile = responseXML(page);
                String requestWhile = HttpUtil.doPostXML(responseWhile, staffUrl, this.yhipSender);
                if (requestWhile == null) {
                    log.error(InterfaceCode.ICD10 + "请求数据为空");
                    continue;
                }
                Document messageDataWhile = examService.parseDocument(requestWhile, InterfaceCode.ICD10);
                Element bodyElementWhile = messageDataWhile.getRootElement().element("Result");
                List<Element> elementItem = bodyElementWhile.elements("Entry");
                if (elementItem == null) {
                    log.error(InterfaceCode.ICD10 + "请求接口解析错误:bodyElement");
                    continue;
                }
                for (Element element : elementItem) {
                    List<Element> fields = element.elements("Field");
                    if (CollectionUtil.isEmpty(fields)) {
                        log.error(InterfaceCode.ICD10 + "请求接口解析错误:fields");
                        continue;
                    }
                    Icd10 icd10 = new Icd10();
                    String status = "";
                    for (Element field : fields) {
                        String name = field.attribute("Name").getValue();
                        String value = field.attribute("Value").getValue();
                        if (StrUtil.isBlank(value) || value.contains("NULL")) continue;
                        switch (name) {
                            case "CJBZDBM":
                                icd10.setIcdCode(value);
                                break;
                            case "CJBZDMC":
                                icd10.setIcdName(value);
                                break;
                            case "BSJZT":
                                status = value;
                                break;
                        }
                    }
                    if ("1".equals(status)) {
                        if (icd10Map == null) {
                            this.mongoDao.save(icd10);
                        } else {
                            Icd10 icd101 = icd10Map.get(icd10.getIcdCode());
                            if (icd101 == null) {
                                this.mongoDao.save(icd10);
                            }
                        }
                    }
                }
                page++;
            }
        }catch (Exception e ){
            log.error(InterfaceCode.ICD10+"接口请求异常:"+e.getMessage());
        }
    }

    private void synYongFa() {
        String response = responseXML(1);
        String staffUrl = this.URL + this.methodUrl;
        log.info("response:" + response);
        log.info("staffUrl:" + staffUrl);
        String request = HttpUtil.doPostXML(response, staffUrl,this.yhipSender);
        if (request==null){
            log.error(InterfaceCode.DRUG_METHOD_DICT+"请求数据为空");
            return;
        }
        List<DrugMethod> yfAll=this.mongoDao.findYfAll();
        Map<String, DrugMethod> yfmap=null;
        if(CollectionUtil.isNotEmpty(yfmap)){
            yfmap = yfAll.stream()
                    .collect(Collectors.toMap(
                            DrugMethod::getCode,       // 1. 提取 key (假设 pid 是 String 类型)
                            Function.identity(),          // 2. value 就是对象本身
                            (existing, replacement) -> existing // 3. 遇到重复时的合并策略：保留旧值
                    ));
        }
        try {
            Document messageData = examService.parseDocument(request, InterfaceCode.DRUG_METHOD_DICT);
            Element bodyElement = messageData.getRootElement().element("Result");
            String count = bodyElement.attribute("count").getValue();
            log.info(InterfaceCode.DRUG_METHOD_DICT+"count:" + count);
            Integer size=0;
            try {
                size=Integer.parseInt(count);
            }catch (Exception e){
                size=50;
            }
            log.info(InterfaceCode.DRUG_METHOD_DICT+"size:" + size);
            int page=1;
            while (page<=(int)Math.ceil((double)size/15)) {
                String responseWhile = responseXML(page);
                String requestWhile = HttpUtil.doPostXML(responseWhile, staffUrl, this.yhipSender);
                if (requestWhile == null) {
                    log.error(InterfaceCode.DRUG_METHOD_DICT + "请求数据为空");
                    continue;
                }
                Document messageDataWhile = examService.parseDocument(requestWhile, InterfaceCode.DRUG_METHOD_DICT);
                Element bodyElementWhile = messageDataWhile.getRootElement().element("Result");
                List<Element> elementItem = bodyElementWhile.elements("Entry");
                if (elementItem == null) {
                    log.error(InterfaceCode.DRUG_METHOD_DICT + "请求接口解析错误:bodyElement");
                    continue;
                }
                for (Element element : elementItem) {
                    List<Element> fields = element.elements("Field");
                    if (CollectionUtil.isEmpty(fields)) {
                        log.error(InterfaceCode.DRUG_METHOD_DICT + "请求接口解析错误:fields");
                        continue;
                    }
                    DrugMethod method = new DrugMethod();
                    String status = "";
                    for (Element field : fields) {
                        String name = field.attribute("Name").getValue();
                        String value = field.attribute("Value").getValue();
                        if (StrUtil.isBlank(value) || value.contains("NULL")) continue;
                        switch (name) {
                            case "CSRM":
                                method.setName(value);
                                break;
                            case "CZJLSH":
                                method.setMethodId(value);
                                break;
                            case "IYYTJBM":
                                method.setCode(value);
                                break;
                            case "BSJZT":
                                status = value;
                                break;
                        }
                    }
                    if ("1".equals(status)) {
                        if (yfmap == null) {
                            this.mongoDao.save(method);
                        } else {
                            DrugMethod method1 = yfmap.get(method.getCode());
                            if (method1 == null) {
                                this.mongoDao.save(method);
                            }
                        }
                    }
                }
                page++;
            }
        }catch (Exception e ){
            log.error(InterfaceCode.DRUG_METHOD_DICT+"接口请求异常:"+e.getMessage());
        }
    }

    private void synPinCi() {
        String response = responseXML(1);
        String staffUrl = this.URL + this.freqUrl;
        log.info("response:" + response);
        log.info("staffUrl:" + staffUrl);
        String request = HttpUtil.doPostXML(response, staffUrl,this.yhipSender);
        if (request==null){
            log.error(InterfaceCode.FREQ_DICT+"请求数据为空");
            return;
        }
        List<DrugFreq> freqAll=this.mongoDao.findFreqAll();
        Map<String, DrugFreq> freqMap=null;
        if(CollectionUtil.isNotEmpty(freqMap)){
            freqMap = freqAll.stream()
                    .collect(Collectors.toMap(
                            DrugFreq::getFreqName,       // 1. 提取 key (假设 pid 是 String 类型)
                            Function.identity(),          // 2. value 就是对象本身
                            (existing, replacement) -> existing // 3. 遇到重复时的合并策略：保留旧值
                    ));
        }
        try {
            Document messageData = examService.parseDocument(request, InterfaceCode.FREQ_DICT);
            Element bodyElement = messageData.getRootElement().element("Result");
            String count = bodyElement.attribute("count").getValue();
            log.info(InterfaceCode.FREQ_DICT+"count:" + count);
            Integer size=0;
            try {
                size=Integer.parseInt(count);
            }catch (Exception e){
                size=50;
            }
            log.info(InterfaceCode.FREQ_DICT+"size:" + size);
            int page=1;
            while (page<=(int)Math.ceil((double)size/15)){
                String responseWhile = responseXML(page);
                String requestWhile = HttpUtil.doPostXML(responseWhile, staffUrl,this.yhipSender);
                if (requestWhile==null){
                    log.error(InterfaceCode.FREQ_DICT+"请求数据为空");
                    continue;
                }
                Document messageDataWhile = examService.parseDocument(requestWhile, InterfaceCode.FREQ_DICT);
                Element bodyElementWhile = messageDataWhile.getRootElement().element("Result");
                List<Element> elementItem = bodyElementWhile.elements("Entry");
                if(elementItem==null){
                    log.error(InterfaceCode.FREQ_DICT+"请求接口解析错误:bodyElement");
                    continue;
                }
                for (Element element : elementItem) {
                    List<Element> fields = element.elements("Field");
                    if(CollectionUtil.isEmpty(fields)){
                        log.error(InterfaceCode.FREQ_DICT+"请求接口解析错误:fields");
                        continue;
                    }
                    DrugFreq drugFreq = new DrugFreq();
                    String status="";
                    for (Element field : fields) {
                        String name = field.attribute("Name").getValue();
                        String value = field.attribute("Value").getValue();
                        if(StrUtil.isBlank(value)||value.contains("NULL"))continue;
                        switch (name){
                            case "CMC":
                                drugFreq.setDesc(value);
                                break;
                            case "CSRM":
                                drugFreq.setFreqName(value);
                                break;
                            case "BSJZT":
                                status=value;
                                break;
                        }
                    }
                    if("1".equals(status)){
                        if(freqMap==null){
                            this.mongoDao.save(drugFreq);
                        }else {
                            DrugFreq drugFreq1 = freqMap.get(drugFreq.getFreqName());
                            if(drugFreq1==null){
                                this.mongoDao.save(drugFreq);
                            }
                        }
                    }
                }
                page++;
            }
        }catch (Exception e ){
            log.error(InterfaceCode.FREQ_DICT+"接口请求异常:"+e.getMessage());
        }
    }

    private void synDeptData(String dict) {
        String response = responseXML(1);
        String staffUrl = this.URL + this.deptUrl;
        log.info("response:" + response);
        log.info("staffUrl:" + staffUrl);
        String request = HttpUtil.doPostXML(response, staffUrl,this.yhipSender);
        if (request==null){
            log.error(InterfaceCode.DEPT_DICT+"请求数据为空");
            return;
        }
        List<DeptInfo> deptAll=this.mongoDao.findDeptAll();
        Map<String, DeptInfo> deptInfoMap=null;
        if(CollectionUtil.isNotEmpty(deptAll)){
            deptInfoMap = deptAll.stream()
                    .collect(Collectors.toMap(
                            DeptInfo::getDeptCode,       // 1. 提取 key (假设 pid 是 String 类型)
                            Function.identity(),          // 2. value 就是对象本身
                            (existing, replacement) -> existing // 3. 遇到重复时的合并策略：保留旧值
                    ));
        }
        try {
            Document messageData = examService.parseDocument(request, InterfaceCode.DEPT_DICT);
            Element bodyElement = messageData.getRootElement().element("Result");
            String count = bodyElement.attribute("count").getValue();
            log.info(InterfaceCode.DEPT_DICT+"count:" + count);
            Integer size=0;
            try {
                size=Integer.parseInt(count);
            }catch (Exception e){
                size=50;
            }
            log.info(InterfaceCode.DEPT_DICT+"size:" + size);
            int page=1;
            while (page<=(int)Math.ceil((double)size/15)){
                String responseWhile = responseXML(page);
                String requestWhile = HttpUtil.doPostXML(responseWhile, staffUrl,this.yhipSender);
                if (requestWhile==null){
                    log.error(InterfaceCode.DEPT_DICT+"请求数据为空");
                    continue;
                }
                Document messageDataWhile = examService.parseDocument(requestWhile, InterfaceCode.DEPT_DICT);
                Element bodyElementWhile = messageDataWhile.getRootElement().element("Result");
                List<Element> elementItem = bodyElementWhile.elements("Entry");
                if(elementItem==null){
                    log.error(InterfaceCode.DEPT_DICT+"请求接口解析错误:bodyElement");
                    continue;
                }
                for (Element element : elementItem) {
                    List<Element> fields = element.elements("Field");
                    if(CollectionUtil.isEmpty(fields)){
                        log.error(InterfaceCode.DEPT_DICT+"请求接口解析错误:fields");
                        continue;
                    }
                    DeptInfo deptInfo = new DeptInfo();
                    String status="";
                    for (Element field : fields) {
                        String name = field.attribute("Name").getValue();
                        String value = field.attribute("Value").getValue();
                        if(StrUtil.isBlank(value)||value.contains("NULL"))continue;
                        switch (name){
                            case "CKSBM":
                                deptInfo.setDeptCode(value);
                                break;
                            case "CKSMC":
                                deptInfo.setDeptName(value);
                                break;
                            case "BSJZT":
                                status=value;
                                break;
                        }
                    }
                    if("1".equals(status)){
                        if(deptInfoMap==null){
                            this.mongoDao.save(deptInfo);
                        }else {
                            DeptInfo deptInfo1 = deptInfoMap.get(deptInfo.getDeptCode());
                            if(deptInfo1==null){
                                this.mongoDao.save(deptInfo);
                            }
                        }
                    }
                }
                page++;
            }
        }catch (Exception e ){
            log.error(InterfaceCode.DEPT_DICT+"接口请求异常:"+e.getMessage());
        }
    }

    private void synStaffData(String dict) {
        String response = responseXML(1);
        String staffUrl = this.URL + this.staffUrl;
        log.info("response:" + response);
        log.info("staffUrl:" + staffUrl);
        String request = HttpUtil.doPostXML(response, staffUrl,this.yhipSender);
        if (request==null){
            log.error(InterfaceCode.STAFF_DICT+"请求数据为空");
            return;
        }
        List<StaffInfo> staffAll=this.mongoDao.findStaffAll();
        Map<String, StaffInfo> staffInfoMap=null;
        if(CollectionUtil.isNotEmpty(staffAll)){
            staffInfoMap = staffAll.stream()
                    .collect(Collectors.toMap(
                            StaffInfo::getCode,       // 1. 提取 key (假设 pid 是 String 类型)
                            Function.identity(),          // 2. value 就是对象本身
                            (existing, replacement) -> existing // 3. 遇到重复时的合并策略：保留旧值
                    ));
        }
        try {
            Document messageData = examService.parseDocument(request, InterfaceCode.STAFF_DICT);
            Element bodyElement = messageData.getRootElement().element("Result");
            String count = bodyElement.attribute("count").getValue();
            log.info(InterfaceCode.STAFF_DICT+"count:" + count);
            Integer size=0;
            try {
               size=Integer.parseInt(count);
            }catch (Exception e){
                size=50;
            }
            log.info(InterfaceCode.STAFF_DICT+"size:" + size);
            int page=1;
            while (page<=(int)Math.ceil((double)size/15)){
                String responseWhile = responseXML(page);
                String requestWhile = HttpUtil.doPostXML(responseWhile, staffUrl,this.yhipSender);
                if (requestWhile==null){
                    log.error(InterfaceCode.STAFF_DICT+"请求数据为空");
                    continue;
                }
                Document messageDataWhile = examService.parseDocument(requestWhile, InterfaceCode.STAFF_DICT);
                Element bodyElementWhile = messageDataWhile.getRootElement().element("Result");
                List<Element> elementItem = bodyElementWhile.elements("Entry");
                if(elementItem==null){
                    log.error(InterfaceCode.STAFF_DICT+"请求接口解析错误:bodyElement");
                    continue;
                }
                for (Element element : elementItem) {
                    List<Element> fields = element.elements("Field");
                    if(CollectionUtil.isEmpty(fields)){
                        log.error(InterfaceCode.STAFF_DICT+"请求接口解析错误:fields");
                        continue;
                    }
                    StaffInfo staffInfo = new StaffInfo();
                    String status="";
                    for (Element field : fields) {
                        String name = field.attribute("Name").getValue();
                        String value = field.attribute("Value").getValue();
                        if(StrUtil.isBlank(value)||value.contains("NULL"))continue;
                        switch (name){
                            case "CRYBH":
                                staffInfo.setCode(value);
                                break;
                            case "CKSBM":
                                staffInfo.setDeptCode(value);
                                break;
                            case "CXM":
                                staffInfo.setName(value);
                            case "CXBMC":
                                staffInfo.setSex("男性".equals(value)?"男":"女");
                                break;
                            case "BSJZT":
                                status=value;
                                break;
                        }
                    }
                    staffInfo.setDutyType("护士");
                    if("1".equals(status)){
                        if(staffInfoMap==null){
                            this.mongoDao.save(staffInfo);
                        }else {
                            StaffInfo staffInfo1 = staffInfoMap.get(staffInfo.getCode());
                            if(staffInfo1==null){
                                this.mongoDao.save(staffInfo);
                            }
                        }
                    }
                }
                page++;
            }
        }catch (Exception e ){
            log.error(InterfaceCode.STAFF_DICT+"接口请求异常:"+e.getMessage());
        }
    }

    private String getResponse(String dict, int index) {
        return "{\n    \"condition\": {\n        \"CJGDM\": \"" + dict + "\"\n    },\n    \"page\": {\n        \"index\":" + index + ",\n        \"size\": 50\n    }\n}";
    }

    private String getResponsePC(int index) {
        return "{\n    \"condition\": {\n        \"BENABLE\": \"1\"\n    },\n    \"page\": {\n        \"index\":" + index + ",\n        \"size\": 50\n    }\n}";
    }

    public List<String> getDictionaryList() {
        if (StringUtils.isEmpty(this.dictCode)) {
            return this.dictionaryList;
        }
        this.dictionaryList.clear();
        if (this.dictCode.contains("、")) {
            String[] split = this.dictCode.split("、");
            for (String s : split) {
                if (!StringUtils.isEmpty(s) && !this.dictionaryList.contains(s)) {
                    this.dictionaryList.add(s);
                }
            }
        } else {
            this.dictionaryList.add(this.dictCode);
        }
        return this.dictionaryList;
    }

    public List<String> getDeptList() {
        if (StringUtils.isEmpty(this.deptCode)) {
            return this.deptList;
        }
        this.deptList.clear();
        if (this.deptCode.contains("、")) {
            String[] split = this.deptCode.split("、");
            for (String s : split) {
                if (!StringUtils.isEmpty(s) && !this.deptList.contains(s)) {
                    this.deptList.add(s);
                }
            }
        } else {
            this.deptList.add(this.deptCode);
        }
        return this.deptList;
    }

    public void synPatientData() {
        List<InHospitalInfo> icuPatientInfoByZyzt = this.mongoDao.getIcuPatientInfoByZyzt();
        if(CollectionUtil.isEmpty(icuPatientInfoByZyzt)){
            log.info("没有在科患者");
            return ;
        }
        // (v1, v2) -> v1 表示：如果遇到重复的 key，直接保留第一个值（丢弃后面的）
        Map<String, InHospitalInfo> patientMap = icuPatientInfoByZyzt.stream()
                .collect(Collectors.toMap(
                        InHospitalInfo::getPid,       // 1. 提取 key (假设 pid 是 String 类型)
                        Function.identity(),          // 2. value 就是对象本身
                        (existing, replacement) -> existing // 3. 遇到重复时的合并策略：保留旧值
                ));
        for (InHospitalInfo inHospitalInfo : icuPatientInfoByZyzt) {
            String response= responsePatien(inHospitalInfo.getPid());
            String staffUrl = this.URL + this.patientUrl;
            log.info("response:" + response);
            log.info("staffUrl:" + staffUrl);
            String request = HttpUtil.doPostXML(response, staffUrl,this.yhipSender);
            log.info("数据内容：[[[" + request + "]]");
            if (request==null){
                log.error("患者信息请求数据为空");
                continue;
            }
            try {
                Document messageData = examService.parseDocument(request, InterfaceCode.PATIENT_INFO);
                Element bodyElement = messageData.getRootElement().element("Result").element("Entry");
//                List<Node> nodes = messageData.selectNodes("//YHYL/Result/Entry/Field");
                if(bodyElement==null){
                    log.error("患者信息请求接口解析错误:bodyElement");
//                    log.error("患者信息请求接口解析错误：//YHYL/Result/Entry/Field");
                    continue;
                }
                List<Element> fields = bodyElement.elements("Field");
                if(CollectionUtil.isEmpty(fields)){
                    log.error("患者信息请求接口解析错误:fields");
//                    log.error("患者信息请求接口解析错误：//YHYL/Result/Entry/Field");
                    continue;
                }
                String pid="";
                String tel="";
                String address="";
                Date dob=null;
                String idcardno="";
                String contactsName="";
                String contactsRelation="";
                String contactsPhone="";
                for (Element field : fields) {
                    String name = field.attribute("Name").getValue();
                    String value = field.attribute("Value").getValue();
                    if(StrUtil.isBlank(value)||value.contains("NULL"))continue;
                    switch (name){
                        case "czjlsh":
                            pid=value;
                            break;
                        case "cdhhm":
                            tel=value;
                            break;
                        case "cjtzz":
                            address=value;
                            break;
                        case "cjtdzyxzdm":
                            address+=value;
                            break;
                        case "cjtdzyxzmc":
                            address+=value;
                            break;
                        case "cjtdzycdm":
                            address+=value;
                            break;
                        case "cjtdzycmc":
                            address+=value;
                            break;
                        case "cjtdzmph":
                            address+=value;
                            break;
                        case "dcsrq":
                            dob= DataUtil.stringToDate(value, "yyyy-MM-dd");
                            break;
                        case "csfzhm":
                            idcardno=value;
                            break;
                        case "cxgrxm":
                            contactsName=value;
                            break;
                        case "clxrgxmc":
                            contactsRelation=value;
                            break;
                        case "clxrdh":
                            contactsPhone=value;
                            break;
                    }
                }
                InHospitalInfo inHospitalInfo1 = patientMap.get(pid);
                if(inHospitalInfo1==null)continue;
                inHospitalInfo.setTel(tel);
                inHospitalInfo.setAddress(address);
                inHospitalInfo.setDob(dob);
                inHospitalInfo.setIdcardno(idcardno);
                inHospitalInfo.setContactsName(contactsName);
                inHospitalInfo.setContactsRelation(contactsRelation);
                inHospitalInfo.setContactsPhone(contactsPhone);
                this.mongoDao.save(inHospitalInfo);
            }catch (Exception e ){
                log.error("接口请求异常:"+e.getMessage());

            }
        }
    }

    private String responsePatien(String pid) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<YHYL MessageId=\"000000001\" Action=\"zsy_hzjbxx\" Sender=\"ECIS\">\n" +
                "\t<Message CreateTime=\"\">\n" +
                "\t\t<Component Name=\"tb_zsy_hzjbxx\" Option=\"page\" Page=\"1\" Size=\"15\" PK=\"czjlsh\">\n" +
                "\t\t\t<Entry>\n" +
                "\t\t\t\t<Field Condition=\"asc\" Name=\"_NAV_ORDER_F_\" Value=\"\" Explain=\"默认排序字段\" Type=\"nvarchar\" Required=\"true\"/>\n" +
                "\t\t\t\t<Field Condition=\"eq\" Name=\"CZJLSH\" Value=\""+pid+"\" Explain=\"患者基本信息的唯一标识\" Type=\"varchar\" Required=\"true\" Len=\"200\"/>\n" +
                "\t\t\t</Entry>\n" +
                "\t\t</Component>\n" +
                "\t</Message>\n" +
                "</YHYL>";
    }
    private String responseXML(int page) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<YHYL>\n" +
                "    <Message>\n" +
                "        <Component  Option=\"page\" Page=\""+page+"\" Size=\"15\" PK=\"CKSLSH\">\n" +
                "            <Entry>\n" +
                "            </Entry>\n" +
                "        </Component>\n" +
                "    </Message>\n" +
                "</YHYL>";
    }

    public void isSynInPatient(InHospitalInfo inHospitalInfo) {
        if (inHospitalInfo==null)return;
        Date admitTime = inHospitalInfo.getAdmitTime();
        if(admitTime==null)return;
        Patient admittedPatient = this.mongoDao.findAdmittedPatient(inHospitalInfo.getMrn(),List.of("admitted","wait_admitted"));
        //为空说明科室目前没有患者
        if (admittedPatient==null){
                synPatient("",inHospitalInfo);
        }else{
            //todo zybr里面有patient,需要通过入科时间去匹配是否已经在科室了，在科了就不需要新增了
            //todo 如果入科时间变动了怎么办？ 现确定的是入科时间在系统基本信息里面修改了怎么处理？ 新增一个中间表呢来处理呢？
            //todo 用中间表保存zybr.id和patient.id+patient的创建时间，当入科时间不匹配时通过中间表判断 加上创建时间
            //todo 当入科时间不匹配时,可能出现手动在基本信息里面修改了，所以先判断入科时间没有在判断中间表
            Date icuAdmissionTime = admittedPatient.getIcuAdmissionTime();
            if(icuAdmissionTime==null)return;
            if(admitTime.getTime()!=icuAdmissionTime.getTime()){
                ZybrToPatientLog zybrToPatientLog = this.mongoDao.findZybrToPatientLog(admittedPatient.getId(), inHospitalInfo.getId(), admittedPatient.getCreatedTime());
                if(zybrToPatientLog==null){
                    synPatient("",inHospitalInfo);
                }
            }else {
                String status = admittedPatient.getStatus();
                if("wait_admitted".equals(status)){
                    synPatient(admittedPatient.getId(),inHospitalInfo);
                }
            }
        }
    }

    private void synPatient(String pid,InHospitalInfo inHospitalInfo) {
        Patient patient = new Patient();
        if(StrUtil.isNotBlank(pid)){
            patient.setId(pid);
        }
        patient.setHisPid(inHospitalInfo.getPid());
        patient.setMrn(inHospitalInfo.getMrn());
        patient.setCreatedTime(new Date());
        patient.setAdmissionTime(inHospitalInfo.getInPatientTime());
        patient.setName(inHospitalInfo.getName());
        patient.setBirthday(inHospitalInfo.getDob());
        patient.setGender("男".equals(inHospitalInfo.getGender())?"Male":"Female");
        //todo 注意类型
        patient.setAllergicRecordList(new ArrayList<>());
        patient.setHospitalTime(inHospitalInfo.getZycs());
        patient.setHisBed(inHospitalInfo.getBed());
        patient.setBedTime(inHospitalInfo.getAdmitTime());
        patient.setStatus("admitted");
        patient.setIcuAdmissionTime(inHospitalInfo.getAdmitTime());
        patient.setHisAdmissionTime(inHospitalInfo.getAdmitTime());
        //todo 注意类型
        ArrayList<Date> dates = new ArrayList<>();
        dates.add(inHospitalInfo.getAdmitTime());
        patient.setHisAdmissionTimeList(dates);
        patient.setAdmissionSource(inHospitalInfo.getAdmissionSource());
        patient.setAdmissionPlan("计划转入");
        patient.setClinicalDiagnosisTime(inHospitalInfo.getAdmitTime());
        patient.setInsuranceType(inHospitalInfo.getYblx());
        patient.setDept(inHospitalInfo.getDept());
        patient.setDeptCode(inHospitalInfo.getDeptCode());
        patient.setDeptCode2(inHospitalInfo.getDeptCode());
        patient.setChiefComplaint(inHospitalInfo.getZhushu());
        patient.setBedDoctorId(inHospitalInfo.getDoctorID());
        //todo  注意调整
        patient.setAdmissionType(inHospitalInfo.getAdmissionType());
        patient.setDischargedDiagnosisIcd(new ArrayList<>());
        patient.setMerge(false);
        patient.setAllowReturn(false);
        patient.setMeetAdmitRequirement(true);
        patient.setMeetDischargeRequirement(true);
        //todo 注意类型
        patient.setExpenseRecordList(new ArrayList<>());
        Patient patientSave = this.mongoDao.savePatient(patient);
        ZybrToPatientLog zybrToPatientLog = this.mongoDao.findZybrToPatientLog(patientSave.getId(), inHospitalInfo.getId(), patient.getCreatedTime());
        if(zybrToPatientLog==null){
            zybrToPatientLog=new ZybrToPatientLog();
            zybrToPatientLog.setCreatedTime(patient.getCreatedTime());
            zybrToPatientLog.setPatientId(patient.getId());
            zybrToPatientLog.setZybrId(inHospitalInfo.getId());
            zybrToPatientLog.setIcuAdmissionTime(patient.getIcuAdmissionTime());
            this.mongoDao.saveZybrToPatientLog(zybrToPatientLog);
        }
    }

    public void isSynOutPatient(InHospitalInfo icuPatientInfoOut) {
        if(icuPatientInfoOut==null)return;
        Patient dischargePatient = this.mongoDao.findAdmittedPatient(icuPatientInfoOut.getMrn(),List.of("admitted","wait_discharged"));
        if (dischargePatient==null)return;
        dischargePatient.setDischargedType(icuPatientInfoOut.getDischargedType());
        dischargePatient.setDischargedDepartment(icuPatientInfoOut.getDischargedDepartment());
        dischargePatient.setDischargedDepartmentCode(icuPatientInfoOut.getDischargedDepartmentCode());
        Date deathTime = icuPatientInfoOut.getDeathTime();
        if(deathTime!=null){
            dischargePatient.setDeathTime(deathTime);
            dischargePatient.setDischargedType("死亡");
        }
        dischargePatient.setStatus("discharged");
        dischargePatient.setIcuDischargeTime(icuPatientInfoOut.getDischargeTime());
        this.mongoDao.savePatient(dischargePatient);
        //绑定设备解绑
        DeviceBind deviceBind=this.mongoDao.findBind(dischargePatient.getId());
        if(deviceBind!=null){
            deviceBind.setUnBindTime(icuPatientInfoOut.getDischargeTime());
            deviceBind.setUnBindUserName("自动解绑");
            deviceBind.setUnBindUserId("6a323a1b9901235b28fcb5c1");
            this.mongoDao.saveDeviceBind(deviceBind);
           DeviceOnline deviceOnline = this.mongoDao.findDeviceOnline(deviceBind.getOnlineID());
           String curBed="";
           if(deviceOnline!=null){
               curBed = deviceOnline.getCurBed();
               deviceOnline.setCurBed("");
               this.mongoDao.saveDeviceOnline(deviceOnline);
           }
            //todo 取消出科怎么办 添加中间表记录 按道理这一步应该必须为空
            UnBindLog unBindLog = this.mongoDao.findUnBindLog(deviceBind.getId(), deviceBind.getPid(), deviceBind.getOnlineID());
            if(unBindLog==null){
                unBindLog = new UnBindLog();
                unBindLog.setDeviceBindId(deviceBind.getId());
                unBindLog.setPatientId(deviceBind.getPid());
                unBindLog.setUnBindTime(icuPatientInfoOut.getDischargeTime());
                unBindLog.setStatus(true);
                unBindLog.setCurBed(curBed);
                unBindLog.setOnlineId(deviceOnline.getId());
                this.mongoDao.save(unBindLog);
            }
        }
    }

    public void isSynCleanPatient(InHospitalInfo icuPatientInfoClean,String type) {
        if(icuPatientInfoClean==null)return;
        if("转科取消".equals(type)){
            Patient cleanPatient = this.mongoDao.findAdmittedPatient(icuPatientInfoClean.getMrn(),List.of("discharged","wait_discharged"));
            if (cleanPatient==null)return;
            cleanPatient.setStatus("admitted");
            cleanPatient.setDischargedType(null);
            cleanPatient.setDischargedDepartment(null);
            cleanPatient.setDischargedDepartmentCode(null);
            cleanPatient.setDeathTime(null);
            cleanPatient.setIcuDischargeTime(null);
            this.mongoDao.savePatient(cleanPatient);
            //绑定设备解绑恢复
            DeviceBind deviceBind=this.mongoDao.findBind(cleanPatient.getId());
            if(deviceBind!=null){
                UnBindLog unBindLog = this.mongoDao.findUnBindLog(deviceBind.getId(), deviceBind.getPid(), deviceBind.getOnlineID());
                if(unBindLog!=null){
                    deviceBind.setUnBindTime(null);
                    deviceBind.setUnBindUserName(null);
                    deviceBind.setUnBindUserId(null);
                    this.mongoDao.saveDeviceBind(deviceBind);
                    DeviceOnline deviceOnline = this.mongoDao.findDeviceOnline(deviceBind.getOnlineID());
                    if(deviceOnline!=null){
                        deviceOnline.setCurBed(unBindLog.getCurBed());
                        this.mongoDao.saveDeviceOnline(deviceOnline);
                    }
                    unBindLog.setStatus(false);
                    this.mongoDao.save(unBindLog);
                }
            }
        }else {
            //"admitted","wait_admitted" 入科取消 的直接赋予无效
            Patient cleanPatient = this.mongoDao.findAdmittedPatient(icuPatientInfoClean.getMrn(),List.of("admitted","wait_admitted"));
            if (cleanPatient==null)return;
            cleanPatient.setStatus("invalid");
            this.mongoDao.savePatient(cleanPatient);
        }
    }
}
