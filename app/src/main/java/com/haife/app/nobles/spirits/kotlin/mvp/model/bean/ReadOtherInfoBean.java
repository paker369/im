package com.haife.app.nobles.spirits.kotlin.mvp.model.bean;

public class ReadOtherInfoBean {

    /**
     * uid : 10010
     * name : test1
     * avatar : https://dss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1950846641,3729028697&fm=26&gp=0.jpg
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
