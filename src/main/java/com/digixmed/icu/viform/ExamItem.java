package com.digixmed.icu.viform;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("VI_ICU_EXAM_ITEM")
public class ExamItem {

    @Id
    private String id;

    @Indexed
    private String hisPid;
    private String testItemID;
    private String examID;
    private String reportID;
    private String orderID;
    private String itemCode;
    private String itemName;
    private String itemShortName;
    private String longName;
    private String result;
    private String otherResult;
    private String printResult;
    private String almFlag;
    private String seriousFlag;
    private String unit;
    private String decimals;
    private String printRange;
    private String resultStatus;
    private String reflow;
    private String refHigh;
    private String seriousLow;
    private String seriousHigh;

    @Indexed(direction = IndexDirection.DESCENDING)
    private Date authTime;
    private String authDoctor;
    private String notes;

    public void setId(final String id) {
        this.id = id;
    }

    public void setHisPid(final String hisPid) {
        this.hisPid = hisPid;
    }

    public void setTestItemID(final String testItemID) {
        this.testItemID = testItemID;
    }

    public void setExamID(final String examID) {
        this.examID = examID;
    }

    public void setReportID(final String reportID) {
        this.reportID = reportID;
    }

    public void setOrderID(final String orderID) {
        this.orderID = orderID;
    }

    public void setItemCode(final String itemCode) {
        this.itemCode = itemCode;
    }

    public void setItemName(final String itemName) {
        this.itemName = itemName;
    }

    public void setItemShortName(final String itemShortName) {
        this.itemShortName = itemShortName;
    }

    public void setLongName(final String longName) {
        this.longName = longName;
    }

    public void setResult(final String result) {
        this.result = result;
    }

    public void setOtherResult(final String otherResult) {
        this.otherResult = otherResult;
    }

    public void setPrintResult(final String printResult) {
        this.printResult = printResult;
    }

    public void setAlmFlag(final String almFlag) {
        this.almFlag = almFlag;
    }

    public void setSeriousFlag(final String seriousFlag) {
        this.seriousFlag = seriousFlag;
    }

    public void setUnit(final String unit) {
        this.unit = unit;
    }

    public void setDecimals(final String decimals) {
        this.decimals = decimals;
    }

    public void setPrintRange(final String printRange) {
        this.printRange = printRange;
    }

