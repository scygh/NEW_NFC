package com.lianxi.dingtu.newsnfc.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.lianxi.dingtu.newsnfc.mvp.contract.HomeContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaiduTokenTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseJson;

import io.reactivex.Observable;


@FragmentScope
public class HomeModel extends BaseModel implements HomeContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

//    @Override public Observable<BaseJson<BaiduTokenTo>> getBaiduToken() {
//        return mRepositoryManager.obtainRetrofitService(BaiduService.class).getBaiduToken();
//    }
}