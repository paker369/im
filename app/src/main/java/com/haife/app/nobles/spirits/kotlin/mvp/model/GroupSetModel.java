package com.haife.app.nobles.spirits.kotlin.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.haife.app.nobles.spirits.kotlin.app.base.BaseResponse;
import com.haife.app.nobles.spirits.kotlin.mvp.http.api.service.AppService;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.haife.app.nobles.spirits.kotlin.mvp.contract.GroupSetContract;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/09/2021 16:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class GroupSetModel extends BaseModel implements GroupSetContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public GroupSetModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
    @Override
    public Observable<BaseResponse> deleteGroup(long groupid) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).deleteGroup(groupid);
    }
    @Override
    public Observable<BaseResponse> deleteMyGroup(long groupid) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).deleteMygroup(groupid);
    }

    @Override
    public Observable<BaseResponse> groupUpdate(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).groupUpdate(body);

    }

    @Override
    public Observable<BaseResponse<String>> upload(List<MultipartBody.Part> parts) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).upload(parts);
    }

    @Override
    public Observable<BaseResponse<String>> uploadAvatar(List<MultipartBody.Part> parts) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).uploadAvatar(parts);

    }


}