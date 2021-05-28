package com.haife.app.nobles.spirits.kotlin.mvp.model.bean;

public class R_deleteFriendBean {
    long sUid;
    String token;
    long friendUid;

    public R_deleteFriendBean(long sUid, String token, long friendUid) {
        this.sUid = sUid;
        this.token = token;
        this.friendUid = friendUid;
    }
}
