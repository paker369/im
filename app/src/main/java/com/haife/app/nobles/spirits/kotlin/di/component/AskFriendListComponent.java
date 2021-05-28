package com.haife.app.nobles.spirits.kotlin.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haife.app.nobles.spirits.kotlin.di.module.AskFriendListModule;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.AskFriendListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.activity.AskFriendListActivity;


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
@ActivityScope
@Component(modules = AskFriendListModule.class, dependencies = AppComponent.class)
public interface AskFriendListComponent {
    void inject(AskFriendListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AskFriendListComponent.Builder view(AskFriendListContract.View view);

        AskFriendListComponent.Builder appComponent(AppComponent appComponent);

        AskFriendListComponent build();
    }
}