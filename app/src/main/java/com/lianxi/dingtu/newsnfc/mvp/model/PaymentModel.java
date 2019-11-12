package com.lianxi.dingtu.newsnfc.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.lianxi.dingtu.newsnfc.mvp.contract.PaymentContract;
import com.lianxi.dingtu.newsnfc.mvp.model.api.UserService;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.QRExpenseParam;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.QRExpenseTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.QRReadTo;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class PaymentModel extends BaseModel implements PaymentContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public PaymentModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override public Observable<BaseResponse<QRReadTo>> getQRRead(String qrCodeContent, int deviceID) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getQRRead(qrCodeContent, deviceID);
    }

    @Override public Observable<BaseResponse<QRExpenseTo>> addQRExpense(QRExpenseParam param) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).addQRExpense(param);
    }
}