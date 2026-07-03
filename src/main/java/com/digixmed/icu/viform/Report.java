package com.digixmed.icu.viform;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
@Data
@Document("VI_ICU_REPORT")
public class Report {
    @Id
    private String id; // 对应 _id (ObjectId)

    private String bed;

    private String reportID;

    private String examCode;

    private String orderID;

    private String examDoctor;

    private String examName;

    private String orderDoctor;

    private String mrn;

    private String patName;

    private String pid;

    private String patDept;

    private String title;

    private String bodyParts;

    private String room;

    private String conclusion;

    private String reportDesc;

    private String examDoctorID;

    private String orderDept;

    private String orderDoctorID;

    private String diagnose;

    private Date examTime;

    private Date reportTime;
}
