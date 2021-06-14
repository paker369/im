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
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    @POST(SPConstant.qianzhui + "/user/login/registerUser")
    Observable<BaseResponse<LoginBean>> registerUser(@Body RequestBody requestBody);


    /*
     * 登录信息
     */
    @GET(SPConstant.qianzhui + "/user/loginInfo")
    Observable<BaseResponse<LoginInfoBean>> loginInfo(@Query("UID") long uId, @Query("SID") String sId);


    /*
     * 读取用户
     */
    @GET(SPConstant.qianzhui + "/user/read")
    Observable<BaseResponse<ReadOtherInfoBean>> read( @Query("uid") long uid);


    /*
     * 私聊列表
     */
    @GET(SPConstant.qianzhui + "/user/friendMsg/lists")
    Observable<BaseResponse<List<MessageBean>>> friendMsgList(@Query("page") int page, @Query("limit") int limit, @Query("senderUid") long senderUid);


    /*
     * 发送消息
     */
    @POST(SPConstant.qianzhui + "/user/friendMsg/create")
    Observable<BaseResponse> sendMsg(@Body RequestBody requestBody);


    /*
     * 好友列表
     */
    @GET(SPConstant.qianzhui + "/user/friend/lists")
    Observable<BaseResponse<List<FriendBean>>> friendList(@Query("page") int page, @Query("limit") int limit);


    /*
     * 删除好友
     */
    @POST(SPConstant.qianzhui + "/user/friend/delete")
    Observable<BaseResponse> deleteFriend(@Body RequestBody requestBody);


    /*
     * 添加好友
     */
    @POST(SPConstant.qianzhui + "/user/friendAsk/create")
    Observable<BaseResponse> addFriend(@Query("UID") long uId, @Query("SID") String sId,@Query("friendUid") long friendUid, @Query("remark") String remark);


    /*
     * 好友申请列表
     */
    @GET(SPConstant.qianzhui + "/user/friendAsk/lists")
    Observable<BaseResponse<List<FriendAskBean>>> addFriendList(@Query("page") int page, @Query("limit") int limit);


    /*
     * 处理申请消息
     */
    @POST(SPConstant.qianzhui + "/user/friendAsk/ack")
    Observable<BaseResponse> confirmOrNot(@Body RequestBody requestBody);


    /*
     * 我的群组列表
     */
    @GET(SPConstant.qianzhui + "/group/user/lists")
    Observable<BaseResponse<List<MyGroup>>> myGroupList(@Query("page") int page, @Query("limit") int limit);


    /*
     * 加入群组
     */
    @POST(SPConstant.qianzhui + "/group/user/create")
    Observable<BaseResponse<MyGroup>> addGroup(@Query("UID") long uId, @Query("SID") String sId,@Query("groupId") long groupId);


    /*
     * 退群
     */
    @POST(SPConstant.qianzhui + "/group/user/delete")
    Observable<BaseResponse> deleteGroup(@Query("groupId") long groupid);



    /*
     * 创建群组
     */
    @POST(SPConstant.qianzhui + "/group/create")
    Observable<BaseResponse<MyGroup.GroupBean>> createGroup(@Body RequestBody requestBody);

    /*
     * 群消息列表
     */
    @GET(SPConstant.qianzhui + "/group/msg/lists")
    Observable<BaseResponse<List<GroupMsgBean>>> groupMsgList(@Query("page") int page, @Query("limit") int limit,@Query("groupId") long groupid);


    /*
     * 发送群消息
     */
    @POST(SPConstant.qianzhui + "/group/msg/create")
    Observable<BaseResponse> sendGroupMsg(@Body RequestBody requestBody);


    /*
     * 群成员列表
     */
    @GET(SPConstant.qianzhui + "/group/lists")
    Observable<BaseResponse<List<GroupMemberBean>>> groupMemberList(@Query("UID") long uId, @Query("SID") String sId,@Query("groupId") long groupid );


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
    Observable<BaseResponse> deleteMygroup(@Query("groupId") long groupid);


    //    @Multipart
//    @POST(AppConstants.qianzhui + "/upload/img")
//    Observable<BaseResponse<String>> uploadimg(@Part List<MultipartBody.Part> parts);
    /*
     * 上传文件
     */
    @Multipart
    @POST(SPConstant.qianzhui + "/upload")
    Observable<BaseResponse<String>> upload(@Part List<MultipartBody.Part> parts);


    /*
     * 更换头像
     */
    @Multipart
    @POST(SPConstant.qianzhui + "/uploadAvatar")
    Observable<BaseResponse<String>> uploadAvatar(@Part List<MultipartBody.Part> parts);




    /*
     * 私聊未读消息归零
     */
    @POST(SPConstant.qianzhui + "/user/friendMsg/clearUnMsgCount")
    Observable<BaseResponse> clearfriendMsg(@Body RequestBody body);


    /*
     * 群组未读消息归零
     */
    @POST(SPConstant.qianzhui + "/group/user/clearUnMsgCount")
    Observable<BaseResponse> cleargroupMsg(@Query("groupId") long groupid);


    /*
     * 修改用户信息
     */
    @POST(SPConstant.qianzhui + "/user/write")
    Observable<BaseResponse> changeinfo(@Body RequestBody body);


    /*
     * 修改备注
     */
    @POST(SPConstant.qianzhui + "/user/friend/updateName")
    Observable<BaseResponse> changenickname(@Query("friendName") String groupid,@Query("fId") long fId);



}