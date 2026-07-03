package com.digixmed.icu.viform;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("VI_ICU_ZYYZ")
public class Order {

    @Id
    private String id;

    @Indexed(direction = IndexDirection.DESCENDING)
    private String orderID;

    @Indexed(direction = IndexDirection.DESCENDING)
    private String pid;

    @Indexed(direction = IndexDirection.DESCENDING)
    private String mrn;
    private String zycs;
    private String jzkh;
    private String name;
    private String gender;
    private String orderType;
    private String yaoType;
    private String freq;
    private String dose;
    private String unit;
    private String exeMethod;
    private String exeMethodCode;
    private String longOrderID;
    private String orderName;
    private String orderYaoCode;
    private String groupID;
    private String spec;
    private Date planTime;
    private String orderDoctor;
    private String orderDoctorID;
    private String reviewer;
    private String reviewerID;
    private String status;

    @Indexed(direction = IndexDirection.DESCENDING)
    private Date orderTime;
    private Date reviewTime;
    private Date cancelTime;
    private Date stopTime;
    private String notes;
    private String jslyw;
    private String recordPrt;
    private String paySelf;
    private String everyTimedose;

    public void setId(final String id) {
        this.id = id;
    }

    public void setOrderID(final String orderID) {
        this.orderID = orderID;
    }

    public void setPid(final String pid) {
        this.pid = pid;
    }

    public void setMrn(final String mrn) {
        this.mrn = mrn;
    }

    public void setZycs(final String zycs) {
        this.zycs = zycs;
    }

