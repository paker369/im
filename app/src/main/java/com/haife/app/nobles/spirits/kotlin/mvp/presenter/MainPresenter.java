package com.haife.app.nobles.spirits.kotlin.mvp.presenter;

import android.app.Application;

import com.blankj.utilcode.util.SPUtils;
import com.haife.app.nobles.spirits.kotlin.app.base.BaseResponse;
import com.haife.app.nobles.spirits.kotlin.app.constant.SPConstant;
import com.haife.app.nobles.spirits.kotlin.app.utils.RxUtils;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.MainContract;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.LoginInfoBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MyGroup;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.LogUtils;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


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
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
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

    public void loginInfo() {
        LogUtils.debugInfo("111测试uid"+SPUtils.getInstance().getLong(SPConstant.UID));
        mModel.loginInfo(SPUtils.getInstance().getLong(SPConstant.UID),SPUtils.getInstance().getString(SPConstant.SID))
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<LoginInfoBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<LoginInfoBean> aboutBeanBaseResponse) {
                        if (aboutBeanBaseResponse.isSuccess()) {

                            mRootView.loginInfoSuccess(aboutBeanBaseResponse.getData());
                        } else {

                            mRootView.showMessage(aboutBeanBaseResponse.getMessage());
                        }
                    }
                });
    }

    public void addFriend(long uid, String remark) {

        mModel.addFriend(SPConstant.MYUID, SPConstant.MYSID, uid, remark)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse aboutBeanBaseResponse) {
                        if (aboutBeanBaseResponse.isSuccess()) {

                            mRootView.addFriendSuccess();
                        } else {

                            mRootView.showMessage(aboutBeanBaseResponse.getMessage());
                        }
                    }
                });
    }

    public void addGroup(long uid) {

        mModel.addGroup(SPConstant.MYUID, SPConstant.MYSID, uid)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<MyGroup>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<MyGroup> aboutBeanBaseResponse) {
                        if (aboutBeanBaseResponse.isSuccess()) {

                            mRootView.addGroupSuccess(aboutBeanBaseResponse.getData());
                        } else {

                            mRootView.showMessage(aboutBeanBaseResponse.getMessage());
                        }
                    }
                });
    }
}
