package com.lianxi.dingtu.newsnfc.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lianxi.dingtu.newsnfc.mvp.contract.StepTwoContract;
import com.lianxi.dingtu.newsnfc.mvp.model.api.UserService;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.CardTypeTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.SubsidyTo;

import java.util.List;

import io.reactivex.Observable;


@ActivityScope
public class StepTwoModel extends BaseModel implements StepTwoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public StepTwoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override public Observable<BaseResponse<List<SubsidyTo>>> getSubsidyLeve() {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getSubsidyLeve();
    }

    @Override public Observable<BaseResponse<List<CardTypeTo>>> getCardType() {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getCardType();
    }

    @Override public Observable<BaseResponse<Double>> getDonate(int cardType, double amount) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getDonate(cardType, amount);
    }

    @Override public Observable<BaseResponse<String>> getPayKeySwitch(String key) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getPayKeySwitch(key);
    }

}