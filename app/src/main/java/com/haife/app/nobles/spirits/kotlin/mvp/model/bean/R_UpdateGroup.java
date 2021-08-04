package com.haife.app.nobles.spirits.kotlin.mvp.model.bean;

public class R_UpdateGroup {
    String name;
    String avatar;
        String remark;
    long groupId;

    public R_UpdateGroup(String name, String avatar, String remark, long groupId) {
        this.name = name;
        this.avatar = avatar;
        this.remark = remark;
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
}
