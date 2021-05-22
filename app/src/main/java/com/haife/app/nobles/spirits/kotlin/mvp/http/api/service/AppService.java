package com.haife.app.nobles.spirits.kotlin.mvp.http.api.service;


import android.content.Intent;

import com.haife.app.nobles.spirits.kotlin.app.base.BaseResponse;
import com.haife.app.nobles.spirits.kotlin.app.constant.SPConstant;
import com.haife.app.nobles.spirits.kotlin.mvp.http.entity.result.HomeRecommendData;
import com.haife.app.nobles.spirits.kotlin.mvp.http.entity.result.RestaurantUnionBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.FriendAskBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.FriendBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.GroupMemberBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.GroupMsgBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.LoginBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.LoginInfoBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MessageBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MyGroup;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.ReadOtherInfoBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AppService {
    /*
     * 用户登陆
     */
    @POST(SPConstant.qianzhui + "/user/login/byPwd")
    Observable<BaseResponse<LoginBean>> loginbyPwd(@Body RequestBody requestBody);


    /*
     * 用户注册
     */
    @GET(SPConstant.qianzhui + "/user/login/registerUser")
    Observable<BaseResponse<LoginBean>> registerUser(@Body RequestBody requestBody);


    /*
     * 登录信息
     */
    @GET(SPConstant.qianzhui + "/user/loginInfo")
    Observable<BaseResponse<LoginInfoBean>> loginInfo(@Query("UID") int uId, @Query("SID") String sId);


    /*
     * 读取用户
     */
    @GET(SPConstant.qianzhui + "/user/read")
    Observable<BaseResponse<ReadOtherInfoBean>> read(@Query("UID") int uId, @Query("SID") String sId, @Query("uid") int uid);


    /*
     * 私聊列表
     */
    @GET(SPConstant.qianzhui + "/user/friendMsg/lists")
    Observable<BaseResponse<List<MessageBean>>> friendMsgList(@Query("UID") int uId, @Query("SID") String sId, @Query("senderUid") int senderUid);


    /*
     * 发送消息
     */
    @POST(SPConstant.qianzhui + "/user/friendMsg/create")
    Observable<BaseResponse> sendMsg(@Body RequestBody requestBody);


    /*
     * 好友列表
     */
    @POST(SPConstant.qianzhui + "/user/friend/lists")
    Observable<BaseResponse<List<FriendBean>>> friendList(@Body RequestBody requestBody);


    /*
     * 删除好友
     */
    @POST(SPConstant.qianzhui + "/user/friend/delete")
    Observable<BaseResponse> deleteFriend(@Body RequestBody requestBody);


    /*
     * 添加好友
     */
    @POST(SPConstant.qianzhui + "/user/friendAsk/create")
    Observable<BaseResponse> addFriend(@Body RequestBody requestBody);


    /*
     * 好友申请列表
     */
    @GET(SPConstant.qianzhui + "/user/friendAsk/lists")
    Observable<BaseResponse<List<FriendAskBean>>> addFriendList(@Body RequestBody requestBody);


    /*
     * 处理申请消息
     */
    @POST(SPConstant.qianzhui + "/user/friendAsk/ack")
    Observable<BaseResponse> confirmOrNot(@Body RequestBody requestBody);


    /*
     * 我的群组列表
     */
    @GET(SPConstant.qianzhui + "/group/user/lists")
    Observable<BaseResponse<List<MyGroup>>> myGroupList(@Body RequestBody requestBody);


    /*
     * 加入群组
     */
    @POST(SPConstant.qianzhui + "/group/user/create")
    Observable<BaseResponse<MyGroup>> addGroup(@Body RequestBody requestBody);


    /*
     * 退群
     */
    @POST(SPConstant.qianzhui + "/group/user/delete")
    Observable<BaseResponse> deleteGroup(@Body RequestBody requestBody);



    /*
     * 创建群组
     */
    @POST(SPConstant.qianzhui + "/group/create")
    Observable<BaseResponse<MyGroup.GroupBean>> createGroup(@Body RequestBody requestBody);

    /*
     * 群消息列表
     */
    @POST(SPConstant.qianzhui + "/group/msg/lists")
    Observable<BaseResponse<List<GroupMsgBean>>> groupMsgList(@Body RequestBody requestBody);


    /*
     * 发送群消息
     */
    @POST(SPConstant.qianzhui + "/group/msg/create")
    Observable<BaseResponse> sendGroupMsg(@Body RequestBody requestBody);


    /*
     * 群成员列表
     */
    @POST(SPConstant.qianzhui + "/group/lists")
    Observable<BaseResponse<List<GroupMemberBean>>> groupMemberList(@Body RequestBody requestBody);


    /*
     * 我的群名片修改
     */
    @POST(SPConstant.qianzhui + "/group/user/update")
    Observable<BaseResponse> groupMyInfoUpdate(@Body RequestBody requestBody);



    /*
     * 修改我的群信息
     */
    @POST(SPConstant.qianzhui + "/group/update")
    Observable<BaseResponse> groupUpdate(@Body RequestBody requestBody);


    /*
     * 解散群组
     */
    @POST(SPConstant.qianzhui + "/group/delete")
    Observable<BaseResponse> deletegroup(@Body RequestBody requestBody);


}