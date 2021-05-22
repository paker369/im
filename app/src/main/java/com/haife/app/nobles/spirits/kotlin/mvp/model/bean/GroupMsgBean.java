package com.haife.app.nobles.spirits.kotlin.mvp.model.bean;

public class GroupMsgBean {

    /**
     * msgId : 15
     * groupId : 16
     * senderUid : 10037
     * msgType : 0
     * msgContent : dfgbf
     * createTime : 2021-05-22T03:08:55.000+0000
     * modifiedTime : null
     * user : {"uid":10037,"name":"火星人OT5KQDJ","avatar":"http://prbsvykmy.bkt.clouddn.com/static/image/user-2-default.png","remark":"你今生有没有坚定不移地相信过一件事或一个人？是那种至死不渝的相信？"}
     */

    private int msgId;
    private int groupId;
    private int senderUid;
    private int msgType;
    private String msgContent;
    private String createTime;
    private String modifiedTime;
    private UserBean user;

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(int senderUid) {
        this.senderUid = senderUid;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

}
