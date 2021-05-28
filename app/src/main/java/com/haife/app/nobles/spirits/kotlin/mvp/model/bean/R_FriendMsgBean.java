package com.haife.app.nobles.spirits.kotlin.mvp.model.bean;

public class R_FriendMsgBean {
    CharSequence msgContent;
   int msgType;
    long receiverUid;

    public R_FriendMsgBean(CharSequence msgContent, int msgType, long receiverUid) {
        this.msgContent = msgContent;
        this.msgType = msgType;
        this.receiverUid = receiverUid;
    }
}
