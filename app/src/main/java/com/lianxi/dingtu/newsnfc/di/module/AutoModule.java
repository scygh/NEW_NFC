package com.lianxi.dingtu.newsnfc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.contract.AutoContract;
import com.lianxi.dingtu.newsnfc.mvp.model.AutoModel;

import dagger.Module;
import dagger.Provides;


@Module
public class AutoModule {
    private AutoContract.View view;

    /**
     * 构建AutoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public AutoModule(AutoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    AutoContract.View provideAutoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    AutoContract.Model provideAutoModel(AutoModel model) {
        return model;
    }
}