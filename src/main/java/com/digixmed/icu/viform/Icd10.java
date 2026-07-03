package com.digixmed.icu.viform;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("VI_ICU_ICD")
@Data
public class Icd10 {
    @Id
    private String id;
    private String icdCode;
    private String icdName;
}
