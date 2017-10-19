package cn.cityworks.soa.dapeng.domain.superviseMatter;

import cn.cityworks.soa.dapeng.domain.type.Events;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * create by afterloe on 2017/10/17
 */
@Entity(name = "formData")
public class FormDataDO implements Serializable {

    @Id
    private String id;
    private Events type;
    private String title;
    private String describe;
    private String uid;
    private String processId;
    private String activeTaskId;
    private Boolean complete;
    private Boolean assign;
    private Boolean activity;
    private Boolean needReply;
    private Long createTime;
    private Long modifyTime;
    private Boolean enable;

    public Boolean getNeedReply() {
        return needReply;
    }

    public void setNeedReply(Boolean needReply) {
        this.needReply = needReply;
    }

    public String getActiveTaskId() {
        return activeTaskId;
    }

    public void setActiveTaskId(String activeTaskId) {
        this.activeTaskId = activeTaskId;
    }

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

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public Boolean getAssign() {
        return assign;
    }

    public void setAssign(Boolean assign) {
        this.assign = assign;
    }

    public Boolean getActivity() {
        return activity;
    }

    public void setActivity(Boolean activity) {
        this.activity = activity;
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
