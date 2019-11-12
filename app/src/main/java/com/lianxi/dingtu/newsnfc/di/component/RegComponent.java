package com.lianxi.dingtu.newsnfc.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lianxi.dingtu.newsnfc.di.module.RegModule;
import com.lianxi.dingtu.newsnfc.mvp.contract.RegContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.RegActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/12/2019 10:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = RegModule.class, dependencies = AppComponent.class)
public interface RegComponent {
    void inject(RegActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        RegComponent.Builder view(RegContract.View view);

        RegComponent.Builder appComponent(AppComponent appComponent);

        RegComponent build();
    }
}