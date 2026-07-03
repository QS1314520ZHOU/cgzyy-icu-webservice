package com.digixmed.icu.viform;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("VI_ICU_HSZ_YYZXJL")
public class OrderExecute {

    @Id
    private String id;

    @Indexed(direction = IndexDirection.DESCENDING)
    private String exeId;

    @Indexed(direction = IndexDirection.DESCENDING)
    private String orderID;
    private String barCode;
    private Date planTime;
    private Date exeTime;
    private Date checkTime;
    private Date finishTime;
    private String status;
    private String speed;
    private String note;
    private String SourceSpeed;

    public void setId(final String id) {
        this.id = id;
    }

    public void setExeId(final String exeId) {
        this.exeId = exeId;
    }

    public void setOrderID(final String orderID) {
        this.orderID = orderID;
    }

    public void setBarCode(final String barCode) {
        this.barCode = barCode;
    }

    public void setPlanTime(final Date planTime) {
        this.planTime = planTime;
    }

    public void setExeTime(final Date exeTime) {
        this.exeTime = exeTime;
    }

    public void setCheckTime(final Date checkTime) {
        this.checkTime = checkTime;
    }

    public void setFinishTime(final Date finishTime) {
        this.finishTime = finishTime;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public void setSpeed(final String speed) {
        this.speed = speed;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public void setSourceSpeed(final String SourceSpeed) {
        this.SourceSpeed = SourceSpeed;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OrderExecute)) {
            return false;
        }
        OrderExecute other = (OrderExecute) o;
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
        Object this$exeId = getExeId();
        Object other$exeId = other.getExeId();
        if (this$exeId == null) {
            if (other$exeId != null) {
                return false;
            }
        } else if (!this$exeId.equals(other$exeId)) {
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
        Object this$barCode = getBarCode();
        Object other$barCode = other.getBarCode();
        if (this$barCode == null) {
            if (other$barCode != null) {
                return false;
            }
        } else if (!this$barCode.equals(other$barCode)) {
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
        Object this$exeTime = getExeTime();
        Object other$exeTime = other.getExeTime();
        if (this$exeTime == null) {
            if (other$exeTime != null) {
                return false;
            }
        } else if (!this$exeTime.equals(other$exeTime)) {
            return false;
        }
        Object this$checkTime = getCheckTime();
        Object other$checkTime = other.getCheckTime();
        if (this$checkTime == null) {
            if (other$checkTime != null) {
                return false;
            }
        } else if (!this$checkTime.equals(other$checkTime)) {
            return false;
        }
        Object this$finishTime = getFinishTime();
        Object other$finishTime = other.getFinishTime();
        if (this$finishTime == null) {
            if (other$finishTime != null) {
                return false;
            }
        } else if (!this$finishTime.equals(other$finishTime)) {
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
        Object this$speed = getSpeed();
        Object other$speed = other.getSpeed();
        if (this$speed == null) {
            if (other$speed != null) {
                return false;
            }
        } else if (!this$speed.equals(other$speed)) {
            return false;
        }
        Object this$note = getNote();
        Object other$note = other.getNote();
        if (this$note == null) {
            if (other$note != null) {
                return false;
            }
        } else if (!this$note.equals(other$note)) {
            return false;
        }
        Object this$SourceSpeed = getSourceSpeed();
        Object other$SourceSpeed = other.getSourceSpeed();
        return this$SourceSpeed == null ? other$SourceSpeed == null : this$SourceSpeed.equals(other$SourceSpeed);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof OrderExecute;
    }

    public int hashCode() {
        Object $id = getId();
        int result = (1 * 59) + ($id == null ? 43 : $id.hashCode());
        Object $exeId = getExeId();
        int result2 = (result * 59) + ($exeId == null ? 43 : $exeId.hashCode());
        Object $orderID = getOrderID();
        int result3 = (result2 * 59) + ($orderID == null ? 43 : $orderID.hashCode());
        Object $barCode = getBarCode();
        int result4 = (result3 * 59) + ($barCode == null ? 43 : $barCode.hashCode());
        Object $planTime = getPlanTime();
        int result5 = (result4 * 59) + ($planTime == null ? 43 : $planTime.hashCode());
        Object $exeTime = getExeTime();
        int result6 = (result5 * 59) + ($exeTime == null ? 43 : $exeTime.hashCode());
        Object $checkTime = getCheckTime();
        int result7 = (result6 * 59) + ($checkTime == null ? 43 : $checkTime.hashCode());
        Object $finishTime = getFinishTime();
        int result8 = (result7 * 59) + ($finishTime == null ? 43 : $finishTime.hashCode());
        Object $status = getStatus();
        int result9 = (result8 * 59) + ($status == null ? 43 : $status.hashCode());
        Object $speed = getSpeed();
        int result10 = (result9 * 59) + ($speed == null ? 43 : $speed.hashCode());
        Object $note = getNote();
        int result11 = (result10 * 59) + ($note == null ? 43 : $note.hashCode());
        Object $SourceSpeed = getSourceSpeed();
        return (result11 * 59) + ($SourceSpeed == null ? 43 : $SourceSpeed.hashCode());
    }

    public String toString() {
        return "OrderExecute(id=" + getId() + ", exeId=" + getExeId() + ", orderID=" + getOrderID() + ", barCode=" + getBarCode() + ", planTime=" + getPlanTime() + ", exeTime=" + getExeTime() + ", checkTime=" + getCheckTime() + ", finishTime=" + getFinishTime() + ", status=" + getStatus() + ", speed=" + getSpeed() + ", note=" + getNote() + ", SourceSpeed=" + getSourceSpeed() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getExeId() {
        return this.exeId;
    }

    public String getOrderID() {
        return this.orderID;
    }

    public String getBarCode() {
        return this.barCode;
    }

    public Date getPlanTime() {
        return this.planTime;
    }

    public Date getExeTime() {
        return this.exeTime;
    }

    public Date getCheckTime() {
        return this.checkTime;
    }

    public Date getFinishTime() {
        return this.finishTime;
    }

    public String getStatus() {
        return this.status;
    }

    public String getSpeed() {
        return this.speed;
    }

    public String getNote() {
        return this.note;
    }

    public String getSourceSpeed() {
        return this.SourceSpeed;
    }
}