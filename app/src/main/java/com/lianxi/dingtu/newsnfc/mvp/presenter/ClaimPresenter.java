package com.lianxi.dingtu.newsnfc.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.lianxi.dingtu.newsnfc.app.utils.RxUtils;
import com.lianxi.dingtu.newsnfc.mvp.contract.ClaimContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.CardInfoTo;


@ActivityScope
public class ClaimPresenter extends BasePresenter<ClaimContract.Model, ClaimContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ClaimPresenter(ClaimContract.Model model, ClaimContract.View rootView) {
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

    public void onByNumber(int number) {
        mModel.getByNumber(number)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<CardInfoTo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponse<CardInfoTo> cardInfoToBaseResponse) {
                        if (cardInfoToBaseResponse.getStatusCode()!=200){
                            mRootView.showMessage(cardInfoToBaseResponse.getMessage());
                        }else {
                            if (cardInfoToBaseResponse.isSuccess())
                                mRootView.onCardInfo(cardInfoToBaseResponse.getContent());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void onObtainByNumber(int number) {
        mModel.addObtainByNumber(number)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.getStatusCode()!=200){
                            mRootView.showMessage(baseResponse.getMessage());
                        }else {
                            if (baseResponse.isSuccess())
                                mRootView.onSuccess(baseResponse.isResult());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
