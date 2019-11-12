package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.di.module.CorrectModule;
import com.lianxi.dingtu.newsnfc.mvp.contract.CorrectContract;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.CorrectActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/18/2019 17:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CorrectModule.class, dependencies = AppComponent.class)
public interface CorrectComponent {
    void inject(CorrectActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CorrectComponent.Builder view(CorrectContract.View view);

        CorrectComponent.Builder appComponent(AppComponent appComponent);

        CorrectComponent build();
    }
}