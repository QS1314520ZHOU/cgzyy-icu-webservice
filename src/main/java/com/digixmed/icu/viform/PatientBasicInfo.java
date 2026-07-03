package com.digixmed.icu.viform;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("VI_PAT_BASIC_INFO")
public class PatientBasicInfo {

    @Id
    private String id;
    private String hospitalCode;
    private String pid;
    private String name;
    private String gender;
    private Date dob;
    private String maritalStatusCode;
    private String maritalStatusDesc;
    private String documentNo;
    private String nation;
    private String nationCode;
    private String countryCode;
    private String countryDesc;
    private String address;
    private String telephone;
    private Date updateTime;

    public void setId(final String id) {
        this.id = id;
    }

    public void setHospitalCode(final String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public void setPid(final String pid) {
        this.pid = pid;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public void setDob(final Date dob) {
        this.dob = dob;
    }

    public void setMaritalStatusCode(final String maritalStatusCode) {
        this.maritalStatusCode = maritalStatusCode;
    }

    public void setMaritalStatusDesc(final String maritalStatusDesc) {
        this.maritalStatusDesc = maritalStatusDesc;
    }

    public void setDocumentNo(final String documentNo) {
        this.documentNo = documentNo;
    }

    public void setNation(final String nation) {
        this.nation = nation;
    }

    public void setNationCode(final String nationCode) {
        this.nationCode = nationCode;
    }

    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }

    public void setCountryDesc(final String countryDesc) {
        this.countryDesc = countryDesc;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setTelephone(final String telephone) {
        this.telephone = telephone;
    }

    public void setUpdateTime(final Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PatientBasicInfo)) {
            return false;
        }
        PatientBasicInfo other = (PatientBasicInfo) o;
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
        Object this$hospitalCode = getHospitalCode();
        Object other$hospitalCode = other.getHospitalCode();
        if (this$hospitalCode == null) {
            if (other$hospitalCode != null) {
                return false;
            }
        } else if (!this$hospitalCode.equals(other$hospitalCode)) {
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
        Object this$dob = getDob();
        Object other$dob = other.getDob();
        if (this$dob == null) {
            if (other$dob != null) {
                return false;
            }
        } else if (!this$dob.equals(other$dob)) {
            return false;
        }
        Object this$maritalStatusCode = getMaritalStatusCode();
        Object other$maritalStatusCode = other.getMaritalStatusCode();
        if (this$maritalStatusCode == null) {
            if (other$maritalStatusCode != null) {
                return false;
            }
        } else if (!this$maritalStatusCode.equals(other$maritalStatusCode)) {
            return false;
        }
        Object this$maritalStatusDesc = getMaritalStatusDesc();
        Object other$maritalStatusDesc = other.getMaritalStatusDesc();
        if (this$maritalStatusDesc == null) {
            if (other$maritalStatusDesc != null) {
                return false;
            }
        } else if (!this$maritalStatusDesc.equals(other$maritalStatusDesc)) {
            return false;
        }
        Object this$documentNo = getDocumentNo();
        Object other$documentNo = other.getDocumentNo();
        if (this$documentNo == null) {
            if (other$documentNo != null) {
                return false;
            }
        } else if (!this$documentNo.equals(other$documentNo)) {
            return false;
        }
        Object this$nation = getNation();
        Object other$nation = other.getNation();
        if (this$nation == null) {
            if (other$nation != null) {
                return false;
            }
        } else if (!this$nation.equals(other$nation)) {
            return false;
        }
        Object this$nationCode = getNationCode();
        Object other$nationCode = other.getNationCode();
        if (this$nationCode == null) {
            if (other$nationCode != null) {
                return false;
            }
        } else if (!this$nationCode.equals(other$nationCode)) {
            return false;
        }
        Object this$countryCode = getCountryCode();
        Object other$countryCode = other.getCountryCode();
        if (this$countryCode == null) {
            if (other$countryCode != null) {
                return false;
            }
        } else if (!this$countryCode.equals(other$countryCode)) {
            return false;
        }
        Object this$countryDesc = getCountryDesc();
        Object other$countryDesc = other.getCountryDesc();
        if (this$countryDesc == null) {
            if (other$countryDesc != null) {
                return false;
            }
        } else if (!this$countryDesc.equals(other$countryDesc)) {
            return false;
        }
        Object this$address = getAddress();
        Object other$address = other.getAddress();
        if (this$address == null) {
            if (other$address != null) {
                return false;
            }
        } else if (!this$address.equals(other$address)) {
            return false;
        }
        Object this$telephone = getTelephone();
        Object other$telephone = other.getTelephone();
        if (this$telephone == null) {
            if (other$telephone != null) {
                return false;
            }
        } else if (!this$telephone.equals(other$telephone)) {
            return false;
        }
        Object this$updateTime = getUpdateTime();
        Object other$updateTime = other.getUpdateTime();
        return this$updateTime == null ? other$updateTime == null : this$updateTime.equals(other$updateTime);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PatientBasicInfo;
    }

    public int hashCode() {
        Object $id = getId();
        int result = (1 * 59) + ($id == null ? 43 : $id.hashCode());
        Object $hospitalCode = getHospitalCode();
        int result2 = (result * 59) + ($hospitalCode == null ? 43 : $hospitalCode.hashCode());
        Object $pid = getPid();
        int result3 = (result2 * 59) + ($pid == null ? 43 : $pid.hashCode());
        Object $name = getName();
        int result4 = (result3 * 59) + ($name == null ? 43 : $name.hashCode());
        Object $gender = getGender();
        int result5 = (result4 * 59) + ($gender == null ? 43 : $gender.hashCode());
        Object $dob = getDob();
        int result6 = (result5 * 59) + ($dob == null ? 43 : $dob.hashCode());
        Object $maritalStatusCode = getMaritalStatusCode();
        int result7 = (result6 * 59) + ($maritalStatusCode == null ? 43 : $maritalStatusCode.hashCode());
        Object $maritalStatusDesc = getMaritalStatusDesc();
        int result8 = (result7 * 59) + ($maritalStatusDesc == null ? 43 : $maritalStatusDesc.hashCode());
        Object $documentNo = getDocumentNo();
        int result9 = (result8 * 59) + ($documentNo == null ? 43 : $documentNo.hashCode());
        Object $nation = getNation();
        int result10 = (result9 * 59) + ($nation == null ? 43 : $nation.hashCode());
        Object $nationCode = getNationCode();
        int result11 = (result10 * 59) + ($nationCode == null ? 43 : $nationCode.hashCode());
        Object $countryCode = getCountryCode();
        int result12 = (result11 * 59) + ($countryCode == null ? 43 : $countryCode.hashCode());
        Object $countryDesc = getCountryDesc();
        int result13 = (result12 * 59) + ($countryDesc == null ? 43 : $countryDesc.hashCode());
        Object $address = getAddress();
        int result14 = (result13 * 59) + ($address == null ? 43 : $address.hashCode());
        Object $telephone = getTelephone();
        int result15 = (result14 * 59) + ($telephone == null ? 43 : $telephone.hashCode());
        Object $updateTime = getUpdateTime();
        return (result15 * 59) + ($updateTime == null ? 43 : $updateTime.hashCode());
    }

    public String toString() {
        return "PatientBasicInfo(id=" + getId() + ", hospitalCode=" + getHospitalCode() + ", pid=" + getPid() + ", name=" + getName() + ", gender=" + getGender() + ", dob=" + getDob() + ", maritalStatusCode=" + getMaritalStatusCode() + ", maritalStatusDesc=" + getMaritalStatusDesc() + ", documentNo=" + getDocumentNo() + ", nation=" + getNation() + ", nationCode=" + getNationCode() + ", countryCode=" + getCountryCode() + ", countryDesc=" + getCountryDesc() + ", address=" + getAddress() + ", telephone=" + getTelephone() + ", updateTime=" + getUpdateTime() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getHospitalCode() {
        return this.hospitalCode;
    }

    public String getPid() {
        return this.pid;
    }

    public String getName() {
        return this.name;
    }

    public String getGender() {
        return this.gender;
    }

    public Date getDob() {
        return this.dob;
    }

    public String getMaritalStatusCode() {
        return this.maritalStatusCode;
    }

    public String getMaritalStatusDesc() {
        return this.maritalStatusDesc;
    }

    public String getDocumentNo() {
        return this.documentNo;
    }

    public String getNation() {
        return this.nation;
    }

    public String getNationCode() {
        return this.nationCode;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public String getCountryDesc() {
        return this.countryDesc;
    }

    public String getAddress() {
        return this.address;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }
}