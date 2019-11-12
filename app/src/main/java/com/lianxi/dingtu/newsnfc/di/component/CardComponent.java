package com.lianxi.dingtu.newsnfc.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lianxi.dingtu.newsnfc.di.module.CardModule;
import com.lianxi.dingtu.newsnfc.mvp.contract.CardContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.CardActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/22/2019 13:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CardModule.class, dependencies = AppComponent.class)
public interface CardComponent {
    void inject(CardActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CardComponent.Builder view(CardContract.View view);

        CardComponent.Builder appComponent(AppComponent appComponent);

        CardComponent build();
    }
}