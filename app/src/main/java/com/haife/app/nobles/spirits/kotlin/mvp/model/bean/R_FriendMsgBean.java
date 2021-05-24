package com.haife.app.nobles.spirits.kotlin.mvp.model.bean;

public class R_FriendMsgBean {
    CharSequence msgContent;
   int msgType;
   int receiverUid;

    public R_FriendMsgBean(CharSequence msgContent, int msgType, int receiverUid) {
        this.msgContent = msgContent;
        this.msgType = msgType;
        this.receiverUid = receiverUid;
    }
}
