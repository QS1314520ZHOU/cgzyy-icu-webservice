package com.digixmed.icu.service;

import com.digixmed.icu.constant.Cache;
import com.digixmed.icu.dao.MongoDao;
import com.digixmed.icu.pojo.Account;
import com.digixmed.icu.util.DataUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digixmed.icu.viform.DeptInfo;
import org.dom4j.Element;
import org.springframework.util.StringUtils;

public class BaseService {
    public List<Element> getElements(Element element, String... keys) {
        List<Element> elements = new ArrayList();
        if (element == null || keys == null || keys.length == 0) {
            return elements;
        }
        Element element1 = element;
        for (int index = 0; index < keys.length && element1 != null; index++) {
            if (index < keys.length - 1) {
                element1 = element1.element(keys[index]);
            } else {
                elements = element1.elements(keys[index]);
            }
        }
        return elements;
    }

    public Date getDateTime(Element element, String dateKey, String timeKey) {
        String date = element.elementText(dateKey);
        String time = element.elementText(timeKey);
        if (!StringUtils.isEmpty(date) && !StringUtils.isEmpty(time)) {
            return DataUtil.stringToDate(date + " " + time, "yyyy-MM-dd HH:mm:ss");
        }
        return null;
    }

    public String getAccountName(String workId, MongoDao mongoDao) {
        Account account;
        if (StringUtils.isEmpty(workId) || !StringUtils.isEmpty(Cache.workIdVNameMap.get(workId)) || (account = mongoDao.findAccountByWorkId(workId)) == null) {
            return null;
        }
        String name = account.getTrueName();
        if (StringUtils.isEmpty(name)) {
            Cache.workIdVNameMap.put(workId, name);
            return null;
        }
        return null;
    }

    public String getDeptName(String outDeptCode, MongoDao mongoDao) {
        DeptInfo deptInfo = mongoDao.findDeptInfo(outDeptCode);
        if(deptInfo==null){
            return null;
        }else {
            return deptInfo.getDeptName();
        }
    }

    public String getElementText(Element element, String key) {
        if (element == null) {
            return "";
        }
        try {
            return element.elementText(key);
        } catch (Exception e) {
            return "";
        }
    }

    public Element getElement(Element element, String key) {
        if (element == null) {
            return null;
        }
        return element.element(key);
    }

    public List<Element> getElements(Element element, String key) {
        if (element == null) {
            return null;
        }
        return element.elements(key);
    }

    public Date getDateTime(String date, String time) {
        if (!StringUtils.isEmpty(date) && !StringUtils.isEmpty(time)) {
            String dateTimeValue = date + " " + time;
            return DataUtil.stringToDate(dateTimeValue, "yyyy-MM-dd HH:mm:ss");
        }
        return null;
    }
}