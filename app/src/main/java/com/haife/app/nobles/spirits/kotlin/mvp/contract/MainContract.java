package com.haife.app.nobles.spirits.kotlin.mvp.contract;

import com.haife.app.nobles.spirits.kotlin.app.base.BaseResponse;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.ChangeInfoBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.LoginInfoBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MyGroup;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.VersionBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

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
public interface MainContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loginInfoSuccess(LoginInfoBean data);

        void addFriendSuccess();

        void addGroupSuccess(MyGroup data);
        void changeinfoSuccess(ChangeInfoBean data );
        void uploadAvatarSuccess(String data);
        void getInvitationCodeSuccess();
        void getversionSuccess(VersionBean data);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<LoginInfoBean>> loginInfo(long uId,  String sId);

        Observable<BaseResponse> addFriend(long uId, String sId, long friendUid,  String remark);

        Observable<BaseResponse<MyGroup>> addGroup(long uId, String sId,long groupId);
        Observable<BaseResponse<ChangeInfoBean>> changeinfo(RequestBody body);

        Observable<BaseResponse<String>> uploadAvatar (List<MultipartBody.Part> parts);

        Observable<BaseResponse> getInvitationCode();

        Observable<BaseResponse<VersionBean>> getversion(int type, String version);



    }
}
