package com.lianxi.dingtu.newsnfc.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.di.module.ExpenseModule;
import com.lianxi.dingtu.newsnfc.mvp.ui.activity.ExpenseActivity;

import dagger.Component;

@ActivityScope
@Component(modules = ExpenseModule.class, dependencies = AppComponent.class)
public interface ExpenseComponent {
    void inject(ExpenseActivity activity);
}