    public void setResultStatus(final String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public void setReflow(final String reflow) {
        this.reflow = reflow;
    }

    public void setRefHigh(final String refHigh) {
        this.refHigh = refHigh;
    }

    public void setSeriousLow(final String seriousLow) {
        this.seriousLow = seriousLow;
    }

    public void setSeriousHigh(final String seriousHigh) {
        this.seriousHigh = seriousHigh;
    }

    public void setAuthTime(final Date authTime) {
        this.authTime = authTime;
    }

    public void setAuthDoctor(final String authDoctor) {
        this.authDoctor = authDoctor;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ExamItem)) {
            return false;
        }
        ExamItem other = (ExamItem) o;
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
        Object this$hisPid = getHisPid();
        Object other$hisPid = other.getHisPid();
        if (this$hisPid == null) {
            if (other$hisPid != null) {
                return false;
            }
        } else if (!this$hisPid.equals(other$hisPid)) {
            return false;
        }
        Object this$testItemID = getTestItemID();
        Object other$testItemID = other.getTestItemID();
        if (this$testItemID == null) {
            if (other$testItemID != null) {
                return false;
            }
        } else if (!this$testItemID.equals(other$testItemID)) {
            return false;
        }
        Object this$examID = getExamID();
        Object other$examID = other.getExamID();
        if (this$examID == null) {
            if (other$examID != null) {
                return false;
            }
        } else if (!this$examID.equals(other$examID)) {
            return false;
        }
        Object this$reportID = getReportID();
        Object other$reportID = other.getReportID();
        if (this$reportID == null) {
            if (other$reportID != null) {
                return false;
            }
        } else if (!this$reportID.equals(other$reportID)) {
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
        Object this$itemCode = getItemCode();
        Object other$itemCode = other.getItemCode();
        if (this$itemCode == null) {
            if (other$itemCode != null) {
                return false;
            }
        } else if (!this$itemCode.equals(other$itemCode)) {
            return false;
        }
        Object this$itemName = getItemName();
        Object other$itemName = other.getItemName();
        if (this$itemName == null) {
            if (other$itemName != null) {
                return false;
            }
        } else if (!this$itemName.equals(other$itemName)) {
            return false;
        }
        Object this$itemShortName = getItemShortName();
        Object other$itemShortName = other.getItemShortName();
        if (this$itemShortName == null) {
            if (other$itemShortName != null) {
                return false;
            }
        } else if (!this$itemShortName.equals(other$itemShortName)) {
            return false;
        }
        Object this$longName = getLongName();
        Object other$longName = other.getLongName();
        if (this$longName == null) {
            if (other$longName != null) {
                return false;
            }
        } else if (!this$longName.equals(other$longName)) {
            return false;
        }
        Object this$result = getResult();
        Object other$result = other.getResult();
        if (this$result == null) {
            if (other$result != null) {
                return false;
            }
        } else if (!this$result.equals(other$result)) {
            return false;
        }
        Object this$otherResult = getOtherResult();
        Object other$otherResult = other.getOtherResult();
        if (this$otherResult == null) {
            if (other$otherResult != null) {
                return false;
            }
        } else if (!this$otherResult.equals(other$otherResult)) {
            return false;
        }
        Object this$printResult = getPrintResult();
        Object other$printResult = other.getPrintResult();
        if (this$printResult == null) {
            if (other$printResult != null) {
                return false;
            }
        } else if (!this$printResult.equals(other$printResult)) {
            return false;
        }
        Object this$almFlag = getAlmFlag();
        Object other$almFlag = other.getAlmFlag();
        if (this$almFlag == null) {
            if (other$almFlag != null) {
                return false;
            }
        } else if (!this$almFlag.equals(other$almFlag)) {
            return false;
        }
        Object this$seriousFlag = getSeriousFlag();
        Object other$seriousFlag = other.getSeriousFlag();
        if (this$seriousFlag == null) {
            if (other$seriousFlag != null) {
                return false;
            }
        } else if (!this$seriousFlag.equals(other$seriousFlag)) {
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
        Object this$decimals = getDecimals();
        Object other$decimals = other.getDecimals();
        if (this$decimals == null) {
            if (other$decimals != null) {
                return false;
            }
        } else if (!this$decimals.equals(other$decimals)) {
            return false;
        }
        Object this$printRange = getPrintRange();
        Object other$printRange = other.getPrintRange();
        if (this$printRange == null) {
            if (other$printRange != null) {
                return false;
            }
        } else if (!this$printRange.equals(other$printRange)) {
            return false;
        }
        Object this$resultStatus = getResultStatus();
        Object other$resultStatus = other.getResultStatus();
        if (this$resultStatus == null) {
            if (other$resultStatus != null) {
                return false;
            }
        } else if (!this$resultStatus.equals(other$resultStatus)) {
            return false;
        }
        Object this$reflow = getReflow();
        Object other$reflow = other.getReflow();
        if (this$reflow == null) {
            if (other$reflow != null) {
                return false;
            }
        } else if (!this$reflow.equals(other$reflow)) {
            return false;
        }
        Object this$refHigh = getRefHigh();
        Object other$refHigh = other.getRefHigh();
        if (this$refHigh == null) {
            if (other$refHigh != null) {
                return false;
            }
        } else if (!this$refHigh.equals(other$refHigh)) {
            return false;
        }
        Object this$seriousLow = getSeriousLow();
        Object other$seriousLow = other.getSeriousLow();
        if (this$seriousLow == null) {
            if (other$seriousLow != null) {
                return false;
            }
        } else if (!this$seriousLow.equals(other$seriousLow)) {
            return false;
        }
        Object this$seriousHigh = getSeriousHigh();
        Object other$seriousHigh = other.getSeriousHigh();
        if (this$seriousHigh == null) {
            if (other$seriousHigh != null) {
                return false;
            }
        } else if (!this$seriousHigh.equals(other$seriousHigh)) {
            return false;
        }
        Object this$authTime = getAuthTime();
        Object other$authTime = other.getAuthTime();
        if (this$authTime == null) {
            if (other$authTime != null) {
                return false;
            }
        } else if (!this$authTime.equals(other$authTime)) {
            return false;
        }
        Object this$authDoctor = getAuthDoctor();
        Object other$authDoctor = other.getAuthDoctor();
        if (this$authDoctor == null) {
            if (other$authDoctor != null) {
                return false;
            }
        } else if (!this$authDoctor.equals(other$authDoctor)) {
            return false;
        }
        Object this$notes = getNotes();
        Object other$notes = other.getNotes();
        return this$notes == null ? other$notes == null : this$notes.equals(other$notes);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ExamItem;
    }

    public int hashCode() {
        Object $id = getId();
        int result = (1 * 59) + ($id == null ? 43 : $id.hashCode());
        Object $hisPid = getHisPid();
        int result2 = (result * 59) + ($hisPid == null ? 43 : $hisPid.hashCode());
        Object $testItemID = getTestItemID();
        int result3 = (result2 * 59) + ($testItemID == null ? 43 : $testItemID.hashCode());
        Object $examID = getExamID();
        int result4 = (result3 * 59) + ($examID == null ? 43 : $examID.hashCode());
        Object $reportID = getReportID();
        int result5 = (result4 * 59) + ($reportID == null ? 43 : $reportID.hashCode());
        Object $orderID = getOrderID();
        int result6 = (result5 * 59) + ($orderID == null ? 43 : $orderID.hashCode());
        Object $itemCode = getItemCode();
        int result7 = (result6 * 59) + ($itemCode == null ? 43 : $itemCode.hashCode());
        Object $itemName = getItemName();
        int result8 = (result7 * 59) + ($itemName == null ? 43 : $itemName.hashCode());
        Object $itemShortName = getItemShortName();
        int result9 = (result8 * 59) + ($itemShortName == null ? 43 : $itemShortName.hashCode());
        Object $longName = getLongName();
        int result10 = (result9 * 59) + ($longName == null ? 43 : $longName.hashCode());
        Object $result = getResult();
        int result11 = (result10 * 59) + ($result == null ? 43 : $result.hashCode());
        Object $otherResult = getOtherResult();
        int result12 = (result11 * 59) + ($otherResult == null ? 43 : $otherResult.hashCode());
        Object $printResult = getPrintResult();
        int result13 = (result12 * 59) + ($printResult == null ? 43 : $printResult.hashCode());
        Object $almFlag = getAlmFlag();
        int result14 = (result13 * 59) + ($almFlag == null ? 43 : $almFlag.hashCode());
        Object $seriousFlag = getSeriousFlag();
        int result15 = (result14 * 59) + ($seriousFlag == null ? 43 : $seriousFlag.hashCode());
        Object $unit = getUnit();
        int result16 = (result15 * 59) + ($unit == null ? 43 : $unit.hashCode());
        Object $decimals = getDecimals();
        int result17 = (result16 * 59) + ($decimals == null ? 43 : $decimals.hashCode());
        Object $printRange = getPrintRange();
        int result18 = (result17 * 59) + ($printRange == null ? 43 : $printRange.hashCode());
        Object $resultStatus = getResultStatus();
        int result19 = (result18 * 59) + ($resultStatus == null ? 43 : $resultStatus.hashCode());
        Object $reflow = getReflow();
        int result20 = (result19 * 59) + ($reflow == null ? 43 : $reflow.hashCode());
        Object $refHigh = getRefHigh();
        int result21 = (result20 * 59) + ($refHigh == null ? 43 : $refHigh.hashCode());
        Object $seriousLow = getSeriousLow();
        int result22 = (result21 * 59) + ($seriousLow == null ? 43 : $seriousLow.hashCode());
        Object $seriousHigh = getSeriousHigh();
        int result23 = (result22 * 59) + ($seriousHigh == null ? 43 : $seriousHigh.hashCode());
        Object $authTime = getAuthTime();
        int result24 = (result23 * 59) + ($authTime == null ? 43 : $authTime.hashCode());
        Object $authDoctor = getAuthDoctor();
        int result25 = (result24 * 59) + ($authDoctor == null ? 43 : $authDoctor.hashCode());
        Object $notes = getNotes();
        return (result25 * 59) + ($notes == null ? 43 : $notes.hashCode());
    }

    public String toString() {
        return "ExamItem(id=" + getId() + ", hisPid=" + getHisPid() + ", testItemID=" + getTestItemID() + ", examID=" + getExamID() + ", reportID=" + getReportID() + ", orderID=" + getOrderID() + ", itemCode=" + getItemCode() + ", itemName=" + getItemName() + ", itemShortName=" + getItemShortName() + ", longName=" + getLongName() + ", result=" + getResult() + ", otherResult=" + getOtherResult() + ", printResult=" + getPrintResult() + ", almFlag=" + getAlmFlag() + ", seriousFlag=" + getSeriousFlag() + ", unit=" + getUnit() + ", decimals=" + getDecimals() + ", printRange=" + getPrintRange() + ", resultStatus=" + getResultStatus() + ", reflow=" + getReflow() + ", refHigh=" + getRefHigh() + ", seriousLow=" + getSeriousLow() + ", seriousHigh=" + getSeriousHigh() + ", authTime=" + getAuthTime() + ", authDoctor=" + getAuthDoctor() + ", notes=" + getNotes() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getHisPid() {
        return this.hisPid;
    }

    public String getTestItemID() {
        return this.testItemID;
    }

    public String getExamID() {
        return this.examID;
    }

    public String getReportID() {
        return this.reportID;
    }

    public String getOrderID() {
        return this.orderID;
    }

    public String getItemCode() {
        return this.itemCode;
    }

    public String getItemName() {
        return this.itemName;
    }

    public String getItemShortName() {
        return this.itemShortName;
    }

    public String getLongName() {
        return this.longName;
    }

    public String getResult() {
        return this.result;
    }

    public String getOtherResult() {
        return this.otherResult;
    }

    public String getPrintResult() {
        return this.printResult;
    }

    public String getAlmFlag() {
        return this.almFlag;
    }

    public String getSeriousFlag() {
        return this.seriousFlag;
    }

    public String getUnit() {
        return this.unit;
    }

    public String getDecimals() {
        return this.decimals;
    }

    public String getPrintRange() {
        return this.printRange;
    }

    public String getResultStatus() {
        return this.resultStatus;
    }

    public String getReflow() {
        return this.reflow;
    }

    public String getRefHigh() {
        return this.refHigh;
    }

    public String getSeriousLow() {
        return this.seriousLow;
    }

    public String getSeriousHigh() {
        return this.seriousHigh;
    }

    public Date getAuthTime() {
        return this.authTime;
    }

    public String getAuthDoctor() {
        return this.authDoctor;
    }

    public String getNotes() {
        return this.notes;
    }
}