package com.digixmed.icu.pojo.entity;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("fileUploadRecord")
public class FileUploadRecord {

    @Id
    private String id;
    private String pid;
    private String hisPid;
    private String mrn;
    private String fileID;
    private String fileCode;
    private String fileName;
    private String recordtime;
    private Date recordDataTime;
    private Integer order;
    private Date updateTime;
    private String uploader;
    private String uploaderId;
    private Boolean isCDA;
    private String ftpUrl;
    private Boolean isCancelCDA;
    private String cancelAccountID;
    private Boolean isCanceling;

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

    public void setFileID(final String fileID) {
        this.fileID = fileID;
    }

    public void setFileCode(final String fileCode) {
        this.fileCode = fileCode;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public void setRecordtime(final String recordtime) {
        this.recordtime = recordtime;
    }

    public void setRecordDataTime(final Date recordDataTime) {
        this.recordDataTime = recordDataTime;
    }

    public void setOrder(final Integer order) {
        this.order = order;
    }

    public void setUpdateTime(final Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setUploader(final String uploader) {
        this.uploader = uploader;
    }

    public void setUploaderId(final String uploaderId) {
        this.uploaderId = uploaderId;
    }

    public void setIsCDA(final Boolean isCDA) {
        this.isCDA = isCDA;
    }

    public void setFtpUrl(final String ftpUrl) {
        this.ftpUrl = ftpUrl;
    }

    public void setIsCancelCDA(final Boolean isCancelCDA) {
        this.isCancelCDA = isCancelCDA;
    }

    public void setCancelAccountID(final String cancelAccountID) {
        this.cancelAccountID = cancelAccountID;
    }

    public void setIsCanceling(final Boolean isCanceling) {
        this.isCanceling = isCanceling;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FileUploadRecord)) {
            return false;
        }
        FileUploadRecord other = (FileUploadRecord) o;
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
        Object this$fileID = getFileID();
        Object other$fileID = other.getFileID();
        if (this$fileID == null) {
            if (other$fileID != null) {
                return false;
            }
        } else if (!this$fileID.equals(other$fileID)) {
            return false;
        }
        Object this$fileCode = getFileCode();
        Object other$fileCode = other.getFileCode();
        if (this$fileCode == null) {
            if (other$fileCode != null) {
                return false;
            }
        } else if (!this$fileCode.equals(other$fileCode)) {
            return false;
        }
        Object this$fileName = getFileName();
        Object other$fileName = other.getFileName();
        if (this$fileName == null) {
            if (other$fileName != null) {
                return false;
            }
        } else if (!this$fileName.equals(other$fileName)) {
            return false;
        }
        Object this$recordtime = getRecordtime();
        Object other$recordtime = other.getRecordtime();
        if (this$recordtime == null) {
            if (other$recordtime != null) {
                return false;
            }
        } else if (!this$recordtime.equals(other$recordtime)) {
            return false;
        }
        Object this$recordDataTime = getRecordDataTime();
        Object other$recordDataTime = other.getRecordDataTime();
        if (this$recordDataTime == null) {
            if (other$recordDataTime != null) {
                return false;
            }
        } else if (!this$recordDataTime.equals(other$recordDataTime)) {
            return false;
        }
        Object this$order = getOrder();
        Object other$order = other.getOrder();
        if (this$order == null) {
            if (other$order != null) {
                return false;
            }
        } else if (!this$order.equals(other$order)) {
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
        Object this$uploader = getUploader();
        Object other$uploader = other.getUploader();
        if (this$uploader == null) {
            if (other$uploader != null) {
                return false;
            }
        } else if (!this$uploader.equals(other$uploader)) {
            return false;
        }
        Object this$uploaderId = getUploaderId();
        Object other$uploaderId = other.getUploaderId();
        if (this$uploaderId == null) {
            if (other$uploaderId != null) {
                return false;
            }
        } else if (!this$uploaderId.equals(other$uploaderId)) {
            return false;
        }
        Object this$isCDA = getIsCDA();
        Object other$isCDA = other.getIsCDA();
        if (this$isCDA == null) {
            if (other$isCDA != null) {
                return false;
            }
        } else if (!this$isCDA.equals(other$isCDA)) {
            return false;
        }
        Object this$ftpUrl = getFtpUrl();
        Object other$ftpUrl = other.getFtpUrl();
        if (this$ftpUrl == null) {
            if (other$ftpUrl != null) {
                return false;
            }
        } else if (!this$ftpUrl.equals(other$ftpUrl)) {
            return false;
        }
        Object this$isCancelCDA = getIsCancelCDA();
        Object other$isCancelCDA = other.getIsCancelCDA();
        if (this$isCancelCDA == null) {
            if (other$isCancelCDA != null) {
                return false;
            }
        } else if (!this$isCancelCDA.equals(other$isCancelCDA)) {
            return false;
        }
        Object this$cancelAccountID = getCancelAccountID();
        Object other$cancelAccountID = other.getCancelAccountID();
        if (this$cancelAccountID == null) {
            if (other$cancelAccountID != null) {
                return false;
            }
        } else if (!this$cancelAccountID.equals(other$cancelAccountID)) {
            return false;
        }
        Object this$isCanceling = getIsCanceling();
        Object other$isCanceling = other.getIsCanceling();
        return this$isCanceling == null ? other$isCanceling == null : this$isCanceling.equals(other$isCanceling);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof FileUploadRecord;
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
        Object $fileID = getFileID();
        int result5 = (result4 * 59) + ($fileID == null ? 43 : $fileID.hashCode());
        Object $fileCode = getFileCode();
        int result6 = (result5 * 59) + ($fileCode == null ? 43 : $fileCode.hashCode());
        Object $fileName = getFileName();
        int result7 = (result6 * 59) + ($fileName == null ? 43 : $fileName.hashCode());
        Object $recordtime = getRecordtime();
        int result8 = (result7 * 59) + ($recordtime == null ? 43 : $recordtime.hashCode());
        Object $recordDataTime = getRecordDataTime();
        int result9 = (result8 * 59) + ($recordDataTime == null ? 43 : $recordDataTime.hashCode());
        Object $order = getOrder();
        int result10 = (result9 * 59) + ($order == null ? 43 : $order.hashCode());
        Object $updateTime = getUpdateTime();
        int result11 = (result10 * 59) + ($updateTime == null ? 43 : $updateTime.hashCode());
        Object $uploader = getUploader();
        int result12 = (result11 * 59) + ($uploader == null ? 43 : $uploader.hashCode());
        Object $uploaderId = getUploaderId();
        int result13 = (result12 * 59) + ($uploaderId == null ? 43 : $uploaderId.hashCode());
        Object $isCDA = getIsCDA();
        int result14 = (result13 * 59) + ($isCDA == null ? 43 : $isCDA.hashCode());
        Object $ftpUrl = getFtpUrl();
        int result15 = (result14 * 59) + ($ftpUrl == null ? 43 : $ftpUrl.hashCode());
        Object $isCancelCDA = getIsCancelCDA();
        int result16 = (result15 * 59) + ($isCancelCDA == null ? 43 : $isCancelCDA.hashCode());
        Object $cancelAccountID = getCancelAccountID();
        int result17 = (result16 * 59) + ($cancelAccountID == null ? 43 : $cancelAccountID.hashCode());
        Object $isCanceling = getIsCanceling();
        return (result17 * 59) + ($isCanceling == null ? 43 : $isCanceling.hashCode());
    }

    public String toString() {
        return "FileUploadRecord(id=" + getId() + ", pid=" + getPid() + ", hisPid=" + getHisPid() + ", mrn=" + getMrn() + ", fileID=" + getFileID() + ", fileCode=" + getFileCode() + ", fileName=" + getFileName() + ", recordtime=" + getRecordtime() + ", recordDataTime=" + getRecordDataTime() + ", order=" + getOrder() + ", updateTime=" + getUpdateTime() + ", uploader=" + getUploader() + ", uploaderId=" + getUploaderId() + ", isCDA=" + getIsCDA() + ", ftpUrl=" + getFtpUrl() + ", isCancelCDA=" + getIsCancelCDA() + ", cancelAccountID=" + getCancelAccountID() + ", isCanceling=" + getIsCanceling() + ")";
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

    public String getFileID() {
        return this.fileID;
    }

    public String getFileCode() {
        return this.fileCode;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getRecordtime() {
        return this.recordtime;
    }

    public Date getRecordDataTime() {
        return this.recordDataTime;
    }

    public Integer getOrder() {
        return this.order;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public String getUploader() {
        return this.uploader;
    }

    public String getUploaderId() {
        return this.uploaderId;
    }

    public Boolean getIsCDA() {
        return this.isCDA;
    }

    public String getFtpUrl() {
        return this.ftpUrl;
    }

    public Boolean getIsCancelCDA() {
        return this.isCancelCDA;
    }

    public String getCancelAccountID() {
        return this.cancelAccountID;
    }

    public Boolean getIsCanceling() {
        return this.isCanceling;
    }
}