    public void setJzkh(final String jzkh) {
        this.jzkh = jzkh;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public void setOrderType(final String orderType) {
        this.orderType = orderType;
    }

    public void setYaoType(final String yaoType) {
        this.yaoType = yaoType;
    }

    public void setFreq(final String freq) {
        this.freq = freq;
    }

    public void setDose(final String dose) {
        this.dose = dose;
    }

    public void setUnit(final String unit) {
        this.unit = unit;
    }

    public void setExeMethod(final String exeMethod) {
        this.exeMethod = exeMethod;
    }

    public void setExeMethodCode(final String exeMethodCode) {
        this.exeMethodCode = exeMethodCode;
    }

    public void setLongOrderID(final String longOrderID) {
        this.longOrderID = longOrderID;
    }

    public void setOrderName(final String orderName) {
        this.orderName = orderName;
    }

    public void setOrderYaoCode(final String orderYaoCode) {
        this.orderYaoCode = orderYaoCode;
    }

    public void setGroupID(final String groupID) {
        this.groupID = groupID;
    }

    public void setSpec(final String spec) {
        this.spec = spec;
    }

    public void setPlanTime(final Date planTime) {
        this.planTime = planTime;
    }

    public void setOrderDoctor(final String orderDoctor) {
        this.orderDoctor = orderDoctor;
    }

    public void setOrderDoctorID(final String orderDoctorID) {
        this.orderDoctorID = orderDoctorID;
    }

    public void setReviewer(final String reviewer) {
        this.reviewer = reviewer;
    }

    public void setReviewerID(final String reviewerID) {
        this.reviewerID = reviewerID;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public void setOrderTime(final Date orderTime) {
        this.orderTime = orderTime;
    }

    public void setReviewTime(final Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public void setCancelTime(final Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public void setStopTime(final Date stopTime) {
        this.stopTime = stopTime;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }

    public void setJslyw(final String jslyw) {
        this.jslyw = jslyw;
    }

    public void setRecordPrt(final String recordPrt) {
        this.recordPrt = recordPrt;
    }

    public void setPaySelf(final String paySelf) {
        this.paySelf = paySelf;
    }

    public void setEveryTimedose(final String everyTimedose) {
        this.everyTimedose = everyTimedose;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        Order other = (Order) o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$id = getId();
        Object other$id = other.getId();
        if (this$id == null) {
            if (other$id != null) {
                return false;
            }
        } else if (!this$id.equals(other$id)) {
            return false;
        }
        Object this$orderID = getOrderID();
        Object other$orderID = other.getOrderID();
        if (this$orderID == null) {
            if (other$orderID != null) {
                return false;
            }
        } else if (!this$orderID.equals(other$orderID)) {
            return false;
        }
        Object this$pid = getPid();
        Object other$pid = other.getPid();
        if (this$pid == null) {
            if (other$pid != null) {
                return false;
            }
        } else if (!this$pid.equals(other$pid)) {
            return false;
        }
        Object this$mrn = getMrn();
        Object other$mrn = other.getMrn();
        if (this$mrn == null) {
            if (other$mrn != null) {
                return false;
            }
        } else if (!this$mrn.equals(other$mrn)) {
            return false;
        }
        Object this$zycs = getZycs();
        Object other$zycs = other.getZycs();
        if (this$zycs == null) {
            if (other$zycs != null) {
                return false;
            }
        } else if (!this$zycs.equals(other$zycs)) {
            return false;
        }
        Object this$jzkh = getJzkh();
        Object other$jzkh = other.getJzkh();
        if (this$jzkh == null) {
            if (other$jzkh != null) {
                return false;
            }
        } else if (!this$jzkh.equals(other$jzkh)) {
            return false;
        }
        Object this$name = getName();
        Object other$name = other.getName();
        if (this$name == null) {
            if (other$name != null) {
                return false;
            }
        } else if (!this$name.equals(other$name)) {
            return false;
        }
        Object this$gender = getGender();
        Object other$gender = other.getGender();
        if (this$gender == null) {
            if (other$gender != null) {
                return false;
            }
        } else if (!this$gender.equals(other$gender)) {
            return false;
        }
        Object this$orderType = getOrderType();
        Object other$orderType = other.getOrderType();
        if (this$orderType == null) {
            if (other$orderType != null) {
                return false;
            }
        } else if (!this$orderType.equals(other$orderType)) {
            return false;
        }
        Object this$yaoType = getYaoType();
        Object other$yaoType = other.getYaoType();
        if (this$yaoType == null) {
            if (other$yaoType != null) {
                return false;
            }
        } else if (!this$yaoType.equals(other$yaoType)) {
            return false;
        }
        Object this$freq = getFreq();
        Object other$freq = other.getFreq();
        if (this$freq == null) {
            if (other$freq != null) {
                return false;
            }
        } else if (!this$freq.equals(other$freq)) {
            return false;
        }
        Object this$dose = getDose();
        Object other$dose = other.getDose();
        if (this$dose == null) {
            if (other$dose != null) {
                return false;
            }
        } else if (!this$dose.equals(other$dose)) {
            return false;
        }
        Object this$unit = getUnit();
        Object other$unit = other.getUnit();
        if (this$unit == null) {
            if (other$unit != null) {
                return false;
            }
        } else if (!this$unit.equals(other$unit)) {
            return false;
        }
        Object this$exeMethod = getExeMethod();
        Object other$exeMethod = other.getExeMethod();
        if (this$exeMethod == null) {
            if (other$exeMethod != null) {
                return false;
            }
        } else if (!this$exeMethod.equals(other$exeMethod)) {
            return false;
        }
        Object this$exeMethodCode = getExeMethodCode();
        Object other$exeMethodCode = other.getExeMethodCode();
        if (this$exeMethodCode == null) {
            if (other$exeMethodCode != null) {
                return false;
            }
        } else if (!this$exeMethodCode.equals(other$exeMethodCode)) {
            return false;
        }
        Object this$longOrderID = getLongOrderID();
        Object other$longOrderID = other.getLongOrderID();
        if (this$longOrderID == null) {
            if (other$longOrderID != null) {
                return false;
            }
        } else if (!this$longOrderID.equals(other$longOrderID)) {
            return false;
        }
        Object this$orderName = getOrderName();
        Object other$orderName = other.getOrderName();
        if (this$orderName == null) {
            if (other$orderName != null) {
                return false;
            }
        } else if (!this$orderName.equals(other$orderName)) {
            return false;
        }
        Object this$orderYaoCode = getOrderYaoCode();
        Object other$orderYaoCode = other.getOrderYaoCode();
        if (this$orderYaoCode == null) {
            if (other$orderYaoCode != null) {
                return false;
            }
        } else if (!this$orderYaoCode.equals(other$orderYaoCode)) {
            return false;
        }
        Object this$groupID = getGroupID();
        Object other$groupID = other.getGroupID();
        if (this$groupID == null) {
            if (other$groupID != null) {
                return false;
            }
        } else if (!this$groupID.equals(other$groupID)) {
            return false;
        }
        Object this$spec = getSpec();
        Object other$spec = other.getSpec();
        if (this$spec == null) {
            if (other$spec != null) {
                return false;
            }
        } else if (!this$spec.equals(other$spec)) {
            return false;
        }
        Object this$planTime = getPlanTime();
        Object other$planTime = other.getPlanTime();
        if (this$planTime == null) {
            if (other$planTime != null) {
                return false;
            }
        } else if (!this$planTime.equals(other$planTime)) {
            return false;
        }
        Object this$orderDoctor = getOrderDoctor();
        Object other$orderDoctor = other.getOrderDoctor();
        if (this$orderDoctor == null) {
            if (other$orderDoctor != null) {
                return false;
            }
        } else if (!this$orderDoctor.equals(other$orderDoctor)) {
            return false;
        }
        Object this$orderDoctorID = getOrderDoctorID();
        Object other$orderDoctorID = other.getOrderDoctorID();
        if (this$orderDoctorID == null) {
            if (other$orderDoctorID != null) {
                return false;
            }
        } else if (!this$orderDoctorID.equals(other$orderDoctorID)) {
            return false;
        }
        Object this$reviewer = getReviewer();
        Object other$reviewer = other.getReviewer();
        if (this$reviewer == null) {
            if (other$reviewer != null) {
                return false;
            }
        } else if (!this$reviewer.equals(other$reviewer)) {
            return false;
        }
        Object this$reviewerID = getReviewerID();
        Object other$reviewerID = other.getReviewerID();
        if (this$reviewerID == null) {
            if (other$reviewerID != null) {
                return false;
            }
        } else if (!this$reviewerID.equals(other$reviewerID)) {
            return false;
        }
        Object this$status = getStatus();
        Object other$status = other.getStatus();
        if (this$status == null) {
            if (other$status != null) {
                return false;
            }
        } else if (!this$status.equals(other$status)) {
            return false;
        }
        Object this$orderTime = getOrderTime();
        Object other$orderTime = other.getOrderTime();
        if (this$orderTime == null) {
            if (other$orderTime != null) {
                return false;
            }
        } else if (!this$orderTime.equals(other$orderTime)) {
            return false;
        }
        Object this$reviewTime = getReviewTime();
        Object other$reviewTime = other.getReviewTime();
        if (this$reviewTime == null) {
            if (other$reviewTime != null) {
                return false;
            }
        } else if (!this$reviewTime.equals(other$reviewTime)) {
            return false;
        }
        Object this$cancelTime = getCancelTime();
        Object other$cancelTime = other.getCancelTime();
        if (this$cancelTime == null) {
            if (other$cancelTime != null) {
                return false;
            }
        } else if (!this$cancelTime.equals(other$cancelTime)) {
            return false;
        }
        Object this$stopTime = getStopTime();
        Object other$stopTime = other.getStopTime();
        if (this$stopTime == null) {
            if (other$stopTime != null) {
                return false;
            }
        } else if (!this$stopTime.equals(other$stopTime)) {
            return false;
        }
        Object this$notes = getNotes();
        Object other$notes = other.getNotes();
        if (this$notes == null) {
            if (other$notes != null) {
                return false;
            }
        } else if (!this$notes.equals(other$notes)) {
            return false;
        }
        Object this$jslyw = getJslyw();
        Object other$jslyw = other.getJslyw();
        if (this$jslyw == null) {
            if (other$jslyw != null) {
                return false;
            }
        } else if (!this$jslyw.equals(other$jslyw)) {
            return false;
        }
        Object this$recordPrt = getRecordPrt();
        Object other$recordPrt = other.getRecordPrt();
        if (this$recordPrt == null) {
            if (other$recordPrt != null) {
                return false;
            }
        } else if (!this$recordPrt.equals(other$recordPrt)) {
            return false;
        }
        Object this$paySelf = getPaySelf();
        Object other$paySelf = other.getPaySelf();
        if (this$paySelf == null) {
            if (other$paySelf != null) {
                return false;
            }
        } else if (!this$paySelf.equals(other$paySelf)) {
            return false;
        }
        Object this$everyTimedose = getEveryTimedose();
        Object other$everyTimedose = other.getEveryTimedose();
        return this$everyTimedose == null ? other$everyTimedose == null : this$everyTimedose.equals(other$everyTimedose);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Order;
    }

    public int hashCode() {
        Object $id = getId();
        int result = (1 * 59) + ($id == null ? 43 : $id.hashCode());
        Object $orderID = getOrderID();
        int result2 = (result * 59) + ($orderID == null ? 43 : $orderID.hashCode());
        Object $pid = getPid();
        int result3 = (result2 * 59) + ($pid == null ? 43 : $pid.hashCode());
        Object $mrn = getMrn();
        int result4 = (result3 * 59) + ($mrn == null ? 43 : $mrn.hashCode());
        Object $zycs = getZycs();
        int result5 = (result4 * 59) + ($zycs == null ? 43 : $zycs.hashCode());
        Object $jzkh = getJzkh();
        int result6 = (result5 * 59) + ($jzkh == null ? 43 : $jzkh.hashCode());
        Object $name = getName();
        int result7 = (result6 * 59) + ($name == null ? 43 : $name.hashCode());
        Object $gender = getGender();
        int result8 = (result7 * 59) + ($gender == null ? 43 : $gender.hashCode());
        Object $orderType = getOrderType();
        int result9 = (result8 * 59) + ($orderType == null ? 43 : $orderType.hashCode());
        Object $yaoType = getYaoType();
        int result10 = (result9 * 59) + ($yaoType == null ? 43 : $yaoType.hashCode());
        Object $freq = getFreq();
        int result11 = (result10 * 59) + ($freq == null ? 43 : $freq.hashCode());
        Object $dose = getDose();
        int result12 = (result11 * 59) + ($dose == null ? 43 : $dose.hashCode());
        Object $unit = getUnit();
        int result13 = (result12 * 59) + ($unit == null ? 43 : $unit.hashCode());
        Object $exeMethod = getExeMethod();
        int result14 = (result13 * 59) + ($exeMethod == null ? 43 : $exeMethod.hashCode());
        Object $exeMethodCode = getExeMethodCode();
        int result15 = (result14 * 59) + ($exeMethodCode == null ? 43 : $exeMethodCode.hashCode());
        Object $longOrderID = getLongOrderID();
        int result16 = (result15 * 59) + ($longOrderID == null ? 43 : $longOrderID.hashCode());
        Object $orderName = getOrderName();
        int result17 = (result16 * 59) + ($orderName == null ? 43 : $orderName.hashCode());
        Object $orderYaoCode = getOrderYaoCode();
        int result18 = (result17 * 59) + ($orderYaoCode == null ? 43 : $orderYaoCode.hashCode());
        Object $groupID = getGroupID();
        int result19 = (result18 * 59) + ($groupID == null ? 43 : $groupID.hashCode());
        Object $spec = getSpec();
        int result20 = (result19 * 59) + ($spec == null ? 43 : $spec.hashCode());
        Object $planTime = getPlanTime();
        int result21 = (result20 * 59) + ($planTime == null ? 43 : $planTime.hashCode());
        Object $orderDoctor = getOrderDoctor();
        int result22 = (result21 * 59) + ($orderDoctor == null ? 43 : $orderDoctor.hashCode());
        Object $orderDoctorID = getOrderDoctorID();
        int result23 = (result22 * 59) + ($orderDoctorID == null ? 43 : $orderDoctorID.hashCode());
        Object $reviewer = getReviewer();
        int result24 = (result23 * 59) + ($reviewer == null ? 43 : $reviewer.hashCode());
        Object $reviewerID = getReviewerID();
        int result25 = (result24 * 59) + ($reviewerID == null ? 43 : $reviewerID.hashCode());
        Object $status = getStatus();
        int result26 = (result25 * 59) + ($status == null ? 43 : $status.hashCode());
        Object $orderTime = getOrderTime();
        int result27 = (result26 * 59) + ($orderTime == null ? 43 : $orderTime.hashCode());
        Object $reviewTime = getReviewTime();
        int result28 = (result27 * 59) + ($reviewTime == null ? 43 : $reviewTime.hashCode());
        Object $cancelTime = getCancelTime();
        int result29 = (result28 * 59) + ($cancelTime == null ? 43 : $cancelTime.hashCode());
        Object $stopTime = getStopTime();
        int result30 = (result29 * 59) + ($stopTime == null ? 43 : $stopTime.hashCode());
        Object $notes = getNotes();
        int result31 = (result30 * 59) + ($notes == null ? 43 : $notes.hashCode());
        Object $jslyw = getJslyw();
        int result32 = (result31 * 59) + ($jslyw == null ? 43 : $jslyw.hashCode());
        Object $recordPrt = getRecordPrt();
        int result33 = (result32 * 59) + ($recordPrt == null ? 43 : $recordPrt.hashCode());
        Object $paySelf = getPaySelf();
        int result34 = (result33 * 59) + ($paySelf == null ? 43 : $paySelf.hashCode());
        Object $everyTimedose = getEveryTimedose();
        return (result34 * 59) + ($everyTimedose == null ? 43 : $everyTimedose.hashCode());
    }

    public String toString() {
        return "Order(id=" + getId() + ", orderID=" + getOrderID() + ", pid=" + getPid() + ", mrn=" + getMrn() + ", zycs=" + getZycs() + ", jzkh=" + getJzkh() + ", name=" + getName() + ", gender=" + getGender() + ", orderType=" + getOrderType() + ", yaoType=" + getYaoType() + ", freq=" + getFreq() + ", dose=" + getDose() + ", unit=" + getUnit() + ", exeMethod=" + getExeMethod() + ", exeMethodCode=" + getExeMethodCode() + ", longOrderID=" + getLongOrderID() + ", orderName=" + getOrderName() + ", orderYaoCode=" + getOrderYaoCode() + ", groupID=" + getGroupID() + ", spec=" + getSpec() + ", planTime=" + getPlanTime() + ", orderDoctor=" + getOrderDoctor() + ", orderDoctorID=" + getOrderDoctorID() + ", reviewer=" + getReviewer() + ", reviewerID=" + getReviewerID() + ", status=" + getStatus() + ", orderTime=" + getOrderTime() + ", reviewTime=" + getReviewTime() + ", cancelTime=" + getCancelTime() + ", stopTime=" + getStopTime() + ", notes=" + getNotes() + ", jslyw=" + getJslyw() + ", recordPrt=" + getRecordPrt() + ", paySelf=" + getPaySelf() + ", everyTimedose=" + getEveryTimedose() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getOrderID() {
        return this.orderID;
    }

    public String getPid() {
        return this.pid;
    }

    public String getMrn() {
        return this.mrn;
    }

    public String getZycs() {
        return this.zycs;
    }

    public String getJzkh() {
        return this.jzkh;
    }

    public String getName() {
        return this.name;
    }

    public String getGender() {
        return this.gender;
    }

    public String getOrderType() {
        return this.orderType;
    }

    public String getYaoType() {
        return this.yaoType;
    }

    public String getFreq() {
        return this.freq;
    }

    public String getDose() {
        return this.dose;
    }

    public String getUnit() {
        return this.unit;
    }

    public String getExeMethod() {
        return this.exeMethod;
    }

    public String getExeMethodCode() {
        return this.exeMethodCode;
    }

    public String getLongOrderID() {
        return this.longOrderID;
    }

    public String getOrderName() {
        return this.orderName;
    }

    public String getOrderYaoCode() {
        return this.orderYaoCode;
    }

    public String getGroupID() {
        return this.groupID;
    }

    public String getSpec() {
        return this.spec;
    }

    public Date getPlanTime() {
        return this.planTime;
    }

    public String getOrderDoctor() {
        return this.orderDoctor;
    }

    public String getOrderDoctorID() {
        return this.orderDoctorID;
    }

    public String getReviewer() {
        return this.reviewer;
    }

    public String getReviewerID() {
        return this.reviewerID;
    }

    public String getStatus() {
        return this.status;
    }

    public Date getOrderTime() {
        return this.orderTime;
    }

    public Date getReviewTime() {
        return this.reviewTime;
    }

    public Date getCancelTime() {
        return this.cancelTime;
    }

    public Date getStopTime() {
        return this.stopTime;
    }

    public String getNotes() {
        return this.notes;
    }

    public String getJslyw() {
        return this.jslyw;
    }

    public String getRecordPrt() {
        return this.recordPrt;
    }

    public String getPaySelf() {
        return this.paySelf;
    }

    public String getEveryTimedose() {
        return this.everyTimedose;
    }

    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public Order m25clone() {
        Order doctorOrderInfo = new Order();
        doctorOrderInfo.cancelTime = this.cancelTime;
        doctorOrderInfo.orderID = this.orderID;
        doctorOrderInfo.dose = this.dose;
        doctorOrderInfo.exeMethod = this.exeMethod;
        doctorOrderInfo.exeMethodCode = this.exeMethodCode;
        doctorOrderInfo.freq = this.freq;
        doctorOrderInfo.gender = this.gender;
        doctorOrderInfo.groupID = this.groupID;
        doctorOrderInfo.jslyw = this.jslyw;
        doctorOrderInfo.longOrderID = this.longOrderID;
        doctorOrderInfo.mrn = this.mrn;
        doctorOrderInfo.notes = this.notes;
        doctorOrderInfo.orderDoctor = this.orderDoctor;
        doctorOrderInfo.orderName = this.orderName;
        doctorOrderInfo.orderDoctorID = this.orderDoctorID;
        doctorOrderInfo.orderTime = this.orderTime;
        doctorOrderInfo.orderType = this.orderType;
        doctorOrderInfo.zycs = this.zycs;
        doctorOrderInfo.paySelf = this.paySelf;
        doctorOrderInfo.recordPrt = this.recordPrt;
        doctorOrderInfo.reviewer = this.reviewer;
        doctorOrderInfo.reviewerID = this.reviewerID;
        doctorOrderInfo.status = this.status;
        doctorOrderInfo.stopTime = this.stopTime;
        doctorOrderInfo.unit = this.unit;
        doctorOrderInfo.reviewTime = this.reviewTime;
        doctorOrderInfo.jzkh = this.jzkh;
        doctorOrderInfo.spec = this.spec;
        doctorOrderInfo.orderYaoCode = this.orderYaoCode;
        doctorOrderInfo.yaoType = this.yaoType;
        doctorOrderInfo.name = this.name;
        doctorOrderInfo.pid = this.pid;
        doctorOrderInfo.planTime = this.planTime;
        return doctorOrderInfo;
    }
}