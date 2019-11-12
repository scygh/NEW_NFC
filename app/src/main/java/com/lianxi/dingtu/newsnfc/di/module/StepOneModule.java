package com.lianxi.dingtu.newsnfc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.contract.StepOneContract;
import com.lianxi.dingtu.newsnfc.mvp.model.StepOneModel;

import dagger.Module;
import dagger.Provides;


@Module
public class StepOneModule {
    private StepOneContract.View view;

    /**
     * 构建StepOneModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public StepOneModule(StepOneContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    StepOneContract.View provideStepOneView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    StepOneContract.Model provideStepOneModel(StepOneModel model) {
        return model;
    }
}