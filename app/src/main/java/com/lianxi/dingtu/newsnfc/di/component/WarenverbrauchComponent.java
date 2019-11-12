package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.di.module.WarenverbrauchModule;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.WarenverbrauchActivity;

import dagger.Component;

@ActivityScope
@Component(modules = WarenverbrauchModule.class, dependencies = AppComponent.class)
public interface WarenverbrauchComponent {
    void inject(WarenverbrauchActivity activity);
}