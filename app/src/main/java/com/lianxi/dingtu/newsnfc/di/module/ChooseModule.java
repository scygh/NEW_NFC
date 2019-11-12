package com.lianxi.dingtu.newsnfc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.contract.ChooseContract;
import com.lianxi.dingtu.newsnfc.mvp.model.ChooseModel;

import dagger.Module;
import dagger.Provides;


@Module
public class ChooseModule {
    private ChooseContract.View view;

    /**
     * 构建ChooseModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ChooseModule(ChooseContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ChooseContract.View provideChooseView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ChooseContract.Model provideChooseModel(ChooseModel model) {
        return model;
    }
}