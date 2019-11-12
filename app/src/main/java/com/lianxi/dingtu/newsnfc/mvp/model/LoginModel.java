package com.lianxi.dingtu.newsnfc.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lianxi.dingtu.newsnfc.mvp.contract.LoginContract;
import com.lianxi.dingtu.newsnfc.mvp.model.api.UserService;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.DishTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.SearchTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.TokenTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.UserInfoTo;

import io.reactivex.Observable;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

import static com.lianxi.dingtu.newsnfc.mvp.model.api.Api.BAIDU_DOMAIN;
import static com.lianxi.dingtu.newsnfc.mvp.model.api.Api.BAIDU_DOMAIN_NAME;


@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override public Observable<BaseResponse<UserInfoTo>> login(String account, String password, String oldAccessToken) {
        return mRepositoryManager.obtainRetrofitService(UserService.class)
                .login(account, password, oldAccessToken);
    }

    @Override public Observable<BaseResponse<String>> getCardPassword() {
        return mRepositoryManager.obtainRetrofitService(UserService.class)
                .getCardPassword();
    }

}