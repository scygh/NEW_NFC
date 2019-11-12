package com.lianxi.dingtu.newsnfc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.contract.StepThreeContract;
import com.lianxi.dingtu.newsnfc.mvp.model.StepThreeModel;

import dagger.Module;
import dagger.Provides;


@Module
public class StepThreeModule {
    private StepThreeContract.View view;

    /**
     * 构建StepThreeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public StepThreeModule(StepThreeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    StepThreeContract.View provideStepThreeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    StepThreeContract.Model provideStepThreeModel(StepThreeModel model) {
        return model;
    }
}