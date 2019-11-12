package com.lianxi.dingtu.newsnfc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.contract.ManualContract;
import com.lianxi.dingtu.newsnfc.mvp.model.ManualModel;

import dagger.Module;
import dagger.Provides;


@Module
public class ManualModule {
    private ManualContract.View view;

    /**
     * 构建ManualModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ManualModule(ManualContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ManualContract.View provideManualView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ManualContract.Model provideManualModel(ManualModel model) {
        return model;
    }
}