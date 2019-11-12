package com.lianxi.dingtu.newsnfc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.contract.StepTwoContract;
import com.lianxi.dingtu.newsnfc.mvp.model.StepTwoModel;

import dagger.Module;
import dagger.Provides;


@Module
public class StepTwoModule {
    private StepTwoContract.View view;

    /**
     * 构建StepTwoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public StepTwoModule(StepTwoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    StepTwoContract.View provideStepTwoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    StepTwoContract.Model provideStepTwoModel(StepTwoModel model) {
        return model;
    }
}