package com.digixmed.icu.pojo.entity;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("uploadPDFRecord")
public class UploadPDFRecord {

    @Id
    private String id;
    private String pid;
    private String hisPid;
    private String mrn;
    private String hospitalTime;

    @Indexed
    private String deptCode;
    private String creatId;
    private String creatName;
    private Date creatTime;
    private String updateId;
    private String updateName;
    private Date updateTime;
    private String formCode;
    private Date pdfTime;
    private String pdfGridId;

    @Indexed
    private String title;
    private Boolean valid;
    private String revokeID;
    private String revokeCode;
    private String revokeName;
    private Date revokeTime;

    public void setId(final String id) {
        this.id = id;
    }

    public void setPid(final String pid) {
        this.pid = pid;
    }

    public void setHisPid(final String hisPid) {
        this.hisPid = hisPid;
    }

    public void setMrn(final String mrn) {
        this.mrn = mrn;
    }

    public void setHospitalTime(final String hospitalTime) {
        this.hospitalTime = hospitalTime;
    }

    public void setDeptCode(final String deptCode) {
        this.deptCode = deptCode;
    }

    public void setCreatId(final String creatId) {
        this.creatId = creatId;
    }

    public void setCreatName(final String creatName) {
        this.creatName = creatName;
    }

    public void setCreatTime(final Date creatTime) {
        this.creatTime = creatTime;
    }

    public void setUpdateId(final String updateId) {
        this.updateId = updateId;
    }

    public void setUpdateName(final String updateName) {
        this.updateName = updateName;
    }

