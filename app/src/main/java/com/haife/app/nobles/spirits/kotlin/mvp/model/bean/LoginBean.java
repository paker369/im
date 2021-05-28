package com.haife.app.nobles.spirits.kotlin.mvp.model.bean;

public class LoginBean {


    /**
     * uid : 10015
     * sid : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjEwMDE1fQ.hiHveHMkEFSXrmF7aZ15l4mVbIjEfBXORvbVY0cG0BI
     * loginCode : null
     */

    private long uid;
    private String sid;
    private int loginCode;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(int loginCode) {
        this.loginCode = loginCode;
    }
}
