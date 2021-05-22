package com.haife.app.nobles.spirits.kotlin.mvp.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.haife.app.nobles.spirits.kotlin.mvp.contract.MainContract;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.activity.MainActivity;
import com.jess.arms.di.component.AppComponent;

import com.haife.app.nobles.spirits.kotlin.mvp.di.module.MainModule;


import com.jess.arms.di.scope.ActivityScope;



/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/22/2021 11:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        MainComponent.Builder view(MainContract.View view);
        MainComponent.Builder appComponent(AppComponent appComponent);
        MainComponent build();
    }
}