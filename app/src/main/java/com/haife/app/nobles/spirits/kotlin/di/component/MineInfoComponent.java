package com.haife.app.nobles.spirits.kotlin.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.haife.app.nobles.spirits.kotlin.di.module.MineInfoModule;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.MineInfoContract;

import com.jess.arms.di.scope.ActivityScope;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.activity.MineInfoActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/10/2021 13:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MineInfoModule.class, dependencies = AppComponent.class)
public interface MineInfoComponent {
    void inject(MineInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MineInfoComponent.Builder view(MineInfoContract.View view);

        MineInfoComponent.Builder appComponent(AppComponent appComponent);

        MineInfoComponent build();
    }
}