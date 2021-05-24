package com.haife.app.nobles.spirits.kotlin.mvp.model.bean;

public class R_AddFriendBean {

    int UID;
    String SID;
    int friendUid;
    String remark;

    public R_AddFriendBean(int UID, String SID, int friendUid, String remark) {
        this.UID = UID;
        this.SID = SID;
        this.friendUid = friendUid;
        this.remark = remark;
    }
}
