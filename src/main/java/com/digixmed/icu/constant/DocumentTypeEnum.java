package com.digixmed.icu.constant;

/* JADX INFO: loaded from: jdqzyy-icu-webservice-1.0-SNAPSHOT.jar:com/digixmed/icu/constant/DocumentTypeEnum.class */
public enum DocumentTypeEnum {
    EXAM_REPORT("01001", "检验报告"),
    WEI_SHENG_WU_REPORT("01002", "微生物检验报告"),
    FANG_SHE("02001", "放射"),
    CHAO_SHENG("02002", "超声"),
    NEI_JING("02003", "内镜"),
    XIN_DIAN("02004", "心电"),
    HE_YI_XUE("02005", "核医学"),
    BING_LI_REPORT("02006", "病理报告");

    private String code;
    private String desc;

    DocumentTypeEnum(String code, String desc) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getCode() {
        return this.code;
    }
}