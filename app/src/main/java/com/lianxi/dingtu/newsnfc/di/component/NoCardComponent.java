package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.di.module.NoCardModule;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.NoCardActivity;

import dagger.Component;

@ActivityScope
@Component(modules = NoCardModule.class, dependencies = AppComponent.class)
public interface NoCardComponent {
    void inject(NoCardActivity activity);
}