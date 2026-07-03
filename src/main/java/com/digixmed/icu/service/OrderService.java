package com.digixmed.icu.service;

import cn.hutool.core.util.StrUtil;
import com.digixmed.icu.dao.MongoDao;
import com.digixmed.icu.util.DataUtil;
import com.digixmed.icu.util.DataUtils;
import com.digixmed.icu.viform.InHospitalInfo;
import com.digixmed.icu.viform.Order;
import com.digixmed.icu.viform.OrderExecute;
import com.digixmed.icu.viform.PatientInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class OrderService extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    MongoDao mongoDao;

    @Value("${digixmed.dept-code:1325257}")
    String deptCode;

    @Value("${digixmed.is-auto-exec:false}")
    boolean isAutoExec;

    @Resource
    private ViPatInfoService patInfoService;

    public void handleOrder(Element bodyElement) throws Exception {
        InHospitalInfo inHospitalInfo;
        PatientInfo patient;
        List<Element> elements = bodyElement.elements("Field");
        if (CollectionUtils.isEmpty(elements)) {
            return;
        }
        String pid = null;
        String mrn = "";
        for (Element element : elements) {
            if ("CHZBS".equals(element.attribute("Name").getValue())) {
                pid = element.attribute("Value").getValue();
                continue;
            }
            if ("CZYH".equals(element.attribute("Name").getValue())) {
                mrn = element.attribute("Value").getValue();
                continue;
            }
        }
        if (StringUtils.isEmpty(pid) || (inHospitalInfo = this.mongoDao.getIcuPatientInfoByPid(pid,mrn)) == null) {
            return;
        }
        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        order.setName(inHospitalInfo.getName());
        order.setGender(inHospitalInfo.getGender());
        order.setPid(inHospitalInfo.getPid());
        order.setMrn(inHospitalInfo.getMrn());
        String cyzph="";
        String status="";
        for (Element element2 : elements) {
            String name = element2.attribute("Name").getValue();
            String value = element2.attribute("Value").getValue();
            if(StrUtil.isBlank(value)||value.contains("NULL"))continue;
            if (value != null) {
                switch (name) {
                    case "CYZMXID":
                        order.setOrderID(value);
                        break;
                    case "CYZLBBM":
                        if ("1".equals(value)) {
                            value = "长期";
                        }
                        if ("2".equals(value)) {
                            value = "临时";
                        }
                        order.setOrderType(value);
                        break;
                    case "CYZXMLXMC":
                        order.setYaoType(value);
                        break;
                    case "CYWSYPCBM":
                        order.setFreq(value);
                        break;
                    case "NYWSYCJL":
                        order.setDose(value);
                        break;
                    case "CYWSYCJLDWMC":
                        order.setUnit(value);
                        break;
                    case "CYWSYTJMC":
                        order.setExeMethod(value);
                        break;
                    case "CYWSYTJBM":
                        order.setExeMethodCode(value);
                        break;
                    case "CYZXMMC":
                        order.setOrderName(value);
                        break;
                    case "CYZXMNF":
                        order.setOrderName(order.getOrderName() + "," + value);
                        break;
                    case "CYZXMBM":
                        order.setOrderYaoCode(value);
                        break;
                    case "CYZPH":
                        order.setGroupID(value);
                        break;
                    case "CYWGG":
                        order.setSpec(value);
                        break;
                    case "DYZKLRQSJ":
                        order.setOrderTime(DataUtil.stringToDate(value, "yyyy-MM-dd HH:mm:ss"));
                        order.setReviewTime(DataUtil.stringToDate(value, "yyyy-MM-dd HH:mm:ss"));
                        break;
                    case "DYZJHKSSJ":
                        order.setPlanTime(DataUtil.stringToDate(value, "yyyy-MM-dd HH:mm:ss"));
                        break;
                    case "CYZKLZBM":
                        order.setOrderDoctorID(value);
                        break;
                    case "CYZKLZQM":
                        order.setOrderDoctor(value);
                        break;
                    case "CYZSHRQM":
                        order.setReviewer(value);
                        break;
                    case "CYZSHRBM":
                        order.setReviewerID(value);
                        break;
                    case "CYZZTBM":
                        order.setStatus(getOrderStatusFlag(value));
                        break;
                    case "DYZTZRQSJ":
                        order.setStopTime(DataUtil.stringToDate(value, "yyyy-MM-dd HH:mm:ss"));
                        break;
                    case "DYZQXSJ":
                        order.setCancelTime(DataUtil.stringToDate(value, "yyyy-MM-dd HH:mm:ss"));
                        break;
                    case "CYZBZXX":
                        order.setNotes(value);
                        break;
                    case "CYZZH":
                        cyzph=value;
                        break;
                    case "BSJZT":
                        status=value;
                        break;
                }
            }
        }
        if (order.getYaoType() != null && order.getYaoType().contains("输血类医嘱") && order.getExeMethod() == null) {
            order.setExeMethod("输血");
            order.setExeMethodCode("sx");
        }
        //CYZPH+医嘱组号
        if(StrUtil.isNotBlank(cyzph)){
            order.setGroupID(order.getGroupID()+cyzph);
        }
        orderList.add(order);
        this.mongoDao.updateOrSaveOrder(orderList);
        String[] strs = {"转科", "出院"};
        boolean isUpdate = false;
        String orderName = order.getOrderName();
        for (int i = 0; i < strs.length; i++) {
            if (orderName.contains(strs[i])) {
                isUpdate = true;
            }
        }
        if (isUpdate && (patient = this.mongoDao.getPatientInfoByPid(inHospitalInfo.getPid(),inHospitalInfo.getMrn())) != null) {
            patient.setZyzt("出院");
            Date time = order.getOrderTime();
            if (order.getStopTime() != null) {
                time = order.getStopTime();
            }

            if(orderName.contains("转科")){
                patient.setDischargedType("转出");
            }
            if(orderName.contains("出院")){
                if(orderName.contains("死亡")){
                    patient.setDischargedType("死亡");
                    patient.setDeathTime(time);
                }else {
                    patient.setDischargedType("出院");
                }
            }
            patient.setDischargeTime(time);
            this.mongoDao.save(patient);
            //通过下达的医嘱转出
            this.patInfoService.transferOut(patient, time,status);
        }
    }

    public void handleOrderExec(Element bodyElement) throws Exception {
        Element orderExecuteInfoRt = bodyElement.element("OrderExecuteInfoRt");
        List<Element> oeorIInfoElementList = getElements(orderExecuteInfoRt, "OEORIInfoList", "OEORIInfo");
        if (oeorIInfoElementList == null || oeorIInfoElementList.isEmpty()) {
            return;
        }
        List<OrderExecute> executeList = new ArrayList<>();
        for (Element element : oeorIInfoElementList) {
            OrderExecute execute4His = new OrderExecute();
            String orderId = getElementText(element, "OEORIOrderItemID");
            if (!StringUtils.isEmpty(orderId)) {
                Order order = this.mongoDao.getOrderByOrderId(orderId);
                if (order != null) {
                    execute4His.setExeId(getElementText(element, "OrderExecuteID"));
                    execute4His.setStatus(getOrderExecStatusFlag(getElementText(element, "OrderExecuteStatus")));
                    execute4His.setPlanTime(getDateTime(element, "OrderRequredExDate", "OrderRequredExTime"));
                    execute4His.setCheckTime(getDateTime(element, "UpdateDate", "UpdateTime"));
                    if (this.isAutoExec) {
                        execute4His.setExeTime(execute4His.getCheckTime());
                    }
                    execute4His.setOrderID(getElementText(element, "OEORIOrderItemID"));
                    execute4His.setBarCode("PDA扫描条码1");
                    String speed = getElementText(element, "Speed");
                    execute4His.setSourceSpeed(speed);
                    if (DataUtils.isUserDefault(speed)) {
                        speed = "1 ml/h";
                    }
                    execute4His.setSpeed(speed);
                    executeList.add(execute4His);
                }
            }
        }
        this.mongoDao.updateOrSaveOrderExec(executeList);
    }

    public void handleOrderStateChange(Element bodyElement) throws Exception {
        List<Element> elements = bodyElement.elements("Field");
        if (CollectionUtils.isEmpty(elements)) {
            return;
        }
        String orderId = null;
        String oeoriStatusCode = null;
        for (Element element : elements) {
            String name = element.attribute("Name").getValue();
            if ("CYZMXID".equals(name)) {
                orderId = element.attribute("Value").getValue();
                continue;
            }
            if ("CYZZTDM".equals(name)) {
                oeoriStatusCode = element.attribute("Value").getValue();
                continue;
            }
        }
        if (StringUtils.isEmpty(orderId)) {
            return;
        }
        List<Order> orderList = new ArrayList<>();
        Order order = this.mongoDao.getOrderByOrderId(orderId);
        if (order == null) {
            throw new Exception("没有查询到相关医嘱");
        }
        if (StringUtils.isEmpty(oeoriStatusCode)) {
            throw new Exception("oeoriStatusCode==NULL");
        }
        order.setStatus(oeoriStatusCode);
        orderList.add(order);
        this.mongoDao.updateOrSaveOrder(orderList);
    }

    public String getOrderTypeFlag(String orderType) {
        String flag;
        if (StringUtils.isEmpty(orderType)) {
            return "";
        }
        flag = orderType;
        switch (orderType) {
            case "长期医嘱":
                flag = "长期";
                break;
            case "临时医嘱":
                flag = "临时";
                break;
        }
        return flag;
    }

    public String getYaoTypeFlag(String yaoType) {
        String yaoTypeFlag;
        if (StringUtils.isEmpty(yaoType)) {
            return "";
        }
        yaoTypeFlag = yaoType;
        switch (yaoType) {
            case "西成药":
                yaoTypeFlag = "4";
                break;
            case "中成药":
                yaoTypeFlag = "5";
                break;
            case "输血":
                yaoTypeFlag = "K";
                break;
        }
        return yaoTypeFlag;
    }

    public String getOrderStatusFlag(String orderStatus) {
        String orderStatusFlag;
        if (StringUtils.isEmpty(orderStatus)) {
            return "";
        }
        orderStatusFlag = orderStatus;
        switch (orderStatus) {
            case "01":
                orderStatusFlag = "新开";
                break;
            case "02":
                orderStatusFlag = "已发送";
                break;
            case "03":
                orderStatusFlag = "已校对";
                break;
            case "04":
                orderStatusFlag = "在执行";
                break;
            case "05":
                orderStatusFlag = "执行完";
                break;
            case "06":
                orderStatusFlag = "已停止";
                break;
            case "07":
                orderStatusFlag = "作废";
                break;
            case "08":
                orderStatusFlag = "申请";
                break;
            case "09":
                orderStatusFlag = "医生取消";
                break;
            case "10":
                orderStatusFlag = "护士取消";
                break;
            case "11":
                orderStatusFlag = "已收费";
                break;
            case "12":
                orderStatusFlag = "已退回";
                break;
            case "13":
                orderStatusFlag = "已执行";
                break;
            case "17":
                orderStatusFlag = "暂停执行";
                break;
            case "20":
                orderStatusFlag = "取消执行";
            case "19":
                orderStatusFlag = "取消审核";
                break;
            case "21":
                orderStatusFlag = "取消停嘱";
                break;
            case "99":
                orderStatusFlag = "自定义";
                break;
        }
        return orderStatusFlag;
    }

    public String getOrderExecStatusFlag(String flag) {
        String status;
        if (StringUtils.isEmpty(flag)) {
            flag = "";
        }
        status = "已执行";
        switch (flag) {
            case "F":
                status = "已执行";
                break;
            case "D":
                status = "停止执行";
                break;
            case "C":
                status = "撤销执行";
                break;
        }
        return status;
    }
}