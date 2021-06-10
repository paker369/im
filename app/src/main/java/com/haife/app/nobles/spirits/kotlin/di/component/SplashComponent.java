package com.haife.app.nobles.spirits.kotlin.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haife.app.nobles.spirits.kotlin.di.module.SplashModule;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.SplashContract;

import com.jess.arms.di.scope.ActivityScope;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.activity.SplashActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/01/2021 19:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SplashModule.class, dependencies = AppComponent.class)
public interface SplashComponent {
    void inject(SplashActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SplashComponent.Builder view(SplashContract.View view);

        SplashComponent.Builder appComponent(AppComponent appComponent);

        SplashComponent build();
    }
}