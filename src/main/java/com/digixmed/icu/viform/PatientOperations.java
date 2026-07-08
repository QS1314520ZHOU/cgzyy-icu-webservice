package com.digixmed.icu.viform;

import lombok.Data;

import java.util.Date;

@Data
public class PatientOperations {
    private Date startTime;
    private Date endTime;
    private String name;
}
