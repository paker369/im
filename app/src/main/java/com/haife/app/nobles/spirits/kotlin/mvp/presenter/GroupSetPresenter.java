package com.haife.app.nobles.spirits.kotlin.mvp.presenter;

import android.app.Application;

import com.google.gson.Gson;
import com.haife.app.nobles.spirits.kotlin.app.base.BaseResponse;
import com.haife.app.nobles.spirits.kotlin.app.utils.RxUtils;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.R_UpdateGroup;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.haife.app.nobles.spirits.kotlin.mvp.contract.GroupSetContract;

import java.util.List;


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
public class GroupSetPresenter extends BasePresenter<GroupSetContract.Model, GroupSetContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public GroupSetPresenter(GroupSetContract.Model model, GroupSetContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
    public void deleteGroup(long groupid) {

        mModel.deleteGroup(groupid)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse aboutBeanBaseResponse) {
                        if (aboutBeanBaseResponse.isSuccess()) {

                            mRootView.deleteGroupSuccess();
                        } else {

                            mRootView.showMessage(aboutBeanBaseResponse.getMessage());
                        }
                    }
                });
    }
    public void deleteMyGroup(long groupid) {

        mModel.deleteMyGroup(groupid)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse aboutBeanBaseResponse) {
                        if (aboutBeanBaseResponse.isSuccess()) {

                            mRootView.deleteMyGroupSuccess();
                        } else {

                            mRootView.showMessage(aboutBeanBaseResponse.getMessage());
                        }
                    }
                });
    }

    public void groupUpdate(String name,String avatar,String remark,long groupId) {
        R_UpdateGroup userInfoBean = new R_UpdateGroup(name, avatar, remark, groupId);
        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(userInfoBean));
        mModel.groupUpdate(body)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse aboutBeanBaseResponse) {
                        if (aboutBeanBaseResponse.isSuccess()) {
                            mRootView.groupUpdateSuccess( );
                        } else {

                            mRootView.showMessage(aboutBeanBaseResponse.getMessage());
                        }
                    }
                });
    }

    public void upload(List<MultipartBody.Part> parts) {

        mModel.upload(parts)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<String> aboutBeanBaseResponse) {

                        mRootView.uploadSuccess( aboutBeanBaseResponse.getData());

                    }
                });
    }

    public void uploadAvatar(List<MultipartBody.Part> parts) {

        mModel.uploadAvatar(parts)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse aboutBeanBaseResponse) {

                        if (aboutBeanBaseResponse.isSuccess()) {
                            mRootView.uploadAvatarSuccess();
                        } else {

                            mRootView.showMessage(aboutBeanBaseResponse.getMessage());
                        }

                    }
                });
    }
}
