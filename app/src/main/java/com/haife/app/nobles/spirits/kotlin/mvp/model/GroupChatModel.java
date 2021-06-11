package com.haife.app.nobles.spirits.kotlin.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.haife.app.nobles.spirits.kotlin.app.base.BaseResponse;
import com.haife.app.nobles.spirits.kotlin.mvp.http.api.service.AppService;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.GroupMemberBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.GroupMsgBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MessageBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.ReadOtherInfoBean;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.haife.app.nobles.spirits.kotlin.mvp.contract.GroupChatContract;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Query;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/22/2021 14:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class GroupChatModel extends BaseModel implements GroupChatContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public GroupChatModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<GroupMsgBean>>> groupMsgList(int page, int limit, long groupid) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).groupMsgList(page, limit,groupid);

    }

    @Override
    public Observable<BaseResponse> sendGroupMsg(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).sendGroupMsg(body);

    }


    @Override
    public Observable<BaseResponse<List<GroupMemberBean>>> groupMemberList(long uId, String sId, long groupid) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).groupMemberList(uId, sId, groupid);

    }

    @Override
    public Observable<BaseResponse> groupUpdate(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).groupUpdate(body);

    }


    @Override
    public Observable<BaseResponse<String>> upload(List<MultipartBody.Part> parts) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).upload(parts);
    }
}