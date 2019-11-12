package com.lianxi.dingtu.newsnfc.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lianxi.dingtu.newsnfc.mvp.contract.RechargeContract;
import com.lianxi.dingtu.newsnfc.mvp.model.api.UserService;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.CardInfoTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.MoneyParam;

import io.reactivex.Observable;


@ActivityScope
public class RechargeModel extends BaseModel implements RechargeContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public RechargeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override public Observable<BaseResponse<CardInfoTo>> getByNumber(int number) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getByNumber(number);
    }

    @Override public Observable<BaseResponse<Double>> getDonate(int cardType, double amount) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getDonate(cardType, amount);
    }

    @Override public Observable<BaseResponse> addDeposit(MoneyParam param) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).addDeposit(param);
    }
}