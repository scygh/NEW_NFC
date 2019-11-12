package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.di.module.RefundModule;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.RefundActivity;

import dagger.Component;

@ActivityScope
@Component(modules = RefundModule.class, dependencies = AppComponent.class)
public interface RefundComponent {
    void inject(RefundActivity activity);
}