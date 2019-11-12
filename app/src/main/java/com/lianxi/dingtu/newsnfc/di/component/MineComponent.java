package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.lianxi.dingtu.newsnfc.di.module.MineModule;
import com.lianxi.dingtu.newsnfc.mvp.ui.fragment.MineFragment;

import dagger.Component;

@FragmentScope
@Component(modules = MineModule.class, dependencies = AppComponent.class)
public interface MineComponent {
    void inject(MineFragment fragment);
}