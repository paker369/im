package com.haife.app.nobles.spirits.kotlin.mvp.presenter;

import android.app.Application;

import com.google.gson.Gson;
import com.haife.app.nobles.spirits.kotlin.app.base.BaseResponse;
import com.haife.app.nobles.spirits.kotlin.app.constant.SPConstant;
import com.haife.app.nobles.spirits.kotlin.app.utils.RxUtils;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MyGroup;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.R_AddGroupBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.R_SimpleBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.haife.app.nobles.spirits.kotlin.mvp.contract.GroupContract;

import java.util.List;


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
public class GroupPresenter extends BasePresenter<GroupContract.Model, GroupContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public GroupPresenter(GroupContract.Model model, GroupContract.View rootView) {
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
    public void myGroupList(int page,int limit) {
        mModel.myGroupList(page, limit)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<MyGroup>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<MyGroup>> aboutBeanBaseResponse) {
                        if (aboutBeanBaseResponse.isSuccess()) {

                            mRootView.myGroupListSuccess(aboutBeanBaseResponse.getData());
                        } else {

                            mRootView.showMessage(aboutBeanBaseResponse.getMessage());
                        }
                    }
                });
    }
}
