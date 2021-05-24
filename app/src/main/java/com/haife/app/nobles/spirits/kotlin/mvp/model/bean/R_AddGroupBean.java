package com.haife.app.nobles.spirits.kotlin.mvp.model.bean;

public class R_AddGroupBean {
    int UID;
    String SID;
    int friendUid;

    public R_AddGroupBean(int UID, String SID, int friendUid) {
        this.UID = UID;
        this.SID = SID;
        this.friendUid = friendUid;
    }
}
