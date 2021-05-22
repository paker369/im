package com.haife.app.nobles.spirits.kotlin.mvp.model.bean;

public class UserBean {
    /**
     * uid : 10035
     * name : 火星人1OBLR5X
     * avatar : http://prbsvykmy.bkt.clouddn.com/static/image/user-2-default.png
     * remark : 你今生有没有坚定不移地相信过一件事或一个人？是那种至死不渝的相信？
     */

    private int uid;
    private String name;
    private String avatar;
    private String remark;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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
}
