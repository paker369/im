package com.haife.app.nobles.spirits.kotlin.mvp.model.bean;

public class R_AddGroupBean {
    long UID;
    String SID;
    long friendUid;

    public R_AddGroupBean(long UID, String SID, long friendUid) {
        this.UID = UID;
        this.SID = SID;
        this.friendUid = friendUid;
    }
}
