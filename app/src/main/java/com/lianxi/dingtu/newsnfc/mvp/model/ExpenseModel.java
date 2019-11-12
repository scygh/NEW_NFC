package com.lianxi.dingtu.newsnfc.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lianxi.dingtu.newsnfc.mvp.contract.ExpenseContract;
import com.lianxi.dingtu.newsnfc.mvp.model.api.UserService;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.DepositTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.ExpenseReportTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.ExpenseTo;

import java.util.List;

import io.reactivex.Observable;


@ActivityScope
public class ExpenseModel extends BaseModel implements ExpenseContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ExpenseModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override public Observable<BaseResponse<ExpenseTo>> getExpenseReport(int pageIndex, int pageSize, String orderColumn, String orderPattern) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getExpenseReport(pageIndex, pageSize, orderColumn, orderPattern);
    }
}