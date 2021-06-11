package com.haife.app.nobles.spirits.kotlin.app.constant;

import com.blankj.utilcode.util.SPUtils;
import com.haife.app.nobles.spirits.kotlin.mvp.http.entity.bean.CityBean;

import java.util.List;

/**
 * @author Eddie Android Developer
 * @company Q | 樽尚汇
 * @Lin TODO:
 * @since 2019/1/14$
 */
public class SPConstant {
    /*登录手机号*/
    public static final String SP_LOGIN_PHONE = "SP_LOGIN_PHONE";
    public static final String SP_LOGIN_SUCCESS_CODE = "SP_LOGIN_SUCCESS_CODE";

    /**
     * 保存后台返回的所有城市地址
     * {@link List<CityBean>}
     */
    public static final String SP_CITY_LIST = "SP_CITY_LIST";
    /*当前城市*/
    public static final String CURRENT_CITY = "CURRENT_CITY";
    public static final String SID ="TOKEN" ;
    public static final String UID = "USER_ID";
    public static final String USERNAME = "USERNAME";
    public static final String HEADER = "HEADER";
    public final  static String qianzhui="/api";
    public static final String KEYBOARD = "KEYBOARD";
    public static final String RECEIVEWSSINGLECHATE ="RECEIVEWSSINGLECHATE" ;
    public static final String RECEIVEWSGROUPCHATE ="RECEIVEWSGROUPCHATE" ;
    public static final String FRIENDMESSAGE = "FRIENDMESSAGE";
    public static final String GROUPMESSAGE = "GROUPMESSAGE";
    public static final String ADDPERSON = "ADDPERSON";
    public static final String ADDGROUP = "ADDGROUP";
    public static final String loginInfoSuccess="loginInfoSuccess";
    public static final String SHOWINFO ="SHOWINFO" ;
    public static final String LOGOUT ="LOGOUT" ;
    public static final String CLICKMORE ="CLICKMORE" ;
    public static final String OPENSLIDE = "OPENSLIDE";
    public static final String REMEBERID ="REMEBERID" ;
//    public   static String MYSID= SPUtils.getInstance().getString(SID,"");
//    public   static long MYUID=SPUtils.getInstance().getLong(UID);
}
