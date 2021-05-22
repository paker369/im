package com.haife.app.nobles.spirits.kotlin.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haife.app.nobles.spirits.kotlin.di.module.GroupChatModule;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.GroupChatContract;

import com.jess.arms.di.scope.ActivityScope;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.activity.GroupChatActivity;


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
@Component(modules = GroupChatModule.class, dependencies = AppComponent.class)
public interface GroupChatComponent {
    void inject(GroupChatActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GroupChatComponent.Builder view(GroupChatContract.View view);

        GroupChatComponent.Builder appComponent(AppComponent appComponent);

        GroupChatComponent build();
    }
}