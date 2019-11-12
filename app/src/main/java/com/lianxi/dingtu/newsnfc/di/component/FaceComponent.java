package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.di.module.FaceModule;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.FaceActivity;

import dagger.Component;

@ActivityScope
@Component(modules = FaceModule.class, dependencies = AppComponent.class)
public interface FaceComponent {
    void inject(FaceActivity activity);
}