package com.lianxi.dingtu.newsnfc.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lianxi.dingtu.newsnfc.mvp.contract.TodayContract;
import com.lianxi.dingtu.newsnfc.mvp.model.api.UserService;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.AggregateTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;

import java.util.List;

import io.reactivex.Observable;


@ActivityScope
public class TodayModel extends BaseModel implements TodayContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public TodayModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
    @Override public Observable<BaseResponse<List<AggregateTo>>> getAggregateTo(String startTime, String endTime) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getAggregateTo(startTime, endTime);
    }
}