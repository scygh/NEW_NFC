package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.di.module.ImageModule;
import com.lianxi.dingtu.newsnfc.mvp.contract.ImageContract;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.ImageActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/17/2019 15:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ImageModule.class, dependencies = AppComponent.class)
public interface ImageComponent {
    void inject(ImageActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ImageComponent.Builder view(ImageContract.View view);

        ImageComponent.Builder appComponent(AppComponent appComponent);

        ImageComponent build();
    }
}