package com.haife.app.nobles.spirits.kotlin.mvp.model.bean;

import java.io.Serializable;

public class R_Ws implements Serializable {
    long uid;
    String sid;
    int type;

    public R_Ws(long uid, String sid, int type) {
        this.uid = uid;
        this.sid = sid;
        this.type = type;
    }
}