    public void setUpdateTime(final Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setFormCode(final String formCode) {
        this.formCode = formCode;
    }

    public void setPdfTime(final Date pdfTime) {
        this.pdfTime = pdfTime;
    }

    public void setPdfGridId(final String pdfGridId) {
        this.pdfGridId = pdfGridId;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setValid(final Boolean valid) {
        this.valid = valid;
    }

    public void setRevokeID(final String revokeID) {
        this.revokeID = revokeID;
    }

    public void setRevokeCode(final String revokeCode) {
        this.revokeCode = revokeCode;
    }

    public void setRevokeName(final String revokeName) {
        this.revokeName = revokeName;
    }

    public void setRevokeTime(final Date revokeTime) {
        this.revokeTime = revokeTime;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UploadPDFRecord)) {
            return false;
        }
        UploadPDFRecord other = (UploadPDFRecord) o;
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
        Object this$pid = getPid();
        Object other$pid = other.getPid();
        if (this$pid == null) {
            if (other$pid != null) {
                return false;
            }
        } else if (!this$pid.equals(other$pid)) {
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
        Object this$mrn = getMrn();
        Object other$mrn = other.getMrn();
        if (this$mrn == null) {
            if (other$mrn != null) {
                return false;
            }
        } else if (!this$mrn.equals(other$mrn)) {
            return false;
        }
        Object this$hospitalTime = getHospitalTime();
        Object other$hospitalTime = other.getHospitalTime();
        if (this$hospitalTime == null) {
            if (other$hospitalTime != null) {
                return false;
            }
        } else if (!this$hospitalTime.equals(other$hospitalTime)) {
            return false;
        }
        Object this$deptCode = getDeptCode();
        Object other$deptCode = other.getDeptCode();
        if (this$deptCode == null) {
            if (other$deptCode != null) {
                return false;
            }
        } else if (!this$deptCode.equals(other$deptCode)) {
            return false;
        }
        Object this$creatId = getCreatId();
        Object other$creatId = other.getCreatId();
        if (this$creatId == null) {
            if (other$creatId != null) {
                return false;
            }
        } else if (!this$creatId.equals(other$creatId)) {
            return false;
        }
        Object this$creatName = getCreatName();
        Object other$creatName = other.getCreatName();
        if (this$creatName == null) {
            if (other$creatName != null) {
                return false;
            }
        } else if (!this$creatName.equals(other$creatName)) {
            return false;
        }
        Object this$creatTime = getCreatTime();
        Object other$creatTime = other.getCreatTime();
        if (this$creatTime == null) {
            if (other$creatTime != null) {
                return false;
            }
        } else if (!this$creatTime.equals(other$creatTime)) {
            return false;
        }
        Object this$updateId = getUpdateId();
        Object other$updateId = other.getUpdateId();
        if (this$updateId == null) {
            if (other$updateId != null) {
                return false;
            }
        } else if (!this$updateId.equals(other$updateId)) {
            return false;
        }
        Object this$updateName = getUpdateName();
        Object other$updateName = other.getUpdateName();
        if (this$updateName == null) {
            if (other$updateName != null) {
                return false;
            }
        } else if (!this$updateName.equals(other$updateName)) {
            return false;
        }
        Object this$updateTime = getUpdateTime();
        Object other$updateTime = other.getUpdateTime();
        if (this$updateTime == null) {
            if (other$updateTime != null) {
                return false;
            }
        } else if (!this$updateTime.equals(other$updateTime)) {
            return false;
        }
        Object this$formCode = getFormCode();
        Object other$formCode = other.getFormCode();
        if (this$formCode == null) {
            if (other$formCode != null) {
                return false;
            }
        } else if (!this$formCode.equals(other$formCode)) {
            return false;
        }
        Object this$pdfTime = getPdfTime();
        Object other$pdfTime = other.getPdfTime();
        if (this$pdfTime == null) {
            if (other$pdfTime != null) {
                return false;
            }
        } else if (!this$pdfTime.equals(other$pdfTime)) {
            return false;
        }
        Object this$pdfGridId = getPdfGridId();
        Object other$pdfGridId = other.getPdfGridId();
        if (this$pdfGridId == null) {
            if (other$pdfGridId != null) {
                return false;
            }
        } else if (!this$pdfGridId.equals(other$pdfGridId)) {
            return false;
        }
        Object this$title = getTitle();
        Object other$title = other.getTitle();
        if (this$title == null) {
            if (other$title != null) {
                return false;
            }
        } else if (!this$title.equals(other$title)) {
            return false;
        }
        Object this$valid = getValid();
        Object other$valid = other.getValid();
        if (this$valid == null) {
            if (other$valid != null) {
                return false;
            }
        } else if (!this$valid.equals(other$valid)) {
            return false;
        }
        Object this$revokeID = getRevokeID();
        Object other$revokeID = other.getRevokeID();
        if (this$revokeID == null) {
            if (other$revokeID != null) {
                return false;
            }
        } else if (!this$revokeID.equals(other$revokeID)) {
            return false;
        }
        Object this$revokeCode = getRevokeCode();
        Object other$revokeCode = other.getRevokeCode();
        if (this$revokeCode == null) {
            if (other$revokeCode != null) {
                return false;
            }
        } else if (!this$revokeCode.equals(other$revokeCode)) {
            return false;
        }
        Object this$revokeName = getRevokeName();
        Object other$revokeName = other.getRevokeName();
        if (this$revokeName == null) {
            if (other$revokeName != null) {
                return false;
            }
        } else if (!this$revokeName.equals(other$revokeName)) {
            return false;
        }
        Object this$revokeTime = getRevokeTime();
        Object other$revokeTime = other.getRevokeTime();
        return this$revokeTime == null ? other$revokeTime == null : this$revokeTime.equals(other$revokeTime);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UploadPDFRecord;
    }

    public int hashCode() {
        Object $id = getId();
        int result = (1 * 59) + ($id == null ? 43 : $id.hashCode());
        Object $pid = getPid();
        int result2 = (result * 59) + ($pid == null ? 43 : $pid.hashCode());
        Object $hisPid = getHisPid();
        int result3 = (result2 * 59) + ($hisPid == null ? 43 : $hisPid.hashCode());
        Object $mrn = getMrn();
        int result4 = (result3 * 59) + ($mrn == null ? 43 : $mrn.hashCode());
        Object $hospitalTime = getHospitalTime();
        int result5 = (result4 * 59) + ($hospitalTime == null ? 43 : $hospitalTime.hashCode());
        Object $deptCode = getDeptCode();
        int result6 = (result5 * 59) + ($deptCode == null ? 43 : $deptCode.hashCode());
        Object $creatId = getCreatId();
        int result7 = (result6 * 59) + ($creatId == null ? 43 : $creatId.hashCode());
        Object $creatName = getCreatName();
        int result8 = (result7 * 59) + ($creatName == null ? 43 : $creatName.hashCode());
        Object $creatTime = getCreatTime();
        int result9 = (result8 * 59) + ($creatTime == null ? 43 : $creatTime.hashCode());
        Object $updateId = getUpdateId();
        int result10 = (result9 * 59) + ($updateId == null ? 43 : $updateId.hashCode());
        Object $updateName = getUpdateName();
        int result11 = (result10 * 59) + ($updateName == null ? 43 : $updateName.hashCode());
        Object $updateTime = getUpdateTime();
        int result12 = (result11 * 59) + ($updateTime == null ? 43 : $updateTime.hashCode());
        Object $formCode = getFormCode();
        int result13 = (result12 * 59) + ($formCode == null ? 43 : $formCode.hashCode());
        Object $pdfTime = getPdfTime();
        int result14 = (result13 * 59) + ($pdfTime == null ? 43 : $pdfTime.hashCode());
        Object $pdfGridId = getPdfGridId();
        int result15 = (result14 * 59) + ($pdfGridId == null ? 43 : $pdfGridId.hashCode());
        Object $title = getTitle();
        int result16 = (result15 * 59) + ($title == null ? 43 : $title.hashCode());
        Object $valid = getValid();
        int result17 = (result16 * 59) + ($valid == null ? 43 : $valid.hashCode());
        Object $revokeID = getRevokeID();
        int result18 = (result17 * 59) + ($revokeID == null ? 43 : $revokeID.hashCode());
        Object $revokeCode = getRevokeCode();
        int result19 = (result18 * 59) + ($revokeCode == null ? 43 : $revokeCode.hashCode());
        Object $revokeName = getRevokeName();
        int result20 = (result19 * 59) + ($revokeName == null ? 43 : $revokeName.hashCode());
        Object $revokeTime = getRevokeTime();
        return (result20 * 59) + ($revokeTime == null ? 43 : $revokeTime.hashCode());
    }

    public String toString() {
        return "UploadPDFRecord(id=" + getId() + ", pid=" + getPid() + ", hisPid=" + getHisPid() + ", mrn=" + getMrn() + ", hospitalTime=" + getHospitalTime() + ", deptCode=" + getDeptCode() + ", creatId=" + getCreatId() + ", creatName=" + getCreatName() + ", creatTime=" + getCreatTime() + ", updateId=" + getUpdateId() + ", updateName=" + getUpdateName() + ", updateTime=" + getUpdateTime() + ", formCode=" + getFormCode() + ", pdfTime=" + getPdfTime() + ", pdfGridId=" + getPdfGridId() + ", title=" + getTitle() + ", valid=" + getValid() + ", revokeID=" + getRevokeID() + ", revokeCode=" + getRevokeCode() + ", revokeName=" + getRevokeName() + ", revokeTime=" + getRevokeTime() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getPid() {
        return this.pid;
    }

    public String getHisPid() {
        return this.hisPid;
    }

    public String getMrn() {
        return this.mrn;
    }

    public String getHospitalTime() {
        return this.hospitalTime;
    }

    public String getDeptCode() {
        return this.deptCode;
    }

    public String getCreatId() {
        return this.creatId;
    }

    public String getCreatName() {
        return this.creatName;
    }

    public Date getCreatTime() {
        return this.creatTime;
    }

    public String getUpdateId() {
        return this.updateId;
    }

    public String getUpdateName() {
        return this.updateName;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public Date getPdfTime() {
        return this.pdfTime;
    }

    public String getPdfGridId() {
        return this.pdfGridId;
    }

    public String getTitle() {
        return this.title;
    }

    public Boolean getValid() {
        return this.valid;
    }

    public String getRevokeID() {
        return this.revokeID;
    }

    public String getRevokeCode() {
        return this.revokeCode;
    }

    public String getRevokeName() {
        return this.revokeName;
    }

    public Date getRevokeTime() {
        return this.revokeTime;
    }
}