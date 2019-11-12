package com.lianxi.dingtu.newsnfc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.contract.WarenverbrauchContract;
import com.lianxi.dingtu.newsnfc.mvp.model.WarenverbrauchModel;

import dagger.Module;
import dagger.Provides;


@Module
public class WarenverbrauchModule {
    private WarenverbrauchContract.View view;

    /**
     * 构建AutoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public WarenverbrauchModule(WarenverbrauchContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WarenverbrauchContract.View provideAutoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    WarenverbrauchContract.Model provideAutoModel(WarenverbrauchModel model) {
        return model;
    }
}