package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.lianxi.dingtu.newsnfc.di.module.HomeModule;
import com.lianxi.dingtu.newsnfc.mvp.ui.fragment.HomeFragment;

import dagger.Component;

@FragmentScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeFragment fragment);
}