package com.digixmed.icu.viform;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("VI_ICU_KSXX")
public class DeptInfo {

    @Id
    private String id;

    @Indexed(direction = IndexDirection.DESCENDING)
    private String deptCode;
    private String deptName;
    private String businessrowid;

    public void setId(final String id) {
        this.id = id;
    }

    public void setDeptCode(final String deptCode) {
        this.deptCode = deptCode;
    }

    public void setDeptName(final String deptName) {
        this.deptName = deptName;
    }

    public void setBusinessrowid(final String businessrowid) {
        this.businessrowid = businessrowid;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DeptInfo)) {
            return false;
        }
        DeptInfo other = (DeptInfo) o;
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
        Object this$deptCode = getDeptCode();
        Object other$deptCode = other.getDeptCode();
        if (this$deptCode == null) {
            if (other$deptCode != null) {
                return false;
            }
        } else if (!this$deptCode.equals(other$deptCode)) {
            return false;
        }
        Object this$deptName = getDeptName();
        Object other$deptName = other.getDeptName();
        if (this$deptName == null) {
            if (other$deptName != null) {
                return false;
            }
        } else if (!this$deptName.equals(other$deptName)) {
            return false;
        }
        Object this$businessrowid = getBusinessrowid();
        Object other$businessrowid = other.getBusinessrowid();
        return this$businessrowid == null ? other$businessrowid == null : this$businessrowid.equals(other$businessrowid);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DeptInfo;
    }

    public int hashCode() {
        Object $id = getId();
        int result = (1 * 59) + ($id == null ? 43 : $id.hashCode());
        Object $deptCode = getDeptCode();
        int result2 = (result * 59) + ($deptCode == null ? 43 : $deptCode.hashCode());
        Object $deptName = getDeptName();
        int result3 = (result2 * 59) + ($deptName == null ? 43 : $deptName.hashCode());
        Object $businessrowid = getBusinessrowid();
        return (result3 * 59) + ($businessrowid == null ? 43 : $businessrowid.hashCode());
    }

    public String toString() {
        return "DeptInfo(id=" + getId() + ", deptCode=" + getDeptCode() + ", deptName=" + getDeptName() + ", businessrowid=" + getBusinessrowid() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getDeptCode() {
        return this.deptCode;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public String getBusinessrowid() {
        return this.businessrowid;
    }
}