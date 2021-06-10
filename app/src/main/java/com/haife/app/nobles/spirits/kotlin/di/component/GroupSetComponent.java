package com.haife.app.nobles.spirits.kotlin.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haife.app.nobles.spirits.kotlin.di.module.GroupSetModule;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.GroupSetContract;

import com.jess.arms.di.scope.ActivityScope;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.activity.GroupSetActivity;


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
@ActivityScope
@Component(modules = GroupSetModule.class, dependencies = AppComponent.class)
public interface GroupSetComponent {
    void inject(GroupSetActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GroupSetComponent.Builder view(GroupSetContract.View view);

        GroupSetComponent.Builder appComponent(AppComponent appComponent);

        GroupSetComponent build();
    }
}