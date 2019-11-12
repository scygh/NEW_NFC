package com.lianxi.dingtu.newsnfc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.contract.ChoiceContract;
import com.lianxi.dingtu.newsnfc.mvp.model.ChoiceModel;

import dagger.Module;
import dagger.Provides;


@Module
public class ChoiceModule {
    private ChoiceContract.View view;

    /**
     * 构建ChoiceModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ChoiceModule(ChoiceContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ChoiceContract.View provideChoiceView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ChoiceContract.Model provideChoiceModel(ChoiceModel model) {
        return model;
    }
}