package com.lianxi.dingtu.newsnfc.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.lianxi.dingtu.newsnfc.mvp.contract.WarenverbrauchContract;
import com.lianxi.dingtu.newsnfc.mvp.model.api.UserService;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.CardInfoTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.GetDetailList;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.GetEMGoods;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.KeySwitchTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.ReadCardTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.SimpleExpenseParam;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.SimpleExpenseTo;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class WarenverbrauchModel extends BaseModel implements WarenverbrauchContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public WarenverbrauchModel(IRepositoryManager repositoryManager) {
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

    @Override public Observable<BaseResponse<SimpleExpenseTo>> createSimpleExpense(SimpleExpenseParam param) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).createSimpleExpense(param);
    }

    @Override public Observable<BaseResponse<String>> getPayKeySwitch(String key) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getPayKeySwitch(key);
    }

    @Override public Observable<BaseResponse<KeySwitchTo>> getEMDevice(int id) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getEMDevice(id);
    }

    @Override public Observable<BaseResponse<ReadCardTo>> addReadCard(int companyCode, int deviceID, int number) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).addReadCard(companyCode, deviceID, number);
    }

    @Override
    public Observable<GetEMGoods> getEmGoods() {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getEMGoods("1");
    }

    @Override
    public Observable<GetDetailList> getEmGoodsdetail(int index, int pagesize, String type) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getEMGoodsDetail(index,pagesize,type);
    }
}