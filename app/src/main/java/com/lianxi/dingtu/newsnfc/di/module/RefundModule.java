package com.lianxi.dingtu.newsnfc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.contract.RefundContract;
import com.lianxi.dingtu.newsnfc.mvp.model.RefundModel;

import dagger.Module;
import dagger.Provides;


@Module
public class RefundModule {
    private RefundContract.View view;

    /**
     * 构建RefundModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public RefundModule(RefundContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RefundContract.View provideRefundView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    RefundContract.Model provideRefundModel(RefundModel model) {
        return model;
    }
}