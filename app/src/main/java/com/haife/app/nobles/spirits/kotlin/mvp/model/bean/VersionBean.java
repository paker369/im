package com.haife.app.nobles.spirits.kotlin.mvp.model.bean;

public class VersionBean {

    /**
     * versionMini : 0.0.0
     * upgradeMsg : 123
     * versionCurrent : 1.0.5
     * createTime : 1623914367000
     * appName : 2
     * updateTime : 1623914370000
     * id : 2
     * type : 1
     */

    private String versionMini;
    private String upgradeMsg;
    private String versionCurrent;
    private long createTime;
    private String appName;
    private long updateTime;
    private int id;
    private int type;
    private String  apkUrl;

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getVersionMini() {
        return versionMini;
    }

    public void setVersionMini(String versionMini) {
        this.versionMini = versionMini;
    }

    public String getUpgradeMsg() {
        return upgradeMsg;
    }

    public void setUpgradeMsg(String upgradeMsg) {
        this.upgradeMsg = upgradeMsg;
    }

    public String getVersionCurrent() {
        return versionCurrent;
    }

    public void setVersionCurrent(String versionCurrent) {
        this.versionCurrent = versionCurrent;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
