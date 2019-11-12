package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.di.module.OpenModule;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.OpenActivity;

import dagger.Component;

@ActivityScope
@Component(modules = OpenModule.class, dependencies = AppComponent.class)
public interface OpenComponent {
    void inject(OpenActivity activity);
}