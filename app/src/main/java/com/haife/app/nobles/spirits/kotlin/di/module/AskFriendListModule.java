package com.haife.app.nobles.spirits.kotlin.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.haife.app.nobles.spirits.kotlin.mvp.contract.AskFriendListContract;
import com.haife.app.nobles.spirits.kotlin.mvp.model.AskFriendListModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/25/2021 09:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class AskFriendListModule {

    @Binds
    abstract AskFriendListContract.Model bindAskFriendListModel(AskFriendListModel model);
}