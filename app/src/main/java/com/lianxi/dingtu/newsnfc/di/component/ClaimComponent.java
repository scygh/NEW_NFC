package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.di.module.ClaimModule;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.ClaimActivity;

import dagger.Component;

@ActivityScope
@Component(modules = ClaimModule.class, dependencies = AppComponent.class)
public interface ClaimComponent {
    void inject(ClaimActivity activity);
}