package com.digixmed.icu.service;

import cn.hutool.core.date.DateUtil;
import com.digixmed.icu.dao.MongoDao;
import com.digixmed.icu.pojo.CustomElement;
import com.digixmed.icu.viform.InHospitalInfo;
import java.util.Date;
import org.bson.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    MongoDao mongoDao;

    public String getIcuTime(Element bodyElement, String sourceSystem, String messageId, String code, String content) {
        String visitNumber = getElementText(bodyElement, "AdmNo");
        String admitTimeStr = "";
        String dischargeTimeStr = "";
        try {
            InHospitalInfo patientInfo = this.mongoDao.getIcuPatientInfoByHisVisitId2(visitNumber);
            String mrn = patientInfo.getMrn();
            String pid = patientInfo.getPid();
            Document document = this.mongoDao.getPatientsByMrnAndPid(mrn, pid);
            Document statusDocument = (Document) document.get("status", Document.class);
            if (statusDocument != null) {
                Date admitTime = statusDocument.getDate("admitTime");
                Date dischargeTime = statusDocument.getDate("dischargeTime");
                if (admitTime != null) {
                    admitTimeStr = DateUtil.format(admitTime, "yyyy-MM-dd HH:mm:ss");
                    dischargeTimeStr = DateUtil.format(dischargeTime, "yyyy-MM-dd HH:mm:ss");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getReturnResult(sourceSystem, messageId, code, content, admitTimeStr, dischargeTimeStr);
    }

    private String getReturnResult(String sourceSystem, String messageId, String code, String content, String admitTimeStr, String dischargeTimeStr) {
        CustomElement responseElement = new CustomElement("Response");
        responseElement.addHeaderElement(sourceSystem, messageId);
        responseElement.addBodyElement("");
        Element bodyElement = responseElement.getBodyElement();
        responseElement.addElement(bodyElement, "ResultCode", code);
        responseElement.addElement(bodyElement, "ResultContent", content);
        responseElement.addElement(bodyElement, "ICUSDate", admitTimeStr);
        responseElement.addElement(bodyElement, "ICUEDate", dischargeTimeStr);
        return responseElement.getElementAsXmlStr();
    }
}