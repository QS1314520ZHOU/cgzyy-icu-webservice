package com.digixmed.icu.pojo;

import java.util.Date;

public class Account {
    private String id;
    private String hospitalCode;
    private String deptCode;
    private int sortId;
    private String loginName;
    private String workID;
    private String trueName;
    private String gender;
    private String officeID;
    private Date dob;
    private String role;
    private String title;
    private String schoolLevel;
    private Date inDate;
    private Date outDate;
    private String pwd;
    private Date createTime;
    private Date lastUpdateTime;
    private String status;
    private String phone;
    private String latestSignedPic;
    private String orLevel;
    private boolean chargeReview;
    private boolean chargeUpload;
    private boolean chargeReviewUpdate;
    private String hisID = null;
    private String officeName = "";
    private Date invalidDate = null;
    private String invalidReason = null;
    private Boolean canLogin = false;

    public String getHospitalCode() {
        return this.hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getDeptCode() {
        return this.deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public int getSortId() {
        return this.sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public Date getOutDate() {
        return this.outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public String getOrLevel() {
        return this.orLevel;
    }

    public void setOrLevel(String orLevel) {
        this.orLevel = orLevel;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLatestSignedPic() {
        return this.latestSignedPic;
    }

    public void setLatestSignedPic(String latestSignedPic) {
        this.latestSignedPic = latestSignedPic;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getTrueName() {
        return this.trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getWorkID() {
        return this.workID;
    }

    public void setWorkID(String workID) {
        this.workID = workID;
    }

    public String getOfficeID() {
        return this.officeID;
    }

    public void setOfficeID(String officeID) {
        this.officeID = officeID;
    }

    public String getOfficeName() {
        return this.officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return this.dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getRole() {
        if (null == this.role) {
            return "";
        }
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSchoolLevel() {
        return this.schoolLevel;
    }

    public void setSchoolLevel(String schoolLevel) {
        this.schoolLevel = schoolLevel;
    }

    public Date getInDate() {
        return this.inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return getTrueName();
    }

    public String getHisID() {
        return this.hisID;
    }

    public void setHisID(String hisID) {
        this.hisID = hisID;
    }

    public Date getInvalidDate() {
        return this.invalidDate;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

    public String getInvalidReason() {
        return this.invalidReason;
    }

    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }

    public Boolean getCanLogin() {
        return this.canLogin;
    }

    public void setCanLogin(Boolean canLogin) {
        this.canLogin = canLogin;
    }

    public boolean isChargeReview() {
        return this.chargeReview;
    }

    public void setChargeReview(boolean chargeReview) {
        this.chargeReview = chargeReview;
    }

    public boolean isChargeUpload() {
        return this.chargeUpload;
    }

    public void setChargeUpload(boolean chargeUpload) {
        this.chargeUpload = chargeUpload;
    }

    public boolean isChargeReviewUpdate() {
        return this.chargeReviewUpdate;
    }

    public void setChargeReviewUpdate(boolean chargeReviewUpdate) {
        this.chargeReviewUpdate = chargeReviewUpdate;
    }
}