package com.digixmed.icu.util;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.springframework.util.StringUtils;

public class DataUtils {
    public static final String PARAM_T = "param_T";
    public static final String PARAM_HR = "param_HR";
    public static final String PARAM_PR = "param_PR";
    public static final String PARAM_RESP = "param_resp";
    public static final String PARAM_TI_WEN_BU_WEI = "param_tiWenBuWei";
    public static final String PARAM_SPO2 = "param_spo2";
    public static final String PARAM_XUEYA = "xueya";
    public static final String PARAM_IBP_S = "param_ibp_s";
    public static final String PARAM_IBP_D = "param_ibp_d";
    public static final String PARAM_NBP_S = "param_nibp_s";
    public static final String PARAM_NBP_D = "param_nibp_d";
    public static final String PARAM_IBP_PART_S = "param_ibp_pART_s";
    public static final String PARAM_IBP_PART_D = "param_ibp_pART_d";
    public static final String PARAM_XI_YANG_TU_JING = "param_XiYangTuJing";
    public static final String PARAM_VENTILATOR = "param_呼吸机";
    public static final String PARAM_POOP = "param_daBianAmount";
    public static final String PARAM_PEE = "param_niaoLiang";
    public static final String PARAM_TAN_LIANG = "param_tanLiang";
    public static final String PARAM_IN_VOLUME = "param_in_hour_sum";
    public static final String PARAM_OUT_VOLUME = "param_out_hour_sum";
    public static final String PARAM_CPOT_SCORE = "param_zhenTong_CPOT_score";
    public static final String PARAM_NRS_SCORE = "param_zhenTong_NRS_score";

    public static List<String> getCountCodeList() {
        List<String> codeList = new ArrayList<>();
        codeList.add("param_daBianAmount");
        codeList.add("param_niaoLiang");
        codeList.add("param_tanLiang");
        return codeList;
    }

    public static String getDocDataStr(Document doc, String subDocName, String key) {
        Document subDoc;
        if (doc == null || (subDoc = (Document) doc.get(subDocName, Document.class)) == null) {
            return null;
        }
        return subDoc.getString(key);
    }

    public static String formatDouble(String value) {
        if (StringUtils.isEmpty(value)) {
            return "0";
        }
        String[] valueArr = value.split("\\.");
        return valueArr[0];
    }

    public static String bedsideCode2OracleCode(String code) {
        String result;
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        switch (code) {
            case "param_T":
                result = "TW";
                break;
            case "param_HR":
                result = "XL";
                break;
            case "param_PR":
                result = "MB";
                break;
            case "param_resp":
                result = "HX";
                break;
            case "xueya":
                result = "XY";
                break;
            case "param_daBianAmount":
                result = "DBCS";
                break;
            case "param_niaoLiang":
                result = "XBCS";
                break;
            case "param_tanLiang":
                result = "TL";
                break;
            case "param_in_hour_sum":
                result = "ZRL";
                break;
            case "param_out_hour_sum":
                result = "ZCL";
                break;
            case "param_zhenTong_CPOT_score":
            case "param_zhenTong_NRS_score":
                result = "TTZ";
                break;
            default:
                result = "";
                break;
        }
        return result;
    }

    public static boolean isUserDefault(String speed) {
        boolean isUserDefault = true;
        if (StringUtils.isEmpty(speed)) {
            return true;
        }
        try {
            if (speed.contains("/")) {
                String[] speedArray = speed.split("/");
                if (speedArray.length == 2) {
                    String speedWithUnit = speedArray[0];
                    if (speedWithUnit.contains("ml")) {
                        int length = speedWithUnit.length();
                        String tempSpeed = speedWithUnit.substring(0, length - 2);
                        float speedF = Float.parseFloat(tempSpeed);
                        if (speedF != 0.0f) {
                            isUserDefault = false;
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return isUserDefault;
    }

    public static void main(String[] args) {
        isUserDefault(" ml/分");
    }
}