package com.lianxi.dingtu.newsnfc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.contract.DepositContract;
import com.lianxi.dingtu.newsnfc.mvp.model.DepositModel;

import dagger.Module;
import dagger.Provides;


@Module
public class DepositModule {
    private DepositContract.View view;

    /**
     * 构建DepositModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public DepositModule(DepositContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    DepositContract.View provideDepositView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    DepositContract.Model provideDepositModel(DepositModel model) {
        return model;
    }
}