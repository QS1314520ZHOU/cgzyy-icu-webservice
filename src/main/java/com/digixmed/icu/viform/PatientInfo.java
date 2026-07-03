package com.digixmed.icu.viform;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("VI_PAT_INFO")
public class PatientInfo {

    @Id
    private String id;

    @Indexed(direction = IndexDirection.DESCENDING)
    private String pid;

    @Indexed(direction = IndexDirection.DESCENDING)
    private String mrn;
    private String zycs;
    private String yblx;
    private String jzkh;
    private String name;
    private String gender;
    private Date dob;
    private String age;

    @Indexed(direction = IndexDirection.DESCENDING)
    private Date admitTime;
    private Date inPatientTime;
    private String dept;

    @Indexed(direction = IndexDirection.DESCENDING)
    private String deptCode;
    private String bed;

    @Indexed(direction = IndexDirection.DESCENDING)
    private String zyzt;

    @Indexed(direction = IndexDirection.DESCENDING)
    private Date dischargeTime;
    private String idcardno;
    private String address;
    private String height;
    private String weight;
    private String blood;
    private String tel;
    private String operation;
    private Date operTime;
    private String diagnose;
    private String vip;
    private String illnessLevel;
    private String allergic;
    private String zhushu;
    private String doctor;
    private String doctorID;
    private String shouYeXuHao;
    private String hisVisitId;
    private Date deathTime;
    private String dischargedType;
    private String dischargedDepartment;
    private String dischargedDepartmentCode;
    public String getDischargedDepartment() {
        return dischargedDepartment;
    }

    public void setDischargedDepartment(String dischargedDepartment) {
        this.dischargedDepartment = dischargedDepartment;
    }

    public String getDischargedDepartmentCode() {
        return dischargedDepartmentCode;
    }

    public void setDischargedDepartmentCode(String dischargedDepartmentCode) {
        this.dischargedDepartmentCode = dischargedDepartmentCode;
    }

    public String getDischargedType() {
        return dischargedType;
    }

    public void setDischargedType(String dischargedType) {
        this.dischargedType = dischargedType;
    }

    public Date getDeathTime() {
        return deathTime;
    }

    public void setDeathTime(Date deathTime) {
        this.deathTime = deathTime;
    }

    public String getAdmissionType() {
        return admissionType;
    }

    public void setAdmissionType(String admissionType) {
        this.admissionType = admissionType;
    }

    private String admissionType;

    public String getAdmissionSource() {
        return admissionSource;
    }

    public void setAdmissionSource(String admissionSource) {
        this.admissionSource = admissionSource;
    }

    private String admissionSource;

