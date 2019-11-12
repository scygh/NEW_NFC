package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.di.module.TodayModule;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.TodayActivity;

import dagger.Component;

@ActivityScope
@Component(modules = TodayModule.class, dependencies = AppComponent.class)
public interface TodayComponent {
    void inject(TodayActivity activity);
}