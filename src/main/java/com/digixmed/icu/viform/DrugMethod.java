package com.digixmed.icu.viform;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("VI_ICU_YWYF")
public class DrugMethod {

    @Id
    private String id;
    private String methodId;
    private String code;
    private String name;
    private String describe;
    private String flag;

    public void setId(final String id) {
        this.id = id;
    }

    public void setMethodId(final String methodId) {
        this.methodId = methodId;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setDescribe(final String describe) {
        this.describe = describe;
    }

    public void setFlag(final String flag) {
        this.flag = flag;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DrugMethod)) {
            return false;
        }
        DrugMethod other = (DrugMethod) o;
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
        Object this$methodId = getMethodId();
        Object other$methodId = other.getMethodId();
        if (this$methodId == null) {
            if (other$methodId != null) {
                return false;
            }
        } else if (!this$methodId.equals(other$methodId)) {
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
        Object this$name = getName();
        Object other$name = other.getName();
        if (this$name == null) {
            if (other$name != null) {
                return false;
            }
        } else if (!this$name.equals(other$name)) {
            return false;
        }
        Object this$describe = getDescribe();
        Object other$describe = other.getDescribe();
        if (this$describe == null) {
            if (other$describe != null) {
                return false;
            }
        } else if (!this$describe.equals(other$describe)) {
            return false;
        }
        Object this$flag = getFlag();
        Object other$flag = other.getFlag();
        return this$flag == null ? other$flag == null : this$flag.equals(other$flag);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DrugMethod;
    }

    public int hashCode() {
        Object $id = getId();
        int result = (1 * 59) + ($id == null ? 43 : $id.hashCode());
        Object $methodId = getMethodId();
        int result2 = (result * 59) + ($methodId == null ? 43 : $methodId.hashCode());
        Object $code = getCode();
        int result3 = (result2 * 59) + ($code == null ? 43 : $code.hashCode());
        Object $name = getName();
        int result4 = (result3 * 59) + ($name == null ? 43 : $name.hashCode());
        Object $describe = getDescribe();
        int result5 = (result4 * 59) + ($describe == null ? 43 : $describe.hashCode());
        Object $flag = getFlag();
        return (result5 * 59) + ($flag == null ? 43 : $flag.hashCode());
    }

    public String toString() {
        return "DrugMethod(id=" + getId() + ", methodId=" + getMethodId() + ", code=" + getCode() + ", name=" + getName() + ", describe=" + getDescribe() + ", flag=" + getFlag() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getMethodId() {
        return this.methodId;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getDescribe() {
        return this.describe;
    }

    public String getFlag() {
        return this.flag;
    }
}