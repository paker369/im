package com.haife.app.nobles.spirits.kotlin.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haife.app.nobles.spirits.kotlin.di.module.EditMyGroupCardModule;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.EditMyGroupCardContract;

import com.jess.arms.di.scope.ActivityScope;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.activity.EditMyGroupCardActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/22/2021 14:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = EditMyGroupCardModule.class, dependencies = AppComponent.class)
public interface EditMyGroupCardComponent {
    void inject(EditMyGroupCardActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EditMyGroupCardComponent.Builder view(EditMyGroupCardContract.View view);

        EditMyGroupCardComponent.Builder appComponent(AppComponent appComponent);

        EditMyGroupCardComponent build();
    }
}