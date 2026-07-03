package com.digixmed.icu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.util.StringUtils;

public class DataUtil {
    public static final long ONE_DAY_LONG = 86400000;

    public static String getOperLevel(String level) {
        String grade = null;
        if (StringUtils.isEmpty(level)) {
            return null;
        }
        switch (level) {
            case "一级":
                grade = "Ⅰ";
                break;
            case "二级":
                grade = "Ⅱ";
                break;
            case "三级":
                grade = "Ⅲ";
                break;
            case "四级":
                grade = "Ⅳ";
                break;
        }
        return grade;
    }

    public static Date stringToDate(String dateStr, String format) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getOprType(String oprType) {
        String applyType = null;
        if (StringUtils.isEmpty(oprType)) {
            return null;
        }
        switch (oprType) {
            case "0":
                applyType = "择期";
                break;
            case "1":
                applyType = "急诊";
                break;
        }
        return applyType;
    }

    public static String getApplyStatus(String status) {
        String applyStatus;
        if (StringUtils.isEmpty(status)) {
            return null;
        }
        applyStatus = null;
        switch (status) {
            case "N":
            case "G":
                applyStatus = "新申请";
                break;
            case "C":
                applyStatus = "已取消";
                break;
            case "F":
                applyStatus = "已完成";
                break;
        }
        return applyStatus;
    }

    public static String getAgeStr(Date dob, Date inRoomTime) {
        if (dob == null) {
            return "";
        }
        if (inRoomTime == null) {
            inRoomTime = new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(inRoomTime);
        int yearNow = cal.get(1);
        int monthNow = cal.get(2) + 1;
        int dayOfMonthNow = cal.get(5);
        int dayOfMonth = cal.getActualMaximum(5);
        cal.setTime(dob);
        int yearBirth = cal.get(1);
        int monthBirth = cal.get(2) + 1;
        int dayOfMonthBirth = cal.get(5);
        int ageYear = yearNow - yearBirth;
        int ageMonth = monthNow - monthBirth;
        int ageDay = dayOfMonthNow - dayOfMonthBirth;
        if (ageDay < 0) {
            ageDay += dayOfMonth;
            ageMonth--;
        }
        if (ageMonth < 0) {
            ageMonth += 12;
            ageYear--;
        }
        if (ageYear > 1 || (ageYear == 1 && ageMonth == 0)) {
            return ageYear + "岁";
        }
        if (ageYear == 1 && ageMonth > 0) {
            return ageYear + "(" + ageMonth + "/12)岁";
        }
        if (ageMonth > 0 || ageDay == 0) {
            return ageMonth + "月";
        }
        long dateLong = inRoomTime.getTime() - dob.getTime();
        int days = (int) (dateLong / ONE_DAY_LONG);
        if (dateLong % ONE_DAY_LONG != 0) {
            days++;
        }
        return days + "天";
    }
}