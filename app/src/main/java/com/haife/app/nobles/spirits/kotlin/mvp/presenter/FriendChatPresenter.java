package com.haife.app.nobles.spirits.kotlin.mvp.presenter;

import android.app.Application;

import com.google.gson.Gson;
import com.haife.app.nobles.spirits.kotlin.app.base.BaseResponse;
import com.haife.app.nobles.spirits.kotlin.app.utils.RxUtils;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.FriendChatContract;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.LoginBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MessageBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.R_FriendMsgBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.R_loginBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.ReadOtherInfoBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.FormBody;
import okhttp3.MediaType;
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
public class FriendChatPresenter extends BasePresenter<FriendChatContract.Model, FriendChatContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public FriendChatPresenter(FriendChatContract.Model model, FriendChatContract.View rootView) {
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

    public void friendList(int page, int limit, int senderUid) {

        mModel.friendMsgList(page, limit, senderUid)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<MessageBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<MessageBean>> aboutBeanBaseResponse) {
                        if (aboutBeanBaseResponse.isSuccess()) {

                            mRootView.friendMsgListSuccess(aboutBeanBaseResponse.getData());
                        } else {

                            mRootView.showMessage(aboutBeanBaseResponse.getMessage());
                        }
                    }
                });
    }


    public void sendMsg(String content,int type,int id) {
        R_FriendMsgBean userInfoBean = new R_FriendMsgBean(content,type,id);
        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(userInfoBean));
        mModel.sendMsg(body)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse aboutBeanBaseResponse) {
                        if (aboutBeanBaseResponse.isSuccess()) {
                            mRootView.sendMsgtSuccess( type,content);
                        } else {

                            mRootView.showMessage(aboutBeanBaseResponse.getMessage());
                        }
                    }
                });
    }

    public void read(int uid) {

        mModel.read(uid)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<ReadOtherInfoBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<ReadOtherInfoBean> aboutBeanBaseResponse) {
                        if (aboutBeanBaseResponse.isSuccess()) {

                            mRootView.readSuccess(aboutBeanBaseResponse.getData());
                        } else {

                            mRootView.showMessage(aboutBeanBaseResponse.getMessage());
                        }
                    }
                });
    }
}
