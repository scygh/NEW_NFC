package com.lianxi.dingtu.newsnfc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.contract.TodayContract;
import com.lianxi.dingtu.newsnfc.mvp.model.TodayModel;

import dagger.Module;
import dagger.Provides;


@Module
public class TodayModule {
    private TodayContract.View view;

    /**
     * 构建TodayModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public TodayModule(TodayContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    TodayContract.View provideTodayView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    TodayContract.Model provideTodayModel(TodayModel model) {
        return model;
    }
}