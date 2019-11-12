package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.di.module.PaymentModule;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.PaymentActivity;

import dagger.Component;

@ActivityScope
@Component(modules = PaymentModule.class, dependencies = AppComponent.class)
public interface PaymentComponent {
    void inject(PaymentActivity activity);
}