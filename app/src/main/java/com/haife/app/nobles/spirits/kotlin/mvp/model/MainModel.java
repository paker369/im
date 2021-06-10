package com.haife.app.nobles.spirits.kotlin.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.haife.app.nobles.spirits.kotlin.app.base.BaseResponse;
import com.haife.app.nobles.spirits.kotlin.mvp.http.api.service.AppService;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.LoginInfoBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MyGroup;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.haife.app.nobles.spirits.kotlin.mvp.contract.MainContract;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Query;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/22/2021 11:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<LoginInfoBean>> loginInfo(long uId, String sId) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).loginInfo(uId, sId);
    }

    @Override
    public Observable<BaseResponse> addFriend(long uId, String sId, long friendUid,  String remark) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).addFriend( uId, sId, friendUid, remark);
    }

    @Override
    public Observable<BaseResponse<MyGroup>> addGroup(long uId, String sId,long groupId) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).addGroup(uId, sId, groupId);

    }

    @Override
    public Observable<BaseResponse<String>> uploadAvatar(List<MultipartBody.Part> parts) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).uploadAvatar(parts);

    }
}