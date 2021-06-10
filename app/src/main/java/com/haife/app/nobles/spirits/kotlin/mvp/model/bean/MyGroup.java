package com.haife.app.nobles.spirits.kotlin.mvp.model.bean;

public class MyGroup {
    private long id;
    private long groupId;//同GroupBean的groupId
    private long uid;//我的id
    private String remark;
    private int lastAckMsgId;
    private String lastMsgContent;
    private String lastMsgTime;
    private int unMsgCount;
    private int rank;
    private GroupBean group;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getLastAckMsgId() {
        return lastAckMsgId;
    }

    public void setLastAckMsgId(int lastAckMsgId) {
        this.lastAckMsgId = lastAckMsgId;
    }

    public String getLastMsgContent() {
        return lastMsgContent;
    }

    public void setLastMsgContent(String lastMsgContent) {
        this.lastMsgContent = lastMsgContent;
    }

    public String getLastMsgTime() {
        return lastMsgTime;
    }

    public void setLastMsgTime(String lastMsgTime) {
        this.lastMsgTime = lastMsgTime;
    }

    public int getUnMsgCount() {
        return unMsgCount;
    }

    public void setUnMsgCount(int unMsgCount) {
        this.unMsgCount = unMsgCount;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public GroupBean getGroup() {
        return group;
    }

    public void setGroup(GroupBean group) {
        this.group = group;
    }

    public static class GroupBean {
        private long groupId;
        private long uid;//群主id
        private String name;
        private String remark;
        private String avatar;
        private int memberNum;
        private String modifiedTime;
        private String createTime;

        public long getGroupId() {
            return groupId;
        }

        public void setGroupId(long groupId) {
            this.groupId = groupId;
        }

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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getMemberNum() {
            return memberNum;
        }

        public void setMemberNum(int memberNum) {
            this.memberNum = memberNum;
        }

        public String getModifiedTime() {
            return modifiedTime;
        }

        public void setModifiedTime(String modifiedTime) {
            this.modifiedTime = modifiedTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
