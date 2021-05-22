package com.haife.app.nobles.spirits.kotlin.mvp.model.bean;

public class LoginInfoBean {

    /**
     * uid : 10015
     * name : chengdulilaoba
     * avatar : https://dss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1950846641,3729028697&fm=26&gp=0.jpg
     * remark : 你今生有没有坚定不移地相信过一件事或一个人？是那种至死不渝的相信？
     * createTime : 2021-05-21T10:55:16.000+0000
     * profile : {"friendAskCount":null,"friendCount":null}
     */

    private int uid;
    private String name;
    private String avatar;
    private String remark;
    private String createTime;
    private ProfileBean profile;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public ProfileBean getProfile() {
        return profile;
    }

    public void setProfile(ProfileBean profile) {
        this.profile = profile;
    }

    public static class ProfileBean {
        /**
         * friendAskCount : null
         * friendCount : null
         */

        private Object friendAskCount;
        private Object friendCount;

        public Object getFriendAskCount() {
            return friendAskCount;
        }

        public void setFriendAskCount(Object friendAskCount) {
            this.friendAskCount = friendAskCount;
        }

        public Object getFriendCount() {
            return friendCount;
        }

        public void setFriendCount(Object friendCount) {
            this.friendCount = friendCount;
        }
    }
}
