package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.di.module.DepositModule;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.DepositActivity;

import dagger.Component;

@ActivityScope
@Component(modules = DepositModule.class, dependencies = AppComponent.class)
public interface DepositComponent {
    void inject(DepositActivity activity);
}