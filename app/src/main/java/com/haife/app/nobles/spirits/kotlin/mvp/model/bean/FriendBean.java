package com.haife.app.nobles.spirits.kotlin.mvp.model.bean;

import android.text.TextUtils;

public class FriendBean {


    /**
     * uid : 10015
     * friendUid : 101102103118
     * remark : null
     * unMsgCount : 8
     * lastMsgContent : tdytdtryd
     * modifiedTime : 2021-05-27T12:45:43.000+0000
     * user : {"uid":101102103118,"name":"火星人JFKMP6W","avatar":"http://prbsvykmy.bkt.clouddn.com/static/image/user-1-default.png","remark":"你今生有没有坚定不移地相信过一件事或一个人？是那种至死不渝的相信？"}
     * isFriend : null
     */

    private long uid;
    private long friendUid;
    private Object remark;
    private int unMsgCount;
    private String lastMsgContent;
    private String lastMsgTime;
    private String modifiedTime;
    private String friendName;
    private UserBean user;
    private Object isFriend;

    public String getFriendName() {
        if(TextUtils.isEmpty(friendName)){
            return user.name;
        }else {
            return friendName;
        }

    }

    public String getLastMsgTime() {
        return lastMsgTime;
    }

    public void setLastMsgTime(String lastMsgTime) {
        this.lastMsgTime = lastMsgTime;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getFriendUid() {
        return friendUid;
    }

    public void setFriendUid(long friendUid) {
        this.friendUid = friendUid;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
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

    public Object getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(Object isFriend) {
        this.isFriend = isFriend;
    }

    public static class UserBean {
        /**
         * uid : 101102103118
         * name : 火星人JFKMP6W
         * avatar : http://prbsvykmy.bkt.clouddn.com/static/image/user-1-default.png
         * remark : 你今生有没有坚定不移地相信过一件事或一个人？是那种至死不渝的相信？
         */

        private long uid;
        private String name;
        private String avatar;
        private String remark;

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
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
}
