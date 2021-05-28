package com.haife.app.nobles.spirits.kotlin.mvp.model.bean;

public class R_SendGroupMsgBean {

    CharSequence msgContent;
    int msgType;
    long groupId;

    public R_SendGroupMsgBean(CharSequence msgContent, int msgType, long groupId) {
        this.msgContent = msgContent;
        this.msgType = msgType;
        this.groupId = groupId;
    }
}
