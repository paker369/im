package com.haife.app.nobles.spirits.kotlin.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.haife.app.nobles.spirits.kotlin.app.base.BaseResponse;
import com.haife.app.nobles.spirits.kotlin.mvp.http.api.service.AppService;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MessageBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.ReadOtherInfoBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.haife.app.nobles.spirits.kotlin.mvp.contract.FriendChatContract;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


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
public class FriendChatModel extends BaseModel implements FriendChatContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FriendChatModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<MessageBean>>> friendMsgList(int page, int limit, long senderUid) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).friendMsgList(page, limit,senderUid);

    }

    @Override
    public Observable<BaseResponse> sendMsg(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).sendMsg(body);

    }

    @Override
    public Observable<BaseResponse<ReadOtherInfoBean>> read(long uid) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).read(uid);
    }

    @Override
    public Observable<BaseResponse> deleteFriend(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).deleteFriend(body);
    }

    @Override
    public Observable<BaseResponse<String>> upload(List<MultipartBody.Part> parts) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).upload(parts);
    }
}