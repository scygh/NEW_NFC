package com.lianxi.dingtu.newsnfc.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lianxi.dingtu.newsnfc.mvp.contract.StepThreeContract;
import com.lianxi.dingtu.newsnfc.mvp.model.api.UserService;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.DepartmentTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.RegisterParam;

import java.util.List;

import io.reactivex.Observable;


@ActivityScope
public class StepThreeModel extends BaseModel implements StepThreeContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public StepThreeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override public Observable<BaseResponse<List<DepartmentTo>>> getDepartment() {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getDepartment();
    }

    @Override public Observable<BaseResponse<Integer>> getNextNumber() {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getNextNumber();
    }

    @Override public Observable<BaseResponse> addRegister(RegisterParam param) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).addRegister(param);
    }

}