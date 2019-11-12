package com.lianxi.dingtu.newsnfc.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lianxi.dingtu.newsnfc.di.module.CaptureModule;
import com.lianxi.dingtu.newsnfc.mvp.contract.CaptureContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.CaptureActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/28/2019 13:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CaptureModule.class, dependencies = AppComponent.class)
public interface CaptureComponent {
    void inject(CaptureActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CaptureComponent.Builder view(CaptureContract.View view);

        CaptureComponent.Builder appComponent(AppComponent appComponent);

        CaptureComponent build();
    }
}