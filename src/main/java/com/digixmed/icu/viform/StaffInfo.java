package com.digixmed.icu.viform;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("VI_ICU_ZGXX")
public class StaffInfo {

    @Id
    private String id;

    @Indexed(direction = IndexDirection.DESCENDING)
    private String code;
    private String deptCode;
    private String name;
    private String sex;
    private String dutyType;
    private String dutyTypeCode;
    private Integer flag = 0;
    private String businessrowId;

    public void setId(final String id) {
        this.id = id;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setDeptCode(final String deptCode) {
        this.deptCode = deptCode;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSex(final String sex) {
        this.sex = sex;
    }

    public void setDutyType(final String dutyType) {
        this.dutyType = dutyType;
    }

    public void setDutyTypeCode(final String dutyTypeCode) {
        this.dutyTypeCode = dutyTypeCode;
    }

    public void setFlag(final Integer flag) {
        this.flag = flag;
    }

    public void setBusinessrowId(final String businessrowId) {
        this.businessrowId = businessrowId;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StaffInfo)) {
            return false;
        }
        StaffInfo other = (StaffInfo) o;
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
        Object this$code = getCode();
        Object other$code = other.getCode();
        if (this$code == null) {
            if (other$code != null) {
                return false;
            }
        } else if (!this$code.equals(other$code)) {
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
        Object this$name = getName();
        Object other$name = other.getName();
        if (this$name == null) {
            if (other$name != null) {
                return false;
            }
        } else if (!this$name.equals(other$name)) {
            return false;
        }
        Object this$sex = getSex();
        Object other$sex = other.getSex();
        if (this$sex == null) {
            if (other$sex != null) {
                return false;
            }
        } else if (!this$sex.equals(other$sex)) {
            return false;
        }
        Object this$dutyType = getDutyType();
        Object other$dutyType = other.getDutyType();
        if (this$dutyType == null) {
            if (other$dutyType != null) {
                return false;
            }
        } else if (!this$dutyType.equals(other$dutyType)) {
            return false;
        }
        Object this$dutyTypeCode = getDutyTypeCode();
        Object other$dutyTypeCode = other.getDutyTypeCode();
        if (this$dutyTypeCode == null) {
            if (other$dutyTypeCode != null) {
                return false;
            }
        } else if (!this$dutyTypeCode.equals(other$dutyTypeCode)) {
            return false;
        }
        Object this$flag = getFlag();
        Object other$flag = other.getFlag();
        if (this$flag == null) {
            if (other$flag != null) {
                return false;
            }
        } else if (!this$flag.equals(other$flag)) {
            return false;
        }
        Object this$businessrowId = getBusinessrowId();
        Object other$businessrowId = other.getBusinessrowId();
        return this$businessrowId == null ? other$businessrowId == null : this$businessrowId.equals(other$businessrowId);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof StaffInfo;
    }

    public int hashCode() {
        Object $id = getId();
        int result = (1 * 59) + ($id == null ? 43 : $id.hashCode());
        Object $code = getCode();
        int result2 = (result * 59) + ($code == null ? 43 : $code.hashCode());
        Object $deptCode = getDeptCode();
        int result3 = (result2 * 59) + ($deptCode == null ? 43 : $deptCode.hashCode());
        Object $name = getName();
        int result4 = (result3 * 59) + ($name == null ? 43 : $name.hashCode());
        Object $sex = getSex();
        int result5 = (result4 * 59) + ($sex == null ? 43 : $sex.hashCode());
        Object $dutyType = getDutyType();
        int result6 = (result5 * 59) + ($dutyType == null ? 43 : $dutyType.hashCode());
        Object $dutyTypeCode = getDutyTypeCode();
        int result7 = (result6 * 59) + ($dutyTypeCode == null ? 43 : $dutyTypeCode.hashCode());
        Object $flag = getFlag();
        int result8 = (result7 * 59) + ($flag == null ? 43 : $flag.hashCode());
        Object $businessrowId = getBusinessrowId();
        return (result8 * 59) + ($businessrowId == null ? 43 : $businessrowId.hashCode());
    }

    public String toString() {
        return "StaffInfo(id=" + getId() + ", code=" + getCode() + ", deptCode=" + getDeptCode() + ", name=" + getName() + ", sex=" + getSex() + ", dutyType=" + getDutyType() + ", dutyTypeCode=" + getDutyTypeCode() + ", flag=" + getFlag() + ", businessrowId=" + getBusinessrowId() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getDeptCode() {
        return this.deptCode;
    }

    public String getName() {
        return this.name;
    }

    public String getSex() {
        return this.sex;
    }

    public String getDutyType() {
        return this.dutyType;
    }

    public String getDutyTypeCode() {
        return this.dutyTypeCode;
    }

    public Integer getFlag() {
        return this.flag;
    }

    public String getBusinessrowId() {
        return this.businessrowId;
    }
}