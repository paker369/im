package com.haife.app.nobles.spirits.kotlin.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haife.app.nobles.spirits.kotlin.di.module.FriendAskListModule;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.FriendAskListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.activity.FriendAskListActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/22/2021 14:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = FriendAskListModule.class, dependencies = AppComponent.class)
public interface FriendAskListComponent {
    void inject(FriendAskListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FriendAskListComponent.Builder view(FriendAskListContract.View view);

        FriendAskListComponent.Builder appComponent(AppComponent appComponent);

        FriendAskListComponent build();
    }
}