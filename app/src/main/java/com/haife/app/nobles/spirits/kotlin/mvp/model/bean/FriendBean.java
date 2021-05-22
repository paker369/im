package com.haife.app.nobles.spirits.kotlin.mvp.model.bean;

public class FriendBean {

    /**
     * uid : 10037
     * friendUid : 10035
     * remark :
     * unMsgCount : 0
     * lastMsgContent : hdiusahduj
     * modifiedTime : 2021-05-22T02:17:05.000+0000
     * user : {"uid":10035,"name":"火星人1OBLR5X","avatar":"http://prbsvykmy.bkt.clouddn.com/static/image/user-2-default.png","remark":"你今生有没有坚定不移地相信过一件事或一个人？是那种至死不渝的相信？"}
     * isFriend : null
     */

    private int uid;
    private int friendUid;
    private String remark;
    private int unMsgCount;
    private String lastMsgContent;
    private String modifiedTime;
    private UserBean user;
    private int isFriend;

    public int getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getFriendUid() {
        return friendUid;
    }

    public void setFriendUid(int friendUid) {
        this.friendUid = friendUid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getUnMsgCount() {
        return unMsgCount;
    }

    public void setUnMsgCount(int unMsgCount) {
        this.unMsgCount = unMsgCount;
    }

    public String getLastMsgContent() {
        return lastMsgContent;
    }

    public void setLastMsgContent(String lastMsgContent) {
        this.lastMsgContent = lastMsgContent;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }




}
