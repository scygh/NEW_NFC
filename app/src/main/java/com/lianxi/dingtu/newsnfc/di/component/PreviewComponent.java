package com.lianxi.dingtu.newsnfc.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lianxi.dingtu.newsnfc.di.module.PreviewModule;
import com.lianxi.dingtu.newsnfc.mvp.contract.PreviewContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.PreviewActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/24/2019 17:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PreviewModule.class, dependencies = AppComponent.class)
public interface PreviewComponent {
    void inject(PreviewActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PreviewComponent.Builder view(PreviewContract.View view);

        PreviewComponent.Builder appComponent(AppComponent appComponent);

        PreviewComponent build();
    }
}