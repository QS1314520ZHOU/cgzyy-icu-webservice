package com.digixmed.icu.viform;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("VI_ICU_EMERG")
@Data
public class VIICUEMERG {

    @Id
    private String id;
    private String dataID;
    private String hisPid;
    private String patName;
    private String reqDr;
    private String reqName;
    private String bedCode;
    private String deptCode;
    private String deptName;
    private Date repTime;
    private String msg;
    private String executiveDoctor;
    private String executiveDoctorName;
    private String executiveTime;
    private String itemName;
    private String result;
    private String testItemID;
    private String orderID;
    private String seriousFlag;
    private String seriousLow;
    private String seriousHigh;
    private String bigItemName;
}