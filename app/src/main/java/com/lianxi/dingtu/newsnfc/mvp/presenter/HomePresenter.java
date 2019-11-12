package com.lianxi.dingtu.newsnfc.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.utils.RxLifecycleUtils;
import com.lianxi.dingtu.newsnfc.app.utils.AppConstant;
import com.lianxi.dingtu.newsnfc.app.utils.SpUtils;
import com.lianxi.dingtu.newsnfc.mvp.contract.HomeContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaiduTokenTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseJson;


@FragmentScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

//    public void getToken() {
//        mModel.getBaiduToken()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
//                .subscribe(new ErrorHandleSubscriber<BaseJson<BaiduTokenTo>>(mErrorHandler) {
//                    @Override public void onNext(BaseJson<BaiduTokenTo> baiduTokenToBaseJson) {
//                        if (baiduTokenToBaseJson.isSuccess()) {
//                            if (baiduTokenToBaseJson.getData() != null) {
//                                if (!TextUtils.isEmpty(baiduTokenToBaseJson.getData().getBaiduApiKey()) && !TextUtils.isEmpty(baiduTokenToBaseJson.getData().getBaiduSecretKey())) {
//                                    SpUtils.put(mApplication, AppConstant.Api.CLIENT_ID, baiduTokenToBaseJson.getData().getBaiduApiKey());
//                                    SpUtils.put(mApplication, AppConstant.Api.CLIENT_SECRET, baiduTokenToBaseJson.getData().getBaiduSecretKey());
//                                }
//
//                            }
//                        }
//
//                    }
//                });
//    }

}
