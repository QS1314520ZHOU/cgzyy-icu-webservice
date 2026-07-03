package com.digixmed.icu.viform;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import org.springframework.data.annotation.Id;
@Data
@Document("deviceOnline")
public class DeviceOnline {
    @Id
    private String id;

    private String deviceID;

    private String deviceName;

    private String deviceType;

    private String lastBed;

    private String curBed;

    private Date unMountBedTime;

    private Date dataStopTime;

    private Date dataStartTime;

    private boolean isDataRev;

    private Date connTime;

    private boolean isConnected;

    private String deptCode;
}
