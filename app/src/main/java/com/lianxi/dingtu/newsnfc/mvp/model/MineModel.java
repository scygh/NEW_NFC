package com.lianxi.dingtu.newsnfc.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.lianxi.dingtu.newsnfc.mvp.contract.MineContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.TokenTo;

import io.reactivex.Observable;


@FragmentScope
public class MineModel extends BaseModel implements MineContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MineModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

//    @Override public Observable<TokenTo> getToken(String grant_type, String client_id, String client_secret) {
//        return mRepositoryManager.obtainRetrofitService(DishService.class).getToken(grant_type, client_id, client_secret);
//    }
}