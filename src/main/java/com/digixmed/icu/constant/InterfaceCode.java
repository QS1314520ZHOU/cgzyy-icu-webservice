package com.digixmed.icu.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/* JADX INFO: loaded from: jdqzyy-icu-webservice-1.0-SNAPSHOT.jar:com/digixmed/icu/constant/InterfaceCode.class */
public enum InterfaceCode {
    STAFF_DICT("MES0001", "医护人员字典"),
    DEPT_DICT("MES0002", "科室字典"),
    FREQ_DICT("MES0003", "药物频次"),
    DRUG_METHOD_DICT("MES0004", "用药途径字典"),
    BED_DICT("MES0005", "床位字典"),
    WARD_DICT("MES0006", "病区字典"),
    PATIENT_INFO("MES0011", "患者基本信息"),
    INPATIENT_ENCOUNTER("MES0012", "住院登记"),
    INPATIENT_ENCOUNTER_CANCEL("MES0013", "取消住院登记"),
    INPATIENT_DISCHARGE("MES0014", "患者出院"),
    INPATIENT_DISCHARGE_CANCEL("MES0015", "取消患者出院"),
    ADM_TRANSACTION("MES0016", "转科、转床"),
    ORDER_INFO("MES0017", "医嘱"),
    ORDER_STATE_CHANGE("MES0018", "医嘱状态改变"),
    ORDER_EXEC("MES0018", "医嘱执行数据"),
    DOCUMENT_SEARCH("MES0142", "检验文档检索"),
    DOCUMENT_READ("MES0143", "检验文档调阅"),
    SEND_ORDER_EXEC("MES0149", "推送医嘱执行数据"),
    ORDER_EXEC_BACK("T0050", "医嘱执行数据回传"),
    TI_WEN_DAN_BACK("MES0032", "体温单回写"),
    DOCUMENT_TEST("C0089", "文档检索测试"),
    TI_WEN_DAN_TEST("C0090", "体温单测试"),
    REQUEST_ICU_TIME("MES0050", "请求ICU 出入科时间"),
    DEFAULT("default", "未找到该服务编码"),
    ICD10("default", "ICD10");

    private String code;
    private String desc;

    InterfaceCode(String code, String desc) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static InterfaceCode getInterfaceCode(String code) {
        List<InterfaceCode> collect = (List) Arrays.stream(values()).filter(value -> {
            return value.code.equals(code);
        }).collect(Collectors.toList());
        if (collect.isEmpty()) {
            DEFAULT.setCode(code);
            return DEFAULT;
        }
        return collect.get(0);
    }
}