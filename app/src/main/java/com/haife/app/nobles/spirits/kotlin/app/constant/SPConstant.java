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
    public final  static String qianzhui="/api";
    public   static String MYSID= SPUtils.getInstance().getString(SID,"");
    public   static int MYUID=SPUtils.getInstance().getInt(UID,0);
}
