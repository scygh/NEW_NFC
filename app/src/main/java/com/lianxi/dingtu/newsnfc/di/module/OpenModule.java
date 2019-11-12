package com.lianxi.dingtu.newsnfc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.contract.OpenContract;
import com.lianxi.dingtu.newsnfc.mvp.model.OpenModel;

import dagger.Module;
import dagger.Provides;


@Module
public class OpenModule {
    private OpenContract.View view;

    /**
     * 构建OpenModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public OpenModule(OpenContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OpenContract.View provideOpenView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OpenContract.Model provideOpenModel(OpenModel model) {
        return model;
    }
}