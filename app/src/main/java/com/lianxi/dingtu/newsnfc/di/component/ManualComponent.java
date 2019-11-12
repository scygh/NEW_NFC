package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.di.module.ManualModule;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.ManualActivity;

import dagger.Component;

@ActivityScope
@Component(modules = ManualModule.class, dependencies = AppComponent.class)
public interface ManualComponent {
    void inject(ManualActivity activity);
}