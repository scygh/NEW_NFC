package com.lianxi.dingtu.newsnfc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.contract.FaceContract;
import com.lianxi.dingtu.newsnfc.mvp.model.FaceModel;

import dagger.Module;
import dagger.Provides;


@Module
public class FaceModule {
    private FaceContract.View view;

    /**
     * 构建FaceModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public FaceModule(FaceContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FaceContract.View provideFaceView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    FaceContract.Model provideFaceModel(FaceModel model) {
        return model;
    }
}