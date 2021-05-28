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
}
