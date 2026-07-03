package com.digixmed.icu.viform;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("VI_UnBind_Log")
public class UnBindLog {
    @Id
    private String id;
    private String deviceBindId;
    private String patientId;
    private Date   unBindTime;
    private Boolean status;
    private String onlineId;
    private String curBed;
}