    public void setId(final String id) {
        this.id = id;
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

    public void setYblx(final String yblx) {
        this.yblx = yblx;
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

    public void setDob(final Date dob) {
        this.dob = dob;
    }

    public void setAge(final String age) {
        this.age = age;
    }

    public void setAdmitTime(final Date admitTime) {
        this.admitTime = admitTime;
    }

    public void setInPatientTime(final Date inPatientTime) {
        this.inPatientTime = inPatientTime;
    }

    public void setDept(final String dept) {
        this.dept = dept;
    }

    public void setDeptCode(final String deptCode) {
        this.deptCode = deptCode;
    }

    public void setBed(final String bed) {
        this.bed = bed;
    }

    public void setZyzt(final String zyzt) {
        this.zyzt = zyzt;
    }

    public void setDischargeTime(final Date dischargeTime) {
        this.dischargeTime = dischargeTime;
    }

    public void setIdcardno(final String idcardno) {
        this.idcardno = idcardno;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setHeight(final String height) {
        this.height = height;
    }

    public void setWeight(final String weight) {
        this.weight = weight;
    }

    public void setBlood(final String blood) {
        this.blood = blood;
    }

    public void setTel(final String tel) {
        this.tel = tel;
    }

    public void setOperation(final String operation) {
        this.operation = operation;
    }

    public void setOperTime(final Date operTime) {
        this.operTime = operTime;
    }

    public void setDiagnose(final String diagnose) {
        this.diagnose = diagnose;
    }

    public void setVip(final String vip) {
        this.vip = vip;
    }

    public void setIllnessLevel(final String illnessLevel) {
        this.illnessLevel = illnessLevel;
    }

    public void setAllergic(final String allergic) {
        this.allergic = allergic;
    }

    public void setZhushu(final String zhushu) {
        this.zhushu = zhushu;
    }

    public void setDoctor(final String doctor) {
        this.doctor = doctor;
    }

    public void setDoctorID(final String doctorID) {
        this.doctorID = doctorID;
    }

    public void setShouYeXuHao(final String shouYeXuHao) {
        this.shouYeXuHao = shouYeXuHao;
    }

    public void setHisVisitId(final String hisVisitId) {
        this.hisVisitId = hisVisitId;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PatientInfo)) {
            return false;
        }
        PatientInfo other = (PatientInfo) o;
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
        Object this$yblx = getYblx();
        Object other$yblx = other.getYblx();
        if (this$yblx == null) {
            if (other$yblx != null) {
                return false;
            }
        } else if (!this$yblx.equals(other$yblx)) {
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
        Object this$dob = getDob();
        Object other$dob = other.getDob();
        if (this$dob == null) {
            if (other$dob != null) {
                return false;
            }
        } else if (!this$dob.equals(other$dob)) {
            return false;
        }
        Object this$age = getAge();
        Object other$age = other.getAge();
        if (this$age == null) {
            if (other$age != null) {
                return false;
            }
        } else if (!this$age.equals(other$age)) {
            return false;
        }
        Object this$admitTime = getAdmitTime();
        Object other$admitTime = other.getAdmitTime();
        if (this$admitTime == null) {
            if (other$admitTime != null) {
                return false;
            }
        } else if (!this$admitTime.equals(other$admitTime)) {
            return false;
        }
        Object this$inPatientTime = getInPatientTime();
        Object other$inPatientTime = other.getInPatientTime();
        if (this$inPatientTime == null) {
            if (other$inPatientTime != null) {
                return false;
            }
        } else if (!this$inPatientTime.equals(other$inPatientTime)) {
            return false;
        }
        Object this$dept = getDept();
        Object other$dept = other.getDept();
        if (this$dept == null) {
            if (other$dept != null) {
                return false;
            }
        } else if (!this$dept.equals(other$dept)) {
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
        Object this$bed = getBed();
        Object other$bed = other.getBed();
        if (this$bed == null) {
            if (other$bed != null) {
                return false;
            }
        } else if (!this$bed.equals(other$bed)) {
            return false;
        }
        Object this$zyzt = getZyzt();
        Object other$zyzt = other.getZyzt();
        if (this$zyzt == null) {
            if (other$zyzt != null) {
                return false;
            }
        } else if (!this$zyzt.equals(other$zyzt)) {
            return false;
        }
        Object this$dischargeTime = getDischargeTime();
        Object other$dischargeTime = other.getDischargeTime();
        if (this$dischargeTime == null) {
            if (other$dischargeTime != null) {
                return false;
            }
        } else if (!this$dischargeTime.equals(other$dischargeTime)) {
            return false;
        }
        Object this$idcardno = getIdcardno();
        Object other$idcardno = other.getIdcardno();
        if (this$idcardno == null) {
            if (other$idcardno != null) {
                return false;
            }
        } else if (!this$idcardno.equals(other$idcardno)) {
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
        Object this$height = getHeight();
        Object other$height = other.getHeight();
        if (this$height == null) {
            if (other$height != null) {
                return false;
            }
        } else if (!this$height.equals(other$height)) {
            return false;
        }
        Object this$weight = getWeight();
        Object other$weight = other.getWeight();
        if (this$weight == null) {
            if (other$weight != null) {
                return false;
            }
        } else if (!this$weight.equals(other$weight)) {
            return false;
        }
        Object this$blood = getBlood();
        Object other$blood = other.getBlood();
        if (this$blood == null) {
            if (other$blood != null) {
                return false;
            }
        } else if (!this$blood.equals(other$blood)) {
            return false;
        }
        Object this$tel = getTel();
        Object other$tel = other.getTel();
        if (this$tel == null) {
            if (other$tel != null) {
                return false;
            }
        } else if (!this$tel.equals(other$tel)) {
            return false;
        }
        Object this$operation = getOperation();
        Object other$operation = other.getOperation();
        if (this$operation == null) {
            if (other$operation != null) {
                return false;
            }
        } else if (!this$operation.equals(other$operation)) {
            return false;
        }
        Object this$operTime = getOperTime();
        Object other$operTime = other.getOperTime();
        if (this$operTime == null) {
            if (other$operTime != null) {
                return false;
            }
        } else if (!this$operTime.equals(other$operTime)) {
            return false;
        }
        Object this$diagnose = getDiagnose();
        Object other$diagnose = other.getDiagnose();
        if (this$diagnose == null) {
            if (other$diagnose != null) {
                return false;
            }
        } else if (!this$diagnose.equals(other$diagnose)) {
            return false;
        }
        Object this$vip = getVip();
        Object other$vip = other.getVip();
        if (this$vip == null) {
            if (other$vip != null) {
                return false;
            }
        } else if (!this$vip.equals(other$vip)) {
            return false;
        }
        Object this$illnessLevel = getIllnessLevel();
        Object other$illnessLevel = other.getIllnessLevel();
        if (this$illnessLevel == null) {
            if (other$illnessLevel != null) {
                return false;
            }
        } else if (!this$illnessLevel.equals(other$illnessLevel)) {
            return false;
        }
        Object this$allergic = getAllergic();
        Object other$allergic = other.getAllergic();
        if (this$allergic == null) {
            if (other$allergic != null) {
                return false;
            }
        } else if (!this$allergic.equals(other$allergic)) {
            return false;
        }
        Object this$zhushu = getZhushu();
        Object other$zhushu = other.getZhushu();
        if (this$zhushu == null) {
            if (other$zhushu != null) {
                return false;
            }
        } else if (!this$zhushu.equals(other$zhushu)) {
            return false;
        }
        Object this$doctor = getDoctor();
        Object other$doctor = other.getDoctor();
        if (this$doctor == null) {
            if (other$doctor != null) {
                return false;
            }
        } else if (!this$doctor.equals(other$doctor)) {
            return false;
        }
        Object this$doctorID = getDoctorID();
        Object other$doctorID = other.getDoctorID();
        if (this$doctorID == null) {
            if (other$doctorID != null) {
                return false;
            }
        } else if (!this$doctorID.equals(other$doctorID)) {
            return false;
        }
        Object this$shouYeXuHao = getShouYeXuHao();
        Object other$shouYeXuHao = other.getShouYeXuHao();
        if (this$shouYeXuHao == null) {
            if (other$shouYeXuHao != null) {
                return false;
            }
        } else if (!this$shouYeXuHao.equals(other$shouYeXuHao)) {
            return false;
        }
        Object this$hisVisitId = getHisVisitId();
        Object other$hisVisitId = other.getHisVisitId();
        return this$hisVisitId == null ? other$hisVisitId == null : this$hisVisitId.equals(other$hisVisitId);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PatientInfo;
    }

    public int hashCode() {
        Object $id = getId();
        int result = (1 * 59) + ($id == null ? 43 : $id.hashCode());
        Object $pid = getPid();
        int result2 = (result * 59) + ($pid == null ? 43 : $pid.hashCode());
        Object $mrn = getMrn();
        int result3 = (result2 * 59) + ($mrn == null ? 43 : $mrn.hashCode());
        Object $zycs = getZycs();
        int result4 = (result3 * 59) + ($zycs == null ? 43 : $zycs.hashCode());
        Object $yblx = getYblx();
        int result5 = (result4 * 59) + ($yblx == null ? 43 : $yblx.hashCode());
        Object $jzkh = getJzkh();
        int result6 = (result5 * 59) + ($jzkh == null ? 43 : $jzkh.hashCode());
        Object $name = getName();
        int result7 = (result6 * 59) + ($name == null ? 43 : $name.hashCode());
        Object $gender = getGender();
        int result8 = (result7 * 59) + ($gender == null ? 43 : $gender.hashCode());
        Object $dob = getDob();
        int result9 = (result8 * 59) + ($dob == null ? 43 : $dob.hashCode());
        Object $age = getAge();
        int result10 = (result9 * 59) + ($age == null ? 43 : $age.hashCode());
        Object $admitTime = getAdmitTime();
        int result11 = (result10 * 59) + ($admitTime == null ? 43 : $admitTime.hashCode());
        Object $inPatientTime = getInPatientTime();
        int result12 = (result11 * 59) + ($inPatientTime == null ? 43 : $inPatientTime.hashCode());
        Object $dept = getDept();
        int result13 = (result12 * 59) + ($dept == null ? 43 : $dept.hashCode());
        Object $deptCode = getDeptCode();
        int result14 = (result13 * 59) + ($deptCode == null ? 43 : $deptCode.hashCode());
        Object $bed = getBed();
        int result15 = (result14 * 59) + ($bed == null ? 43 : $bed.hashCode());
        Object $zyzt = getZyzt();
        int result16 = (result15 * 59) + ($zyzt == null ? 43 : $zyzt.hashCode());
        Object $dischargeTime = getDischargeTime();
        int result17 = (result16 * 59) + ($dischargeTime == null ? 43 : $dischargeTime.hashCode());
        Object $idcardno = getIdcardno();
        int result18 = (result17 * 59) + ($idcardno == null ? 43 : $idcardno.hashCode());
        Object $address = getAddress();
        int result19 = (result18 * 59) + ($address == null ? 43 : $address.hashCode());
        Object $height = getHeight();
        int result20 = (result19 * 59) + ($height == null ? 43 : $height.hashCode());
        Object $weight = getWeight();
        int result21 = (result20 * 59) + ($weight == null ? 43 : $weight.hashCode());
        Object $blood = getBlood();
        int result22 = (result21 * 59) + ($blood == null ? 43 : $blood.hashCode());
        Object $tel = getTel();
        int result23 = (result22 * 59) + ($tel == null ? 43 : $tel.hashCode());
        Object $operation = getOperation();
        int result24 = (result23 * 59) + ($operation == null ? 43 : $operation.hashCode());
        Object $operTime = getOperTime();
        int result25 = (result24 * 59) + ($operTime == null ? 43 : $operTime.hashCode());
        Object $diagnose = getDiagnose();
        int result26 = (result25 * 59) + ($diagnose == null ? 43 : $diagnose.hashCode());
        Object $vip = getVip();
        int result27 = (result26 * 59) + ($vip == null ? 43 : $vip.hashCode());
        Object $illnessLevel = getIllnessLevel();
        int result28 = (result27 * 59) + ($illnessLevel == null ? 43 : $illnessLevel.hashCode());
        Object $allergic = getAllergic();
        int result29 = (result28 * 59) + ($allergic == null ? 43 : $allergic.hashCode());
        Object $zhushu = getZhushu();
        int result30 = (result29 * 59) + ($zhushu == null ? 43 : $zhushu.hashCode());
        Object $doctor = getDoctor();
        int result31 = (result30 * 59) + ($doctor == null ? 43 : $doctor.hashCode());
        Object $doctorID = getDoctorID();
        int result32 = (result31 * 59) + ($doctorID == null ? 43 : $doctorID.hashCode());
        Object $shouYeXuHao = getShouYeXuHao();
        int result33 = (result32 * 59) + ($shouYeXuHao == null ? 43 : $shouYeXuHao.hashCode());
        Object $hisVisitId = getHisVisitId();
        return (result33 * 59) + ($hisVisitId == null ? 43 : $hisVisitId.hashCode());
    }

    public String toString() {
        return "PatientInfo(id=" + getId() + ", pid=" + getPid() + ", mrn=" + getMrn() + ", zycs=" + getZycs() + ", yblx=" + getYblx() + ", jzkh=" + getJzkh() + ", name=" + getName() + ", gender=" + getGender() + ", dob=" + getDob() + ", age=" + getAge() + ", admitTime=" + getAdmitTime() + ", inPatientTime=" + getInPatientTime() + ", dept=" + getDept() + ", deptCode=" + getDeptCode() + ", bed=" + getBed() + ", zyzt=" + getZyzt() + ", dischargeTime=" + getDischargeTime() + ", idcardno=" + getIdcardno() + ", address=" + getAddress() + ", height=" + getHeight() + ", weight=" + getWeight() + ", blood=" + getBlood() + ", tel=" + getTel() + ", operation=" + getOperation() + ", operTime=" + getOperTime() + ", diagnose=" + getDiagnose() + ", vip=" + getVip() + ", illnessLevel=" + getIllnessLevel() + ", allergic=" + getAllergic() + ", zhushu=" + getZhushu() + ", doctor=" + getDoctor() + ", doctorID=" + getDoctorID() + ", shouYeXuHao=" + getShouYeXuHao() + ", hisVisitId=" + getHisVisitId() + ")";
    }

    public String getId() {
        return this.id;
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

    public String getYblx() {
        return this.yblx;
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

    public Date getDob() {
        return this.dob;
    }

    public String getAge() {
        return this.age;
    }

    public Date getAdmitTime() {
        return this.admitTime;
    }

    public Date getInPatientTime() {
        return this.inPatientTime;
    }

    public String getDept() {
        return this.dept;
    }

    public String getDeptCode() {
        return this.deptCode;
    }

    public String getBed() {
        return this.bed;
    }

    public String getZyzt() {
        return this.zyzt;
    }

    public Date getDischargeTime() {
        return this.dischargeTime;
    }

    public String getIdcardno() {
        return this.idcardno;
    }

    public String getAddress() {
        return this.address;
    }

    public String getHeight() {
        return this.height;
    }

    public String getWeight() {
        return this.weight;
    }

    public String getBlood() {
        return this.blood;
    }

    public String getTel() {
        return this.tel;
    }

    public String getOperation() {
        return this.operation;
    }

    public Date getOperTime() {
        return this.operTime;
    }

    public String getDiagnose() {
        return this.diagnose;
    }

    public String getVip() {
        return this.vip;
    }

    public String getIllnessLevel() {
        return this.illnessLevel;
    }

    public String getAllergic() {
        return this.allergic;
    }

    public String getZhushu() {
        return this.zhushu;
    }

    public String getDoctor() {
        return this.doctor;
    }

    public String getDoctorID() {
        return this.doctorID;
    }

    public String getShouYeXuHao() {
        return this.shouYeXuHao;
    }

    public String getHisVisitId() {
        return this.hisVisitId;
    }
}