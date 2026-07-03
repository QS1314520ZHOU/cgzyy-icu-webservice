package com.digixmed.icu.viform;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("deviceBind")
public class DeviceBind {
    @Id
    private String id;
    private String bed;
    private String pid;
    private String deviceID;
    private String onlineID;
    private String type;
    private String model;
    private String deviceNmae;
    private String bindUserId;
    private String bindUserName;
    private String unBindUserId;
    private String unBindUserName;
    private Date   unBindTime;
    private Date   bindTime;
}
