package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.di.module.RechargeModule;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.RechargeActivity;

import dagger.Component;

@ActivityScope
@Component(modules = RechargeModule.class, dependencies = AppComponent.class)
public interface RechargeComponent {
    void inject(RechargeActivity activity);
}