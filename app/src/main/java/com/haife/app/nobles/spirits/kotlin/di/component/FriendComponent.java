package com.haife.app.nobles.spirits.kotlin.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haife.app.nobles.spirits.kotlin.di.module.FriendModule;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.FriendContract;

import com.jess.arms.di.scope.FragmentScope;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.fragment.FriendFragment;


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
@Component(modules = FriendModule.class, dependencies = AppComponent.class)
public interface FriendComponent {
    void inject(FriendFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FriendComponent.Builder view(FriendContract.View view);

        FriendComponent.Builder appComponent(AppComponent appComponent);

        FriendComponent build();
    }
}