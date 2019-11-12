package com.lianxi.dingtu.newsnfc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.contract.CloseContract;
import com.lianxi.dingtu.newsnfc.mvp.model.CloseModel;

import dagger.Module;
import dagger.Provides;


@Module
public class CloseModule {
    private CloseContract.View view;

    /**
     * 构建CloseModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CloseModule(CloseContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CloseContract.View provideCloseView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CloseContract.Model provideCloseModel(CloseModel model) {
        return model;
    }
}