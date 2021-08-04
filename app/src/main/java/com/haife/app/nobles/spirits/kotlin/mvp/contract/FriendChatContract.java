package com.haife.app.nobles.spirits.kotlin.mvp.contract;

import com.haife.app.nobles.spirits.kotlin.app.base.BaseResponse;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.FriendBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MessageBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.ReadOtherInfoBean;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

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
public interface FriendChatContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void friendMsgListSuccess(List<MessageBean> data);

        void sendMsgtSuccess(int type,String content);

        void readSuccess(ReadOtherInfoBean data);

        void deleteFriendSuccess();

        void uploadSuccess(String data);

        void clearfriendMsgSuccess();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<List<MessageBean>>> friendMsgList (int page, int limit,long senderUid );

        Observable<BaseResponse> sendMsg (RequestBody body);

        Observable<BaseResponse<ReadOtherInfoBean>> read (long uid);

        Observable<BaseResponse> deleteFriend (RequestBody body);

        Observable<BaseResponse<String>> upload (List<MultipartBody.Part> parts);


        Observable<BaseResponse> clearfriendMsg(RequestBody body );
    }
}
