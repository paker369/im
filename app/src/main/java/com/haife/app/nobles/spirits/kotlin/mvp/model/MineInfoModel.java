package com.haife.app.nobles.spirits.kotlin.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.haife.app.nobles.spirits.kotlin.app.base.BaseResponse;
import com.haife.app.nobles.spirits.kotlin.mvp.http.api.service.AppService;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.R_ChangeNameBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.haife.app.nobles.spirits.kotlin.mvp.contract.MineInfoContract;

import io.reactivex.Observable;
import okhttp3.RequestBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/10/2021 13:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MineInfoModel extends BaseModel implements MineInfoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MineInfoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse> deleteFriend(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).deleteFriend(body);

    }

    @Override
    public Observable<R_ChangeNameBean> changenickname(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(AppService.class).changenickname(body);

    }
}