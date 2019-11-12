package com.lianxi.dingtu.newsnfc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.contract.QRDepositContract;
import com.lianxi.dingtu.newsnfc.mvp.model.QRDepositModel;

import dagger.Module;
import dagger.Provides;


@Module
public class QRDepositModule {
    private QRDepositContract.View view;

    /**
     * 构建QRDepositModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public QRDepositModule(QRDepositContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    QRDepositContract.View provideQRDepositView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    QRDepositContract.Model provideQRDepositModel(QRDepositModel model) {
        return model;
    }
}