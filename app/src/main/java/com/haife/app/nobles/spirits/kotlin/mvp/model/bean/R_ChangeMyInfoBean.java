package com.haife.app.nobles.spirits.kotlin.mvp.model.bean;

public class R_ChangeMyInfoBean {

    long uid;
    String name;
    String avatar;
    String remark;

    public R_ChangeMyInfoBean(long uid, String name, String avatar, String remark) {
        this.uid = uid;
        this.name = name;
        this.avatar = avatar;
        this.remark = remark;
    }
}
