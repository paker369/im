package com.haife.app.nobles.spirits.kotlin.mvp.model.bean;

public class R_ChangeNameBean {
    String friendName;
    long fId;

    public R_ChangeNameBean(String friendName, long fId) {
        this.friendName = friendName;
        this.fId = fId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public long getfId() {
        return fId;
    }

    public void setfId(long fId) {
        this.fId = fId;
    }
}
