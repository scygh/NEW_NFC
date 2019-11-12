package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.di.module.ChoiceModule;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.ChoiceActivity;

import dagger.Component;

@ActivityScope
@Component(modules = ChoiceModule.class, dependencies = AppComponent.class)
public interface ChoiceComponent {
    void inject(ChoiceActivity activity);
}