package com.haife.app.nobles.spirits.kotlin.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haife.app.nobles.spirits.kotlin.di.module.RegeisterModule;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.RegeisterContract;

import com.jess.arms.di.scope.ActivityScope;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.activity.RegeisterActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/22/2021 13:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = RegeisterModule.class, dependencies = AppComponent.class)
public interface RegeisterComponent {
    void inject(RegeisterActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        RegeisterComponent.Builder view(RegeisterContract.View view);

        RegeisterComponent.Builder appComponent(AppComponent appComponent);

        RegeisterComponent build();
    }
}