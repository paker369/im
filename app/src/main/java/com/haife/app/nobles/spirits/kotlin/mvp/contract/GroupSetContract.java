package com.haife.app.nobles.spirits.kotlin.mvp.contract;

import com.haife.app.nobles.spirits.kotlin.app.base.BaseResponse;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.R_UpdateGroup;
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
 * Created by MVPArmsTemplate on 06/09/2021 16:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface GroupSetContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void deleteGroupSuccess();
        void deleteMyGroupSuccess();

        void groupUpdateSuccess( R_UpdateGroup userInfoBean);

        void uploadSuccess(String data);

        void uploadAvatarSuccess(String data);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse> deleteMyGroup (long groupid);
        Observable<BaseResponse> deleteGroup (long groupid);

        Observable<BaseResponse> groupUpdate ( RequestBody body);
        Observable<BaseResponse<String>> upload (List<MultipartBody.Part> parts);
        Observable<BaseResponse<String>> uploadAvatar (List<MultipartBody.Part> parts);

    }
}
