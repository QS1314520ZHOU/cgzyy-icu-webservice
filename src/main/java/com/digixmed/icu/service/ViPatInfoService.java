package com.digixmed.icu.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.digixmed.icu.dao.MongoDao;
import com.digixmed.icu.util.DataUtil;
import com.digixmed.icu.viform.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


@Service
public class ViPatInfoService extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(ViPatInfoService.class);

    @Autowired
    MongoDao mongoDao;
    @Autowired
    DictionaryService dictionaryService;
    @Value("${digixmed.isCGZYY:}")
    Boolean isCGZYY;

    public void handlePatientInfo(Element bodyElement) throws Exception {
        String value;
        List<Element> elements = bodyElement.elements("Field");
        if (CollectionUtils.isEmpty(elements)) {
            log.error("数据异常");
            return;
        }
        String patientID = "";
        PatientBasicInfo patientRegistry = new PatientBasicInfo();
        for (Element element : elements) {
            String name = element.attribute("Name").getValue();
            value = element.attribute("Value").getValue();
            if(StrUtil.isBlank(value)||value.contains("NULL"))continue;
            switch (name) {
                case "CYLJGDM":
                    patientRegistry.setHospitalCode(value);
                    break;
                case "CHZBS":
                    patientID = value;
                    patientRegistry.setPid(value);
                    break;
                case "CHZXM":
                    patientRegistry.setName(value);
                    break;
                case "CXBMC":
                    patientRegistry.setGender(getGender(value));
                    break;
                case "DCSRQ":
                    patientRegistry.setDob(DataUtil.stringToDate(value, "yyyy-MM-dd"));
                    break;
                case "CRYJLID":
                    patientRegistry.setDocumentNo(value);
                    break;
                case "documentNo":
                    patientRegistry.setTelephone(value);
                    break;
            }
        }
        PatientBasicInfo patient = this.mongoDao.getPatBasicInfo(patientID);
        if (patient != null) {
            patientRegistry.setId(patient.getId());
        }
        this.mongoDao.savePatientBasicInfo(patientRegistry);
    }

    private String getGender(String value) {
        return StringUtils.isEmpty(value) ? value : value.contains("男") ? "男" : value.contains("女") ? "女" : value;
    }

    public void inPatientRegister(Element bodyElement, List<String> deptCodeList) throws Exception {
        String value;
        List<Element> elements = bodyElement.elements("Field");
        if (CollectionUtils.isEmpty(elements)) {
            log.error("数据异常");
            return;
        }
        String status="";
        PatientInfo inPatient = new PatientInfo();
        for (Element element : elements) {
            String name = element.attribute("Name").getValue();
            value = element.attribute("Value").getValue();
            if(StrUtil.isBlank(value)||value.contains("NULL"))continue;
            switch (name) {
                case "CZYH":
                    inPatient.setMrn(value);
                    break;
                case "CHZBS":
                    inPatient.setPid(value);
                    break;
                case "IZYCS":
                    inPatient.setZycs(value);
                    break;
                case "CYLBXLBMC":
                    inPatient.setYblx(value);
                    break;
                case "CHZXM":
                    inPatient.setName(value);
                    break;
                case "CXBMC":
                    inPatient.setGender(getGender(value));
                    break;
                case "DCSRQ":
                    inPatient.setDob(DataUtil.stringToDate(value, "yyyy-MM-dd"));
                    inPatient.setAge(DataUtil.getAgeStr(inPatient.getDob(), new Date()));
                    break;
                case "DRKRQSJ":
                    inPatient.setAdmitTime(DataUtil.stringToDate(value, "yyyy-MM-dd HH:mm:ss"));
                    break;
                case "DRYRQSJ":
                    inPatient.setInPatientTime(DataUtil.stringToDate(value, "yyyy-MM-dd HH:mm:ss"));
                    break;
                case "CZYBQMC":
                    inPatient.setDept(value);
                    break;
                case "CZYBQBM":
                    inPatient.setDeptCode(value);
                    break;
                case "CSFZJHM":
                    inPatient.setIdcardno(value);
                    break;
                case "documentNo":
                    inPatient.setTel(value);
                    break;
                case "CZYZJBZTMC":
                    inPatient.setIllnessLevel(value);
                    break;
                case "CJLRBM":
                    inPatient.setDoctorID(value);
                    break;
                case "CJLRQM":
                    inPatient.setDoctor(value);
                    break;
                case "BSJZT":
                    status = value;
                    break;
            }
        }
        inPatient.setZyzt("住院");
        try {
            PatientBasicInfo patientBasicInfo = this.mongoDao.getPatBasicInfo(inPatient.getPid());
            if (patientBasicInfo != null) {
                inPatient.setAddress(patientBasicInfo.getAddress());
            }
        } catch (Exception e) {
        }
        PatientInfo patient = this.mongoDao.getPatientInfoByPid(inPatient.getPid(),inPatient.getMrn());
        if (patient != null) {
            inPatient.setId(patient.getId());
        }
        this.mongoDao.save(inPatient);
        if (!StringUtils.isEmpty(inPatient.getDeptCode()) && deptCodeList.contains(inPatient.getDeptCode())) {
            //患者入院直接来ICU的 当入科时间没有时取入院时间
            if (inPatient.getAdmitTime()==null) {
                inPatient.setAdmitTime(inPatient.getInPatientTime());
            }
            transferIn(inPatient,status);
        }
    }

    public void cancelInPatientRegister(Element bodyElement, List<String> deptCodeList) {
        Element inpatientEncounterCancelRt = bodyElement.element("InpatientEncounterCancelRt");
        String visitNumber = getElementText(inpatientEncounterCancelRt, "PAADMVisitNumber");
        Date dateTime = getDateTime(inpatientEncounterCancelRt, "UpdateDate", "UpdateTime");
        PatientInfo patient = this.mongoDao.getPatientInfoByHisVisitId(visitNumber);
        if (patient == null) {
            return;
        }
        patient.setDeptCode(null);
        patient.setDept(null);
        patient.setAdmitTime(null);
        patient.setInPatientTime(null);
        patient.setZyzt("取消住院登记");
        this.mongoDao.save(patient);
//        transferOut(patient, dateTime,status);
    }

    public void inDept(Element bodyElement, List<String> deptCodeList) throws Exception {
        String value;
        List<Element> elements = bodyElement.elements("Field");
        if (CollectionUtils.isEmpty(elements)) {
            return;
        }
        String pid = null;
        String mrn = "";
        for (Element element : elements) {
            if ("CHZBS".equals(element.attribute("Name").getValue())) {
                pid = element.attribute("Value").getValue();
               continue;
            }
            if ("CZYH".equals(element.attribute("Name").getValue())) {
                mrn = element.attribute("Value").getValue();
                continue;
            }
        }
        if (pid == null) {
            return;
        }
        PatientInfo patient = this.mongoDao.getPatientInfoByPid(pid,mrn);
        if (patient == null) {
            return;
        }
        Date admitTime = null;
        String inDeptCode = null;
        String inDeptName = null;
        String bedCode = null;
        String zhuYuanDoctorId = null;
        String status= "";
        for (Element element2 : elements) {
            String name = element2.attribute("Name").getValue();
            value = element2.attribute("Value").getValue();
            if(StrUtil.isBlank(value)||value.contains("NULL"))continue;
            switch (name) {
                case "DRKRQSJ":
                    admitTime = DataUtil.stringToDate(value, "yyyy-MM-dd HH:mm:ss");
                    break;
                case "CZYBQBM":
                    inDeptCode = value;
                    break;
                case "CZYBQMC":
                    inDeptName = value;
                    break;
                case "CRYBCH":
                    bedCode = value;
                    break;
                case "CZYYSDM":
                    zhuYuanDoctorId = value;
                    break;
                case "BSJZT":
                    status = value;
                    break;
            }
        }
        patient.setAdmitTime(admitTime);
        patient.setDischargeTime(null);
        patient.setDeptCode(inDeptCode);
        patient.setDept(inDeptName);
        patient.setBed(bedCode);
        changeBed(patient);
        patient.setDoctorID(zhuYuanDoctorId);
        patient.setDoctor(getAccountName(zhuYuanDoctorId, this.mongoDao));
        changeDoctor(patient);
        if (deptCodeList.contains(inDeptCode)) {
            //确认入科到ICU的
            transferIn(patient,status);
        }
        this.mongoDao.save(patient);
    }

    public void transZhenDuan(Element bodyElement, List<String> deptCodeList) throws Exception {
        List<Element> elements = bodyElement.elements("Field");
        if (CollectionUtils.isEmpty(elements)) {
            return;
        }
        String pid = "";
        String zd = "";
        String zdzt = "";
        String mrn="";
        String zzd="";
        String bm="";
        Date tiem=new Date();
        for (Element element : elements) {
            String name = element.attribute("Name").getValue();
            if ("CHZBS".equals(name)) {
                pid = element.attribute("Value").getValue();
                continue;
            }
            if ("CJBZDMC".equals(name)) {
                zd = element.attribute("Value").getValue();
                continue;
            }
            if ("BSJZT".equals(name)) {
                zdzt = element.attribute("Value").getValue();
                continue;
            }
            if("CZYH".equals(name)){
                mrn = element.attribute("Value").getValue();
                continue;
            }
            if("BZZBZ".equals(name)){
                zzd = element.attribute("Value").getValue();
                continue;
            }
            if("CJBZDBM".equals(name)){
                bm = element.attribute("Value").getValue();
                continue;
            }
            if ("DJLRQSJ".equals(element.attribute("Name").getValue())) {
                tiem = DataUtil.stringToDate(element.attribute("Value").getValue(), "yyyy-MM-dd HH:mm:ss");
                continue;
            }
        }
        PatientInfo patient = this.mongoDao.getPatientInfoByPid(pid,mrn);
        if (patient == null) {
            return;
        }
        Patient patient1 = this.mongoDao.findIcuPatien(pid,mrn,List.of("admitted","wait_admitted","wait_discharged"));
        if (patient1 == null) {
            return;
        }
        List<String> clinicalDiagnosisCodeList = patient1.getClinicalDiagnosisCodeList();
        String diagnose = patient.getDiagnose();
        //取消诊断
        if("0".equals(zdzt)){
            if (!StringUtils.isEmpty(diagnose)&&diagnose.contains(zd)) {
                String fieldToRemove= zd;
                String result = Arrays.stream(diagnose.split(";"))
                        .map(String::trim)             // 去除可能存在的前后空格
                        .filter(s -> !s.isEmpty())     // 过滤掉空字符串（自动解决连续 ;; 或首尾 ; 产生的多余分号）
                        .filter(s -> !s.equals(fieldToRemove)) // 核心：精确匹配，剔除目标字段
                        .collect(Collectors.joining(";"));     // 重新用 ; 拼接
                patient.setDiagnose(result);
            }
            ArrayList<String> strings = new ArrayList<>();
            if(CollectionUtil.isNotEmpty(clinicalDiagnosisCodeList)){
                for (String s : clinicalDiagnosisCodeList) {
                    if(StrUtil.isEmpty(s))continue;
                    if(s.equals(bm))continue;
                    strings.add(s);
                }
            }
        }else {
            if (StringUtils.isEmpty(diagnose)) {
                patient.setDiagnose(zd);
            } else {
                if(!diagnose.contains(zd)){
                    patient.setDiagnose(diagnose + ";" + zd);
                }
                diagnose=patient.getDiagnose();
                if("true".equals(zzd)){
                    //如果是主诊断
                    String[] split = diagnose.split(";");
                    ArrayList<String> strings = new ArrayList<>();
                    strings.add(zd);
                    for (int i = 0; i < split.length; i++) {
                        String s = split[i];
                        if(zd.equals(s))continue;
                        strings.add(s);
                    }
                    patient.setDiagnose(strings.stream().collect(Collectors.joining(";")));
                }else {
                    //排序 todo 先不做科室需要在说
                }


            }
            if(CollectionUtil.isEmpty(clinicalDiagnosisCodeList)){
                clinicalDiagnosisCodeList=new ArrayList<>();
            }
            clinicalDiagnosisCodeList.add(bm);
        }
        this.mongoDao.save(patient);
        InHospitalInfo inHospitalInfo = this.mongoDao.getIcuPatientInfoByPid(pid,mrn);
        if (inHospitalInfo == null) {
            return;
        }
        inHospitalInfo.setDiagnose(patient.getDiagnose());
        this.mongoDao.save(inHospitalInfo);
        if(isCGZYY){
            patient1.setClinicalDiagnosisCodeList(clinicalDiagnosisCodeList);
            patient1.setClinicalDiagnosis(patient.getDiagnose());
            patient1.setClinicalDiagnosisTime(tiem);
            this.mongoDao.savePatient(patient1);
        }
    }

    public void transferDeptOrBed(Element bodyElement, List<String> deptCodeList) throws Exception {
        String value;
        List<Element> elements = bodyElement.elements("Field");
        if (CollectionUtils.isEmpty(elements)) {
            return;
        }
        String pid = null;
        String mrn = null;
        for (Element element : elements) {
            if ("CHZBS".equals(element.attribute("Name").getValue())) {
                pid = element.attribute("Value").getValue();
                continue;
            }
            if ("CZYH".equals(element.attribute("Name").getValue())) {
                mrn = element.attribute("Value").getValue();
                continue;
            }
        }
        if (pid == null||mrn==null) {
            return;
        }
        PatientInfo patient = this.mongoDao.getPatientInfoByPid(pid,mrn);
        if (patient == null) {
            return;
        }
        String outDeptCode = null;
        String outDeptName = null;
        Date dischargeTime = null;
        Date admitTime = null;
        String inDeptCode = null;
        String inDeptName = null;
        String bedCode = null;
        String zhuYuanDoctorId = null;
        String zhushu = null;
        String status = "";
        for (Element element2 : elements) {
            String name = element2.attribute("Name").getValue();
            value = element2.attribute("Value").getValue();
            if(StrUtil.isBlank(value)||value.contains("NULL"))continue;
            switch (name) {
                case "DZCRQSJ":
                    dischargeTime = DataUtil.stringToDate(value, "yyyy-MM-dd HH:mm:ss");
                    break;
                case "CZCBQDM":
                    outDeptCode = value;
                    break;
                case "CZCBQMC":
                    outDeptName = value;
                    break;
                case "DZRRQSJ":
                    admitTime = DataUtil.stringToDate(value, "yyyy-MM-dd HH:mm:ss");
                    break;
                case "CZRBQDM":
                    inDeptCode = value;
                    break;
                case "CZRBQMC":
                    inDeptName = value;
                    break;
                case "CZRBCH":
                    bedCode = value;
                    break;
                case "CZRYSDM":
                    zhuYuanDoctorId = value;
                    break;
                case "CZS":
                    zhushu = value;
                    break;
                case "BSJZT":
                    status = value;
                    break;
            }
        }
        patient.setZhushu(zhushu);
        patient.setAdmitTime(admitTime);
        patient.setDischargeTime(null);
        patient.setDeptCode(inDeptCode);
        patient.setDept(inDeptName);
        patient.setBed(bedCode);
        changeBed(patient);
        patient.setDoctorID(zhuYuanDoctorId);
        patient.setDoctor(getAccountName(zhuYuanDoctorId, this.mongoDao));
        changeDoctor(patient);
        String deptName = getDeptName(outDeptCode, this.mongoDao);
        if (deptCodeList.contains(outDeptCode)) {
            patient.setDischargedType("转出");
            patient.setDischargedDepartment(deptName);
            patient.setDischargedDepartmentCode(outDeptCode);
            //转科医嘱 从ICU转出的
            transferOut(patient, dischargeTime,status);
        } else if (deptCodeList.contains(inDeptCode)) {
            if (StrUtil.isNotBlank(outDeptCode)){
                if(StrUtil.isNotBlank(deptName)){
                    patient.setAdmissionSource(deptName);
                }else {
                    patient.setAdmissionSource(outDeptName);
                }
            }else {
                patient.setAdmissionSource(outDeptName);
            }
            patient.setAdmissionType("转入");
            //转科医嘱到ICU的 todo 这种是不是不需要直接生成在线病人哦  先注释掉
//            transferIn(patient,status);
        }
        this.mongoDao.save(patient);
    }

    public void outPatient(Element bodyElement, List<String> deptCodeList) throws Exception {
        List<Element> elements = bodyElement.elements("Field");
        if (CollectionUtils.isEmpty(elements)) {
            return;
        }
        String pid = null;
        Date outPatientTime = null;
        String status="";
        String type="";
        String deptCode="";
        String mrn="";
        for (Element element : elements) {
            String name = element.attribute("Name").getValue();
            if ("CHZBS".equals(name)) {
                pid = element.attribute("Value").getValue();
                continue;
            }
            if ("DCYRQSJ".equals(name)) {
                outPatientTime = DataUtil.stringToDate(element.attribute("Value").getValue(), "yyyy-MM-dd HH:mm:ss");
                continue;
            }
            if ("BSJZT".equals(name)) {
                status = element.attribute("Value").getValue();
                continue;
            }
            if ("CCYZTMC".equals(name)) {
                type = element.attribute("Value").getValue();
                continue;
            }
            if ("CCYBQBM".equals(name)) {
                deptCode = element.attribute("Value").getValue();
                continue;
            }
            if ("CZYH".equals(name)) {
                mrn = element.attribute("Value").getValue();
                continue;
            }
        }
        PatientInfo patient = this.mongoDao.getPatientInfoByPid(pid,mrn);
        if (patient == null) {
            return;
        }
        //todo 其他科室出院的会推送吗？
        //下达科室不是icu的不管
        if(!deptCodeList.contains(deptCode))return;
        if(StrUtil.isNotBlank(type)&&type.contains("死亡")){
            patient.setDeathTime(outPatientTime);
            patient.setDischargedType("死亡");
        }
        patient.setDischargedType("出院");
        //0是取消，取消出院就在科
        patient.setZyzt("1".equals(status)?"出院":"在科");
        patient.setDischargeTime(outPatientTime);
        this.mongoDao.save(patient);
        //出院推送  直接在ICU出院的
        transferOut(patient, outPatientTime,status);
    }

    public void cancelOutPatient(Element bodyElement, List<String> deptCodeList) {
        Element inpatientDischargeCancelRt = bodyElement.element("InpatientDischargeCancelRt");
        String visitNumber = getElementText(inpatientDischargeCancelRt, "PAADMVisitNumber");
        PatientInfo patient = this.mongoDao.getPatientInfoByHisVisitId(visitNumber);
        if (patient == null) {
            return;
        }
        patient.setZyzt("住院");
        patient.setDischargeTime(null);
        this.mongoDao.save(patient);
    }

    public void transferIn(PatientInfo patient,String status) {
        String pid = patient.getPid();
        if (StringUtils.isEmpty(pid)) {
            return;
        }
        InHospitalInfo inHospitalInfo = this.mongoDao.getIcuPatientInfoByPid(pid,patient.getMrn());
        if (inHospitalInfo == null) {
            inHospitalInfo = new InHospitalInfo();
            inHospitalInfo.transfer(patient);
        }
        if (inHospitalInfo.getAdmitTime() == null) {
            inHospitalInfo.setAdmitTime(patient.getAdmitTime());
        }
        if("1".equals(status)){
            inHospitalInfo.setZyzt("在科");
            inHospitalInfo.setDischargeTime(null);
            inHospitalInfo.setDept(patient.getDept());
            inHospitalInfo.setDeptCode(patient.getDeptCode());
            inHospitalInfo.setBed(patient.getBed());
            inHospitalInfo.setDiagnose(patient.getDiagnose());
            inHospitalInfo.setAdmissionSource(patient.getAdmissionSource());
            inHospitalInfo.setAdmissionType(StrUtil.isNotBlank(patient.getAdmissionType())?patient.getAdmissionType():"入院");
            InHospitalInfo inHospitalInfo1 = this.mongoDao.saveInHospitalInfo(inHospitalInfo);
            //1.确认入科到ICU的   2.患者入院直接来ICU的
            if(this.isCGZYY){
                log.warn("入科同步Patient开始");
                this.dictionaryService.isSynInPatient(inHospitalInfo1);
            }
        }else {
            inHospitalInfo.setZyzt("取消");
            InHospitalInfo icuPatientInfoClean = this.mongoDao.saveInHospitalInfo(inHospitalInfo);
            if(this.isCGZYY){
                log.warn("取消同步Patient开始");
                this.dictionaryService.isSynCleanPatient(icuPatientInfoClean,"入科取消");
            }
        }

    }

    public void transferOut(PatientInfo patient, Date dischargeTime,String status) {
        InHospitalInfo inHospitalInfo;
        String pid = patient.getPid();
        if (StringUtils.isEmpty(pid) || (inHospitalInfo = this.mongoDao.getIcuPatientInfoByPid(pid,patient.getMrn())) == null) {
            return;
        }
        if ("1".equals(status)) {
            inHospitalInfo.setZyzt("出科");
            Date deathTime = patient.getDeathTime();
            if(deathTime!=null){
                inHospitalInfo.setDeathTime(deathTime);
            }
            inHospitalInfo.setDischargedType(patient.getDischargedType());
            inHospitalInfo.setDischargedDepartment(patient.getDischargedDepartment());
            inHospitalInfo.setDischargedDepartmentCode(patient.getDischargedDepartmentCode());
            inHospitalInfo.setDischargeTime(dischargeTime);
            //1.通过下达的医嘱转出   2.转科医嘱 从ICU转出的   3.出院推送  直接在ICU出院的
            InHospitalInfo icuPatientInfoOut = this.mongoDao.saveInHospitalInfo(inHospitalInfo);
            if(this.isCGZYY){
                log.warn("出科同步Patient开始");
                this.dictionaryService.isSynOutPatient(icuPatientInfoOut);
            }
        }else {
            //取消
            inHospitalInfo.setZyzt("在科");
            inHospitalInfo.setDeathTime(null);
            inHospitalInfo.setDischargedType(null);
            inHospitalInfo.setDischargedDepartment(null);
            inHospitalInfo.setDischargedDepartmentCode(null);
            inHospitalInfo.setDischargeTime(null);
            InHospitalInfo icuPatientInfoClean = this.mongoDao.saveInHospitalInfo(inHospitalInfo);
            if(this.isCGZYY){
                log.warn("取消出科同步Patient开始");
                this.dictionaryService.isSynCleanPatient(icuPatientInfoClean,"转科取消");
            }
        }
    }

    public void changeBed(PatientInfo patientInfo) {
        InHospitalInfo inHospitalInfo;
        String pid = patientInfo.getPid();
        if (StringUtils.isEmpty(pid) || (inHospitalInfo = this.mongoDao.getIcuPatientInfoByPid(pid,patientInfo.getMrn())) == null) {
            return;
        }
        inHospitalInfo.setBed(patientInfo.getBed());
        this.mongoDao.save(inHospitalInfo);
    }

    public void changeDoctor(PatientInfo patientInfo) {
        InHospitalInfo inHospitalInfo;
        String pid = patientInfo.getPid();
        if (StringUtils.isEmpty(pid) || (inHospitalInfo = this.mongoDao.getIcuPatientInfoByPid(pid,patientInfo.getMrn())) == null) {
            return;
        }
        inHospitalInfo.setDoctorID(patientInfo.getDoctorID());
        inHospitalInfo.setDoctor(patientInfo.getDoctor());
        this.mongoDao.save(inHospitalInfo);
    }

    public void transferBed(Element bodyElement, List<String> deptCodeList) throws Exception {
        List<Element> elements = bodyElement.elements("Field");
        if (CollectionUtils.isEmpty(elements)) {
            return;
        }
        String pid = null;
        String bedCode = null;
        String bedStaus = null;
        String mrn = "";
        Date tiem=new Date();
        for (Element element : elements) {
            String name = element.attribute("Name").getValue();
            if ("CHZBS".equals(name)) {
                pid = element.attribute("Value").getValue();
                continue;
            }
            if ("CBCH".equals(name)) {
                bedCode = element.attribute("Value").getValue();
                continue;
            }
            if ("ICWZTBM".equals(name)) {
                bedStaus = element.attribute("Value").getValue();
                continue;
            }
            if ("CZYH".equals(element.attribute("Name").getValue())) {
                mrn = element.attribute("Value").getValue();
                continue;
            }
            if ("DJLRQSJ".equals(element.attribute("Name").getValue())) {
                tiem = DataUtil.stringToDate(element.attribute("Value").getValue(), "yyyy-MM-dd HH:mm:ss");
                continue;
            }

        }
        PatientInfo patient = this.mongoDao.getPatientInfoByPid(pid,mrn);
        if (patient == null) {
            throw new Exception("未找到"+pid+"该病人的住院信息");
        }
        if("5".equals(bedStaus)){
            InHospitalInfo  inHospitalInfo = this.mongoDao.getIcuPatientInfoByPid(pid,mrn);
            if(inHospitalInfo==null){
                throw new Exception("未找到"+pid+"该病人的ZYBR信息");
            }
            inHospitalInfo.setBed("");
            this.mongoDao.save(inHospitalInfo);
        }else {
            patient.setBed(bedCode);
            changeBed(patient);
            this.mongoDao.save(patient);
        }
        if(isCGZYY){
            //todo 看不需要内网平台同步，直接接口同步床号
            Patient admittedPatient = this.mongoDao.findAdmittedPatient(mrn,List.of("admitted","wait_admitted"));
            if(admittedPatient!=null){
                if ("5".equals(bedStaus)) {
                    admittedPatient.setHisBed("");
                }else {
                    PatBedHistorie patBedHistorie = new PatBedHistorie();
                    patBedHistorie.setHisBed(admittedPatient.getHisBed());
                    patBedHistorie.setTime(admittedPatient.getBedTime());
                    admittedPatient.setHisBed(bedCode);
                    admittedPatient.setBedTime(tiem);
                    List<PatBedHistorie> patBedHistories = admittedPatient.getPatBedHistories();
                    if(CollectionUtil.isEmpty(patBedHistories)){
                        patBedHistories=new ArrayList<>();
                    }
                    patBedHistories.add(patBedHistorie);
                    admittedPatient.setPatBedHistories(patBedHistories);
                }
                this.mongoDao.savePatient(admittedPatient);
            }
        }
    }

    public void handleSyOperations(Element bodyElement, List<String> deptCodeList)throws Exception {
        List<Element> elements = bodyElement.elements("Field");
        if (CollectionUtils.isEmpty(elements)) {
            return;
        }
        String pid = null;
        String operName = "";
        String mrn = "";
        Date stiem=new Date();
        Date etiem=new Date();
        for (Element element : elements) {
            String name = element.attribute("Name").getValue();
            if ("CHZBS".equals(name)) {
                pid = element.attribute("Value").getValue();
                continue;
            }
            if ("CSS_CZMC".equals(name)) {
                operName = element.attribute("Value").getValue();
                continue;
            }
            if ("CJZH".equals(element.attribute("Name").getValue())) {
                mrn = element.attribute("Value").getValue();
                continue;
            }
            if ("DSSKSSJ".equals(element.attribute("Name").getValue())) {
                stiem = DataUtil.stringToDate(element.attribute("Value").getValue(), "yyyy-MM-dd HH:mm:ss");
                continue;
            }
            if ("DSSJSSJ".equals(element.attribute("Name").getValue())) {
                etiem = DataUtil.stringToDate(element.attribute("Value").getValue(), "yyyy-MM-dd HH:mm:ss");
                continue;
            }

        }
        PatientInfo patient = this.mongoDao.getPatientInfoByPid(pid,mrn);
        if (patient == null) {
            throw new Exception("未找到"+pid+"该病人的住院信息");
        }
        Patient icuPatien = this.mongoDao.findIcuPatien(pid, mrn, List.of("admitted", "wait_admitted", "wait_discharged"));
        if (icuPatien == null) {
            throw new Exception("未找到"+pid+"该病人入ICU的信息");
        }
        PatientOperations patientOperations = new PatientOperations();
        patientOperations.setEndTime(etiem);
        patientOperations.setStartTime(stiem);
        patientOperations.setName(operName);
        List<PatientOperations> patientOperations1 = icuPatien.getPatientOperations();
        if(CollectionUtil.isEmpty(patientOperations1)){
            patientOperations1=new ArrayList<>();
        }
        patientOperations1.add(patientOperations);
        icuPatien.setPatientOperations(patientOperations1);
        this.mongoDao.savePatient(icuPatien);
    }
}