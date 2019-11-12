package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.di.module.StepThreeModule;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.StepThreeActivity;

import dagger.Component;

@ActivityScope
@Component(modules = StepThreeModule.class, dependencies = AppComponent.class)
public interface StepThreeComponent {
    void inject(StepThreeActivity activity);
}