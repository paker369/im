package com.haife.app.nobles.spirits.kotlin.mvp.model.bean;

public class ChangeInfoBean {

    /**
     * uid : 10015
     * pwd : null
     * name : 成都李老八
     * avatar : http://www.haoqbo.com/img/1623655844422IMG_CM
     * remark : 抽烟只抽煊赫门，一生只爱一个人
     * createTime : null
     * modifiedTime : 2021-06-15T06:26:56.280+0000
     * loginCode : null
     */

    private int uid;
    private Object pwd;
    private String name;
    private String avatar;
    private String remark;
    private Object createTime;
    private String modifiedTime;
    private Object loginCode;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Object getPwd() {
        return pwd;
    }

    public void setPwd(Object pwd) {
        this.pwd = pwd;
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

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Object getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(Object loginCode) {
        this.loginCode = loginCode;
    }
}
