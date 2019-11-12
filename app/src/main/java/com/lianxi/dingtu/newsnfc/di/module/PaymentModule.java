package com.lianxi.dingtu.newsnfc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.contract.PaymentContract;
import com.lianxi.dingtu.newsnfc.mvp.model.PaymentModel;

import dagger.Module;
import dagger.Provides;


@Module
public class PaymentModule {
    private PaymentContract.View view;

    /**
     * 构建PaymentModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PaymentModule(PaymentContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PaymentContract.View providePaymentView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PaymentContract.Model providePaymentModel(PaymentModel model) {
        return model;
    }
}