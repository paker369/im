package com.haife.app.nobles.spirits.kotlin.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.haife.app.nobles.spirits.kotlin.app.base.BaseResponse;
import com.haife.app.nobles.spirits.kotlin.mvp.http.api.service.AppService;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MyGroup;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.haife.app.nobles.spirits.kotlin.mvp.contract.GroupContract;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/22/2021 13:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class GroupModel extends BaseModel implements GroupContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public GroupModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<MyGroup>>> myGroupList( int page,  int limit) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).myGroupList(page, limit);
    }

    @Override
    public Observable<BaseResponse> deleteGroup(long groupid) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).deleteGroup(groupid);
    }

    @Override
    public Observable<BaseResponse<MyGroup.GroupBean>> createGroup(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).createGroup(body);
    }

    @Override
    public Observable<BaseResponse> deleteMyGroup(long groupid) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).deleteMygroup(groupid);
    }
}