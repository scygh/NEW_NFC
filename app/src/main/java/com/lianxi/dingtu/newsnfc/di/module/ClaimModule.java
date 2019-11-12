package com.lianxi.dingtu.newsnfc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.contract.ClaimContract;
import com.lianxi.dingtu.newsnfc.mvp.model.ClaimModel;

import dagger.Module;
import dagger.Provides;


@Module
public class ClaimModule {
    private ClaimContract.View view;

    /**
     * 构建ClaimModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ClaimModule(ClaimContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ClaimContract.View provideClaimView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ClaimContract.Model provideClaimModel(ClaimModel model) {
        return model;
    }
}