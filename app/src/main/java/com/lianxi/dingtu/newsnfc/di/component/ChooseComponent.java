package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.di.module.ChooseModule;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.ChooseActivity;

import dagger.Component;

@ActivityScope
@Component(modules = ChooseModule.class, dependencies = AppComponent.class)
public interface ChooseComponent {
    void inject(ChooseActivity activity);
}