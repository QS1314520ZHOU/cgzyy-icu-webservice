package com.digixmed.icu.viform;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("VI_ICU_YYPC")
public class DrugFreq {

    @Id
    private String id;

    @Indexed(direction = IndexDirection.DESCENDING)
    private String freqName;
    private String freqCode;
    private String desc;
    private String flag;

    public void setId(final String id) {
        this.id = id;
    }

    public void setFreqName(final String freqName) {
        this.freqName = freqName;
    }

    public void setFreqCode(final String freqCode) {
        this.freqCode = freqCode;
    }

    public void setDesc(final String desc) {
        this.desc = desc;
    }

    public void setFlag(final String flag) {
        this.flag = flag;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DrugFreq)) {
            return false;
        }
        DrugFreq other = (DrugFreq) o;
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
        Object this$freqName = getFreqName();
        Object other$freqName = other.getFreqName();
        if (this$freqName == null) {
            if (other$freqName != null) {
                return false;
            }
        } else if (!this$freqName.equals(other$freqName)) {
            return false;
        }
        Object this$freqCode = getFreqCode();
        Object other$freqCode = other.getFreqCode();
        if (this$freqCode == null) {
            if (other$freqCode != null) {
                return false;
            }
        } else if (!this$freqCode.equals(other$freqCode)) {
            return false;
        }
        Object this$desc = getDesc();
        Object other$desc = other.getDesc();
        if (this$desc == null) {
            if (other$desc != null) {
                return false;
            }
        } else if (!this$desc.equals(other$desc)) {
            return false;
        }
        Object this$flag = getFlag();
        Object other$flag = other.getFlag();
        return this$flag == null ? other$flag == null : this$flag.equals(other$flag);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DrugFreq;
    }

    public int hashCode() {
        Object $id = getId();
        int result = (1 * 59) + ($id == null ? 43 : $id.hashCode());
        Object $freqName = getFreqName();
        int result2 = (result * 59) + ($freqName == null ? 43 : $freqName.hashCode());
        Object $freqCode = getFreqCode();
        int result3 = (result2 * 59) + ($freqCode == null ? 43 : $freqCode.hashCode());
        Object $desc = getDesc();
        int result4 = (result3 * 59) + ($desc == null ? 43 : $desc.hashCode());
        Object $flag = getFlag();
        return (result4 * 59) + ($flag == null ? 43 : $flag.hashCode());
    }

    public String toString() {
        return "DrugFreq(id=" + getId() + ", freqName=" + getFreqName() + ", freqCode=" + getFreqCode() + ", desc=" + getDesc() + ", flag=" + getFlag() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getFreqName() {
        return this.freqName;
    }

    public String getFreqCode() {
        return this.freqCode;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getFlag() {
        return this.flag;
    }
}