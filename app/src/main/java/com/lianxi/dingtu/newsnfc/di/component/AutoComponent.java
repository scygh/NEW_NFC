package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.di.module.AutoModule;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.AutoActivity;

import dagger.Component;

@ActivityScope
@Component(modules = AutoModule.class, dependencies = AppComponent.class)
public interface AutoComponent {
    void inject(AutoActivity activity);
}