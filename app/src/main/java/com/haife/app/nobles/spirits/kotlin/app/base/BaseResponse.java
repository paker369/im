package com.haife.app.nobles.spirits.kotlin.app.base;



import com.blankj.utilcode.util.SPUtils;
import com.haife.app.nobles.spirits.kotlin.app.constant.SPConstant;
import com.haife.app.nobles.spirits.kotlin.mvp.http.service.Api;
import com.jess.arms.utils.LogUtils;


import java.io.Serializable;


/**
 * ================================================
 * 如果你服务器返回的数据格式固定为这种方式(这里只提供思想,服务器返回的数据格式可能不一致,可根据自家服务器返回的格式作更改)
 * 指定范型即可改变 {@code data} 字段的类型, 达到重用 {@link BaseResponse}, 如果你实在看不懂, 请忽略
 * <p>
 * Created by JessYan on 26/09/2016 15:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */

public class BaseResponse<T> implements Serializable {


    public boolean success;
    private T data;
    private int code;
    private String message;
    private long serverTime;
    private String submessage = "";

    public boolean isSuccess() {

        if (message!=null&&message.contains("登录失效")) {
            SPUtils.getInstance().remove(SPConstant.SID);
            SPUtils.getInstance().remove(SPConstant.UID);
            SPUtils.getInstance().remove(SPConstant.USERNAME);
        }
        return isCodeSuccess();
    }

//    public void setSuccess(boolean success) {
//        this.success = success;
//    }

    /**
     * 请求是否成功
     *
     * @return
     */
    public boolean isCodeSuccess() {
        if (code != Api.RequestSuccess) {
            LogUtils.debugInfo("测试请求不成功" + code);
        }
        return code == Api.RequestSuccess;
    }

    /**
     * 获取返回信息
     *
     * @return
     */
    public String getMessage() {
//        if (code != ApiConstants.RequestSuccess && subCode != ApiConstants.RequestSuccess) {
//            return submessage;
//        } else if (code != ApiConstants.RequestSuccess) {
//            return message;
//        } else if (subCode != ApiConstants.RequestSuccess) {
//            return submessage;
//        } else {
//            return "";
//        }
//        if(isSuccess()){
        return message;
//        }else{
//            return submessage;
//        }
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    public String getSubmessage() {
        return submessage;
    }

    public void setSubmessage(String submessage) {
        this.submessage = submessage;
    }
}

