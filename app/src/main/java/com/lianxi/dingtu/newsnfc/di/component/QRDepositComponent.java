package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.di.module.QRDepositModule;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.QRDepositActivity;

import dagger.Component;

@ActivityScope
@Component(modules = QRDepositModule.class, dependencies = AppComponent.class)
public interface QRDepositComponent {
    void inject(QRDepositActivity activity);
}