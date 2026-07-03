package com.digixmed.icu.viform;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("VI_Zybr_To_Patient_Log")
public class ZybrToPatientLog {
    @Id
    private String id;
    private String zybrId;
    private String patientId;
    private Date createdTime;
    private Date icuAdmissionTime;
}
