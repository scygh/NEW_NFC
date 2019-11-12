package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.di.module.StepOneModule;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.StepOneActivity;

import dagger.Component;

@ActivityScope
@Component(modules = StepOneModule.class, dependencies = AppComponent.class)
public interface StepOneComponent {
    void inject(StepOneActivity activity);
}