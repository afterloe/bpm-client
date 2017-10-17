package cn.cityworks.soa.dapeng.domain.superviseMatter;

import cn.cityworks.soa.dapeng.domain.type.Events;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * create by afterloe on 2017/10/17
 */
@Entity(name = "fromData")
public class FromDataDO implements Serializable {

    @Id
    private String id;
    private Events type;
    private String title;
    private String describe;
    private String uid;
    private String processId;
    private boolean isComplete;
    private Long createTime;
    private Long modifyTime;
    private Boolean enable;

    public String getType() {
        return type.getName();
    }

    public void setType(String value) {
        this.type = Events.get(value);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setType(Events type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
