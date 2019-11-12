package com.lianxi.dingtu.newsnfc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.contract.NoCardContract;
import com.lianxi.dingtu.newsnfc.mvp.model.NoCardModel;

import dagger.Module;
import dagger.Provides;


@Module
public class NoCardModule {
    private NoCardContract.View view;

    /**
     * 构建NoCardModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public NoCardModule(NoCardContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    NoCardContract.View provideNoCardView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    NoCardContract.Model provideNoCardModel(NoCardModel model) {
        return model;
    }
}