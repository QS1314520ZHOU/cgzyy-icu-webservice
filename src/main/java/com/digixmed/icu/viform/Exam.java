package com.digixmed.icu.viform;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("VI_ICU_EXAM")
public class Exam {

    @Id
    private String id;

    @Indexed
    private String hisPid;
    private String reportID;
    private String name;
    private String shortName;

    @Indexed
    private String code;
    private String orderID;
    private String orderDept;
    private String orderDeptCode;
    private String orderDoctor;
    private String orderDoctorID;
    private String specID;
    private String specName;
    private Date collectTime;
    private Date recvTime;
    private Date authTime;
    private String authDoctor;
    private String mrn;
    private String jzkh;
    private String patName;
    private String gender;
    private String status;
    private String pdfPath;
    private Boolean valid = true;

    public void setId(final String id) {
        this.id = id;
    }

    public void setHisPid(final String hisPid) {
        this.hisPid = hisPid;
    }

    public void setReportID(final String reportID) {
        this.reportID = reportID;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setShortName(final String shortName) {
        this.shortName = shortName;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setOrderID(final String orderID) {
        this.orderID = orderID;
    }

    public void setOrderDept(final String orderDept) {
        this.orderDept = orderDept;
    }

    public void setOrderDeptCode(final String orderDeptCode) {
        this.orderDeptCode = orderDeptCode;
    }

    public void setOrderDoctor(final String orderDoctor) {
        this.orderDoctor = orderDoctor;
    }

    public void setOrderDoctorID(final String orderDoctorID) {
        this.orderDoctorID = orderDoctorID;
    }

    public void setSpecID(final String specID) {
        this.specID = specID;
    }

    public void setSpecName(final String specName) {
        this.specName = specName;
    }

    public void setCollectTime(final Date collectTime) {
        this.collectTime = collectTime;
    }

    public void setRecvTime(final Date recvTime) {
        this.recvTime = recvTime;
    }

    public void setAuthTime(final Date authTime) {
        this.authTime = authTime;
    }

    public void setAuthDoctor(final String authDoctor) {
        this.authDoctor = authDoctor;
    }

    public void setMrn(final String mrn) {
        this.mrn = mrn;
    }

    public void setJzkh(final String jzkh) {
        this.jzkh = jzkh;
    }

    public void setPatName(final String patName) {
        this.patName = patName;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public void setPdfPath(final String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public void setValid(final Boolean valid) {
        this.valid = valid;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Exam)) {
            return false;
        }
        Exam other = (Exam) o;
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
        Object this$reportID = getReportID();
        Object other$reportID = other.getReportID();
        if (this$reportID == null) {
            if (other$reportID != null) {
                return false;
            }
        } else if (!this$reportID.equals(other$reportID)) {
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
        Object this$shortName = getShortName();
        Object other$shortName = other.getShortName();
        if (this$shortName == null) {
            if (other$shortName != null) {
                return false;
            }
        } else if (!this$shortName.equals(other$shortName)) {
            return false;
        }
        Object this$code = getCode();
        Object other$code = other.getCode();
        if (this$code == null) {
            if (other$code != null) {
                return false;
            }
        } else if (!this$code.equals(other$code)) {
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
        Object this$orderDept = getOrderDept();
        Object other$orderDept = other.getOrderDept();
        if (this$orderDept == null) {
            if (other$orderDept != null) {
                return false;
            }
        } else if (!this$orderDept.equals(other$orderDept)) {
            return false;
        }
        Object this$orderDeptCode = getOrderDeptCode();
        Object other$orderDeptCode = other.getOrderDeptCode();
        if (this$orderDeptCode == null) {
            if (other$orderDeptCode != null) {
                return false;
            }
        } else if (!this$orderDeptCode.equals(other$orderDeptCode)) {
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
        Object this$specID = getSpecID();
        Object other$specID = other.getSpecID();
        if (this$specID == null) {
            if (other$specID != null) {
                return false;
            }
        } else if (!this$specID.equals(other$specID)) {
            return false;
        }
        Object this$specName = getSpecName();
        Object other$specName = other.getSpecName();
        if (this$specName == null) {
            if (other$specName != null) {
                return false;
            }
        } else if (!this$specName.equals(other$specName)) {
            return false;
        }
        Object this$collectTime = getCollectTime();
        Object other$collectTime = other.getCollectTime();
        if (this$collectTime == null) {
            if (other$collectTime != null) {
                return false;
            }
        } else if (!this$collectTime.equals(other$collectTime)) {
            return false;
        }
        Object this$recvTime = getRecvTime();
        Object other$recvTime = other.getRecvTime();
        if (this$recvTime == null) {
            if (other$recvTime != null) {
                return false;
            }
        } else if (!this$recvTime.equals(other$recvTime)) {
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
        Object this$mrn = getMrn();
        Object other$mrn = other.getMrn();
        if (this$mrn == null) {
            if (other$mrn != null) {
                return false;
            }
        } else if (!this$mrn.equals(other$mrn)) {
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
        Object this$patName = getPatName();
        Object other$patName = other.getPatName();
        if (this$patName == null) {
            if (other$patName != null) {
                return false;
            }
        } else if (!this$patName.equals(other$patName)) {
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
        Object this$status = getStatus();
        Object other$status = other.getStatus();
        if (this$status == null) {
            if (other$status != null) {
                return false;
            }
        } else if (!this$status.equals(other$status)) {
            return false;
        }
        Object this$pdfPath = getPdfPath();
        Object other$pdfPath = other.getPdfPath();
        if (this$pdfPath == null) {
            if (other$pdfPath != null) {
                return false;
            }
        } else if (!this$pdfPath.equals(other$pdfPath)) {
            return false;
        }
        Object this$valid = getValid();
        Object other$valid = other.getValid();
        return this$valid == null ? other$valid == null : this$valid.equals(other$valid);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Exam;
    }

    public int hashCode() {
        Object $id = getId();
        int result = (1 * 59) + ($id == null ? 43 : $id.hashCode());
        Object $hisPid = getHisPid();
        int result2 = (result * 59) + ($hisPid == null ? 43 : $hisPid.hashCode());
        Object $reportID = getReportID();
        int result3 = (result2 * 59) + ($reportID == null ? 43 : $reportID.hashCode());
        Object $name = getName();
        int result4 = (result3 * 59) + ($name == null ? 43 : $name.hashCode());
        Object $shortName = getShortName();
        int result5 = (result4 * 59) + ($shortName == null ? 43 : $shortName.hashCode());
        Object $code = getCode();
        int result6 = (result5 * 59) + ($code == null ? 43 : $code.hashCode());
        Object $orderID = getOrderID();
        int result7 = (result6 * 59) + ($orderID == null ? 43 : $orderID.hashCode());
        Object $orderDept = getOrderDept();
        int result8 = (result7 * 59) + ($orderDept == null ? 43 : $orderDept.hashCode());
        Object $orderDeptCode = getOrderDeptCode();
        int result9 = (result8 * 59) + ($orderDeptCode == null ? 43 : $orderDeptCode.hashCode());
        Object $orderDoctor = getOrderDoctor();
        int result10 = (result9 * 59) + ($orderDoctor == null ? 43 : $orderDoctor.hashCode());
        Object $orderDoctorID = getOrderDoctorID();
        int result11 = (result10 * 59) + ($orderDoctorID == null ? 43 : $orderDoctorID.hashCode());
        Object $specID = getSpecID();
        int result12 = (result11 * 59) + ($specID == null ? 43 : $specID.hashCode());
        Object $specName = getSpecName();
        int result13 = (result12 * 59) + ($specName == null ? 43 : $specName.hashCode());
        Object $collectTime = getCollectTime();
        int result14 = (result13 * 59) + ($collectTime == null ? 43 : $collectTime.hashCode());
        Object $recvTime = getRecvTime();
        int result15 = (result14 * 59) + ($recvTime == null ? 43 : $recvTime.hashCode());
        Object $authTime = getAuthTime();
        int result16 = (result15 * 59) + ($authTime == null ? 43 : $authTime.hashCode());
        Object $authDoctor = getAuthDoctor();
        int result17 = (result16 * 59) + ($authDoctor == null ? 43 : $authDoctor.hashCode());
        Object $mrn = getMrn();
        int result18 = (result17 * 59) + ($mrn == null ? 43 : $mrn.hashCode());
        Object $jzkh = getJzkh();
        int result19 = (result18 * 59) + ($jzkh == null ? 43 : $jzkh.hashCode());
        Object $patName = getPatName();
        int result20 = (result19 * 59) + ($patName == null ? 43 : $patName.hashCode());
        Object $gender = getGender();
        int result21 = (result20 * 59) + ($gender == null ? 43 : $gender.hashCode());
        Object $status = getStatus();
        int result22 = (result21 * 59) + ($status == null ? 43 : $status.hashCode());
        Object $pdfPath = getPdfPath();
        int result23 = (result22 * 59) + ($pdfPath == null ? 43 : $pdfPath.hashCode());
        Object $valid = getValid();
        return (result23 * 59) + ($valid == null ? 43 : $valid.hashCode());
    }

    public String toString() {
        return "Exam(id=" + getId() + ", hisPid=" + getHisPid() + ", reportID=" + getReportID() + ", name=" + getName() + ", shortName=" + getShortName() + ", code=" + getCode() + ", orderID=" + getOrderID() + ", orderDept=" + getOrderDept() + ", orderDeptCode=" + getOrderDeptCode() + ", orderDoctor=" + getOrderDoctor() + ", orderDoctorID=" + getOrderDoctorID() + ", specID=" + getSpecID() + ", specName=" + getSpecName() + ", collectTime=" + getCollectTime() + ", recvTime=" + getRecvTime() + ", authTime=" + getAuthTime() + ", authDoctor=" + getAuthDoctor() + ", mrn=" + getMrn() + ", jzkh=" + getJzkh() + ", patName=" + getPatName() + ", gender=" + getGender() + ", status=" + getStatus() + ", pdfPath=" + getPdfPath() + ", valid=" + getValid() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getHisPid() {
        return this.hisPid;
    }

    public String getReportID() {
        return this.reportID;
    }

    public String getName() {
        return this.name;
    }

    public String getShortName() {
        return this.shortName;
    }

    public String getCode() {
        return this.code;
    }

    public String getOrderID() {
        return this.orderID;
    }

    public String getOrderDept() {
        return this.orderDept;
    }

    public String getOrderDeptCode() {
        return this.orderDeptCode;
    }

    public String getOrderDoctor() {
        return this.orderDoctor;
    }

    public String getOrderDoctorID() {
        return this.orderDoctorID;
    }

    public String getSpecID() {
        return this.specID;
    }

    public String getSpecName() {
        return this.specName;
    }

    public Date getCollectTime() {
        return this.collectTime;
    }

    public Date getRecvTime() {
        return this.recvTime;
    }

    public Date getAuthTime() {
        return this.authTime;
    }

    public String getAuthDoctor() {
        return this.authDoctor;
    }

    public String getMrn() {
        return this.mrn;
    }

    public String getJzkh() {
        return this.jzkh;
    }

    public String getPatName() {
        return this.patName;
    }

    public String getGender() {
        return this.gender;
    }

    public String getStatus() {
        return this.status;
    }

    public String getPdfPath() {
        return this.pdfPath;
    }

    public Boolean getValid() {
        return this.valid;
    }
}