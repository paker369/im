package com.haife.app.nobles.spirits.kotlin.mvp.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class MessageBean implements MultiItemEntity {

    /**
     * msgId : 116
     * uid : null
     * toUid : null
     * senderUid : 10037
     * msgType : 0
     * msgContent : hdiusahduj
     * createTime : 2021-05-22T02:17:05.000+0000
     */

    private int msgId;
    private long uid;
    private long toUid;
    private long senderUid;
    private int msgType;
    private String msgContent;
    private String createTime;

    public MessageBean(long uid, long toUid, long senderUid, int msgType, String msgContent, String createTime) {
        this.uid = uid;
        this.toUid = toUid;
        this.senderUid = senderUid;
        this.msgType = msgType;
        this.msgContent = msgContent;
        this.createTime = createTime;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getToUid() {
        return toUid;
    }

    public void setToUid(long toUid) {
        this.toUid = toUid;
    }

    public long getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(long senderUid) {
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

    @Override
    public int getItemType() {
        int type = 1;
        switch (msgType) {
            case 0:
                type = 1;
                break;
            case 1:
                type = 2;
        }
        return type;
    }
}
