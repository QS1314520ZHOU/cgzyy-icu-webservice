package com.digixmed.icu.dao;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.digixmed.icu.constant.MyConstants;
import com.digixmed.icu.pojo.Account;
import com.digixmed.icu.pojo.entity.FileUploadRecord;
import com.digixmed.icu.pojo.entity.UploadPDFRecord;
import com.digixmed.icu.viform.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MongoDao {
    private static final Logger log = LoggerFactory.getLogger(MongoDao.class);


    @Autowired
    @Qualifier("smartcareMongoTemplate")
    private MongoTemplate smongoTemplate;

    @Autowired
    @Qualifier("datacenterMongoTemplate")
    private MongoTemplate mongoTemplate;


    public Account findAccountByWorkId(String workId) {
        return (Account) this.mongoTemplate.findOne(Query.query(Criteria.where("username").is(workId)), Account.class);
    }

    public DeptInfo findDeptInfo(String deptCode) {
        return (DeptInfo) this.mongoTemplate.findOne(Query.query(Criteria.where("deptCode").is(deptCode)), DeptInfo.class);
    }

    public DeptInfo findDeptInfoByBusinessrowid(String rowId) {
        return (DeptInfo) this.mongoTemplate.findOne(Query.query(Criteria.where("businessrowid").is(rowId)), DeptInfo.class);
    }

    public void updateStaffInfo(List<StaffInfo> staffInfoList) {
        if (staffInfoList == null || staffInfoList.size() == 0) {
            return;
        }
        List<StaffInfo> insertList = new ArrayList<>();
        for (StaffInfo staffInfo : staffInfoList) {
            StaffInfo icuStaffInfo = (StaffInfo) this.mongoTemplate.findOne(Query.query(Criteria.where("code").is(staffInfo.getCode())), StaffInfo.class);
            if (icuStaffInfo == null) {
                insertList.add(staffInfo);
            } else {
                staffInfo.setId(icuStaffInfo.getId());
                Update update = createUpdate(icuStaffInfo, staffInfo);
                if (update != null) {
                    this.mongoTemplate.upsert(Query.query(Criteria.where("_id").is(icuStaffInfo.getId())), update, StaffInfo.class);
                }
            }
        }
        this.mongoTemplate.insertAll(insertList);
    }

    public void updateDrugFreq(List<DrugFreq> drugFreqList) {
        if (drugFreqList == null || drugFreqList.size() == 0) {
            return;
        }
        List<DrugFreq> insertList = new ArrayList<>();
        for (DrugFreq drugFreq : drugFreqList) {
            DrugFreq temp = (DrugFreq) this.mongoTemplate.findOne(Query.query(Criteria.where("freqCode").is(drugFreq.getFreqCode())), DrugFreq.class);
            if (temp == null) {
                insertList.add(drugFreq);
            } else {
                drugFreq.setId(temp.getId());
                Update update = createUpdate(temp, drugFreq);
                if (update != null) {
                    this.mongoTemplate.upsert(Query.query(Criteria.where("_id").is(drugFreq.getId())), update, DrugFreq.class);
                }
            }
        }
        this.mongoTemplate.insertAll(insertList);
    }

    public void updateDrugMethod(List<DrugMethod> drugMethodList) {
        if (drugMethodList == null || drugMethodList.size() == 0) {
            return;
        }
        List<DrugMethod> insertList = new ArrayList<>();
        for (DrugMethod drugMethod : drugMethodList) {
            DrugMethod temp = (DrugMethod) this.mongoTemplate.findOne(Query.query(Criteria.where("code").is(drugMethod.getCode())), DrugMethod.class);
            if (temp == null) {
                insertList.add(drugMethod);
            } else {
                drugMethod.setId(temp.getId());
                Update update = createUpdate(temp, drugMethod);
                if (update != null) {
                    this.mongoTemplate.upsert(Query.query(Criteria.where("_id").is(temp.getId())), update, DrugMethod.class);
                }
            }
        }
        this.mongoTemplate.insertAll(insertList);
    }

    public List<Document> findAdmittedPatList() {
        return this.mongoTemplate.find(Query.query(Criteria.where("status.flag").is("admitted")), Document.class, "patients");
    }

    public void updateDept(List<DeptInfo> deptInfoList) {
        if (deptInfoList == null || deptInfoList.size() == 0) {
            return;
        }
        List<DeptInfo> insertList = new ArrayList<>();
        for (DeptInfo deptInfo : deptInfoList) {
            DeptInfo tempOne = (DeptInfo) this.mongoTemplate.findOne(Query.query(Criteria.where("deptCode").is(deptInfo.getDeptCode())), DeptInfo.class);
            if (tempOne != null) {
                deptInfo.setId(tempOne.getId());
                Update update = createUpdate(tempOne, deptInfo);
                if (update != null) {
                    this.mongoTemplate.upsert(Query.query(Criteria.where("_id").is(tempOne.getId())), update, DeptInfo.class);
                }
            } else {
                insertList.add(deptInfo);
            }
        }
        this.mongoTemplate.insertAll(insertList);
    }

    private Update createUpdate(Object entity, Object dto) {
        Update update = new Update();
        Map<String, Object> map = BeanUtil.beanToMap(entity);
        for (Map.Entry<String, Object> entry : BeanUtil.beanToMap(dto).entrySet()) {
            if (map.get(entry.getKey()) == null || entry.getValue() == null || !map.get(entry.getKey()).equals(entry.getValue())) {
                if (map.get(entry.getKey()) != null || entry.getValue() != null) {
                    update.set(entry.getKey(), entry.getValue());
                }
            }
        }
        if (update.getUpdateObject().size() == 0) {
            return null;
        }
        return update;
    }

    public List<Document> getBedsideByTimeAndPid(String pid, Date startTime, Date endTime, List<String> codeList) {
        Criteria criteria = Criteria.where("pDBid").is(pid).and("time").gte(startTime).lte(endTime).and(MyConstants.FLAG_VALID).is(MyConstants.FLAG_VALID).and("code").in(codeList);
        return this.mongoTemplate.find(Query.query(criteria), Document.class, "bedside");
    }

    public Document getBedsideConfig(String pid) {
        Criteria criteria = Criteria.where("pid").is(pid);
        return (Document) this.mongoTemplate.findOne(Query.query(criteria), Document.class, "bedsideConfig");
    }

    public Document getPatientByHisPid(String hisPid) {
        return (Document) this.mongoTemplate.findOne(Query.query(Criteria.where("hisPid").is(hisPid)), Document.class, "patients");
    }

    public PatientBasicInfo getPatBasicInfo(String hisPid) {
        if (StringUtils.isEmpty(hisPid)) {
            return null;
        }
        return (PatientBasicInfo) this.mongoTemplate.findOne(Query.query(Criteria.where("pid").is(hisPid)), PatientBasicInfo.class);
    }

    public void savePatientBasicInfo(PatientBasicInfo patientBasicInfo) {
        this.mongoTemplate.save(patientBasicInfo);
    }

    public PatientInfo getPatientInfoByHisVisitId(String hisVisitId) {
        if (StringUtils.isEmpty(hisVisitId)) {
            return null;
        }
        return (PatientInfo) this.mongoTemplate.findOne(Query.query(Criteria.where("hisVisitId").is(hisVisitId)), PatientInfo.class);
    }

    public PatientInfo getPatientInfoByPid(String pid,String mrn) {
        if (StringUtils.isEmpty(pid)) {
            return null;
        }
        return (PatientInfo) this.mongoTemplate.findOne(Query.query(Criteria.where("pid").is(pid).and("mrn").is(mrn)), PatientInfo.class);
    }

    public InHospitalInfo getIcuPatientInfoByPid(String pid,String mrn) {
        if (StringUtils.isEmpty(pid)) {
            return null;
        }
        return (InHospitalInfo) this.mongoTemplate.findOne(Query.query(Criteria.where("pid").is(pid).and("mrn").is(mrn)), InHospitalInfo.class);
    }
    public InHospitalInfo findIcuPatientInfoByPid(String pid) {
        if (StringUtils.isEmpty(pid)) {
            return null;
        }
        return (InHospitalInfo) this.mongoTemplate.findOne(Query.query(Criteria.where("pid").is(pid)), InHospitalInfo.class);
    }
    public InHospitalInfo getIcuPatientInfoByHisVisitId2(String hisVisitId) {
        if (StringUtils.isEmpty(hisVisitId)) {
            return null;
        }
        return (InHospitalInfo) this.mongoTemplate.findOne(Query.query(Criteria.where("hisVisitId").is(hisVisitId)), InHospitalInfo.class);
    }

    public InHospitalInfo getIcuPatientInfoByMrn(String mrn) {
        if (StringUtils.isEmpty(mrn)) {
            return null;
        }
        return (InHospitalInfo) this.mongoTemplate.findOne(Query.query(Criteria.where("mrn").is(mrn).and("zyzt").is("在科")), InHospitalInfo.class);
    }

    public List<InHospitalInfo> getIcuPatientInfoByZyzt() {
        return this.mongoTemplate.find(Query.query(Criteria.where("zyzt").is("在科")), InHospitalInfo.class);
    }

    public <T> void save(T t) {
        this.mongoTemplate.save(t);
    }

    public void updateOrSaveOrder(List<Order> orderList) {
        if (orderList == null || orderList.size() == 0) {
            return;
        }
        List<Order> insertList = new ArrayList<>();
        for (Order order : orderList) {
            Order tempOne = (Order) this.mongoTemplate.findOne(Query.query(Criteria.where("orderID").is(order.getOrderID())), Order.class);
            if (tempOne != null) {
                order.setId(tempOne.getId());
                Update update = createUpdate(tempOne, order);
                if (update != null) {
                    this.mongoTemplate.upsert(Query.query(Criteria.where("_id").is(tempOne.getId())), update, Order.class);
                }
            } else {
                insertList.add(order);
            }
        }
        this.mongoTemplate.insertAll(insertList);
    }

    public void updateOrSaveOrderExec(List<OrderExecute> orderExecuteList) {
        if (orderExecuteList == null || orderExecuteList.size() == 0) {
            return;
        }
        List<OrderExecute> insertList = new ArrayList<>();
        for (OrderExecute orderExecute : orderExecuteList) {
            OrderExecute tempOne = (OrderExecute) this.mongoTemplate.findOne(Query.query(Criteria.where("exeId").is(orderExecute.getExeId())), OrderExecute.class);
            if (tempOne != null) {
                orderExecute.setId(tempOne.getId());
                Update update = createUpdate(tempOne, orderExecute);
                if (update != null) {
                    this.mongoTemplate.upsert(Query.query(Criteria.where("_id").is(tempOne.getId())), update, OrderExecute.class);
                }
            } else {
                insertList.add(orderExecute);
            }
        }
        this.mongoTemplate.insertAll(insertList);
    }

    public List<DrugFreq> getOrderFreq() {
        return this.mongoTemplate.findAll(DrugFreq.class);
    }

    public Order getOrderByOrderId(String orderId) {
        return (Order) this.mongoTemplate.findOne(Query.query(Criteria.where("orderID").is(orderId)), Order.class);
    }

    public Exam getExamByOrderID(String orderId) {
        return (Exam) this.mongoTemplate.findOne(Query.query(Criteria.where("orderID").is(orderId)), Exam.class);
    }

    public Account findAccountById(String id) {
        return (Account) this.mongoTemplate.findOne(Query.query(Criteria.where("id").is(id).and("status").ne(MyConstants.FLAG_IN_VALID)), Account.class);
    }

    public <T> List<T> getAll(Class<T> tClass) {
        return this.mongoTemplate.findAll(tClass);
    }

    public Document findDocument(Query query, Class<Document> documentClass, String formName) {
        return (Document) this.mongoTemplate.findOne(query, documentClass, formName);
    }

    public void delRepeatData(String pid, String code, String stDate, String etDate) {
        AggregationOperation group = Aggregation.group(new String[]{"pDBid", "time", "code", "strVal", "eventCode"}).count().as("count").addToSet("_id").as("ids");
        Criteria criteria = Criteria.where("count").gt(1);
        if (!StringUtils.isEmpty(code)) {
            criteria.and("code").regex("^" + code + ".*$");
        }
        if (!StringUtils.isEmpty(pid)) {
            criteria.and("pDBid").is(pid);
        }
        if (!StringUtils.isEmpty(stDate)) {
            criteria.and("time").gte(DateUtil.parseDateTime(stDate));
        }
        if (!StringUtils.isEmpty(etDate)) {
            criteria.and("time").gte(DateUtil.parseDateTime(etDate));
        }
        AggregationOperation match = Aggregation.match(criteria);
        Aggregation aggregation = Aggregation.newAggregation(new AggregationOperation[]{group, match});
        AggregationResults<Document> results = this.mongoTemplate.aggregate(aggregation, "bedside1", Document.class);
        List<Document> mappedResults = results.getMappedResults();
        for (Document mappedResult : mappedResults) {
            List ids = (List) mappedResult.get("ids", List.class);
            ids.remove(0);
            Query delQuery = Query.query(Criteria.where("_id").in(ids));
            this.mongoTemplate.remove(delQuery, "bedside1");
        }
    }

    public void createTestBedside1() {
        List<Document> documentList = this.mongoTemplate.findAll(Document.class, "bedside1");
        if (documentList.size() > 10000000) {
            return;
        }
        documentList.forEach(document -> {
            document.remove("_id");
            this.mongoTemplate.save(document, "bedside1");
        });
    }

    public Document getPatientsByMrnAndPid(String mrn, String pid) {
        return (Document) this.mongoTemplate.findOne(Query.query(Criteria.where("hisPid").is(pid).and("visitID").is(mrn)), Document.class, "patients");
    }

    public List<UploadPDFRecord> findUploadRecordByUpdateTime(Date updateTime) {
        Criteria criteria = Criteria.where("updateTime").gte(updateTime);
        return this.mongoTemplate.find(Query.query(criteria), UploadPDFRecord.class);
    }

    public void dealUploadResult(String id, boolean result) {
        Update update = Update.update("isCDA", Boolean.valueOf(result));
        Criteria criteria = Criteria.where("_id").is(id);
        this.mongoTemplate.updateFirst(Query.query(criteria), update, FileUploadRecord.class);
    }

    public void dealUploadResult(String id, boolean result, String pdfUrl) {
        Update update = Update.update("isCDA", Boolean.valueOf(result));
        update.set("pdfUrl", pdfUrl);
        Criteria criteria = Criteria.where("_id").is(id);
        this.mongoTemplate.updateFirst(Query.query(criteria), update, FileUploadRecord.class);
    }

    public Patient findAdmittedPatient(String mrn,List<String> status) {
        Criteria criteria = Criteria.where("status").in(status).and("mrn").is(mrn);
        return this.smongoTemplate.findOne(Query.query(criteria), Patient.class);
    }

    public Patient findIcuPatien(String hisPid) {
        return this.mongoTemplate.findOne(Query.query(Criteria.where("status").is("admitted").and("hisPid").is(hisPid)), Patient.class);
    }

    public Patient savePatient(Patient patient) {
       return this.smongoTemplate.save(patient);
    }

    public ZybrToPatientLog findZybrToPatientLog(String patientId, String zybrId, Date createdTime) {
        return this.mongoTemplate.findOne(Query.query(Criteria.where("patientId").is(patientId).and("zybrId").is(zybrId).and("createdTime").is(createdTime)), ZybrToPatientLog.class);
    }

    public void saveZybrToPatientLog(ZybrToPatientLog zybrToPatientLog) {
         this.mongoTemplate.save(zybrToPatientLog);
    }

    public DeviceBind findBind(String id) {
        Criteria criteria = Criteria.where("pid").is(id).and("unBindTime").is(null);
        return this.smongoTemplate.findOne(Query.query(criteria), DeviceBind.class);
    }
    public InHospitalInfo saveInHospitalInfo(InHospitalInfo inHospitalInfo) {
        return this.mongoTemplate.save(inHospitalInfo);
    }

    public DeviceBind saveDeviceBind(DeviceBind deviceBind) {
        return this.smongoTemplate.save(deviceBind);
    }

    public UnBindLog findUnBindLog(String deviceBindId, String patientId, String onlineId) {
        Criteria criteria = Criteria.where("deviceBindId").is(deviceBindId).and("patientId").is(patientId).and("onlineId").is(onlineId).and("status").is(true);
        return this.mongoTemplate.findOne(Query.query(criteria), UnBindLog.class);
    }

    public DeviceOnline findDeviceOnline(String onlineID) {
        Criteria criteria = Criteria.where("_id").is(onlineID);
        return this.smongoTemplate.findOne(Query.query(criteria), DeviceOnline.class);
    }

    public DeviceOnline saveDeviceOnline(DeviceOnline deviceOnline) {
        return this.smongoTemplate.save(deviceOnline);
    }

    public Report findReport(String pid, String orderId) {
        Criteria criteria = Criteria.where("pid").is(pid).and("orderID").is(orderId);
        return this.mongoTemplate.findOne(Query.query(criteria), Report.class);
    }

    public void deleteReport(String id) {
        Criteria criteria = Criteria.where("_id").is(id);
         this.mongoTemplate.remove(Query.query(criteria), Report.class);
    }

    public List<StaffInfo> findStaffAll() {
       return this.mongoTemplate.findAll(StaffInfo.class);
    }

    public List<DeptInfo> findDeptAll() {
        return this.mongoTemplate.findAll(DeptInfo.class);
    }

    public List<DrugFreq> findFreqAll() {
        return this.mongoTemplate.findAll(DrugFreq.class);
    }

    public List<DrugMethod> findYfAll() {
        return this.mongoTemplate.findAll(DrugMethod.class);
    }

    public List<Icd10> findICD10All() {
        return this.mongoTemplate.findAll(Icd10.class);
    }
}