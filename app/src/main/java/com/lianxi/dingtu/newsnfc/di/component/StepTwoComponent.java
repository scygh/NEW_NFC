package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.di.module.StepTwoModule;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.StepTwoActivity;

import dagger.Component;

@ActivityScope
@Component(modules = StepTwoModule.class, dependencies = AppComponent.class)
public interface StepTwoComponent {
    void inject(StepTwoActivity activity);
}