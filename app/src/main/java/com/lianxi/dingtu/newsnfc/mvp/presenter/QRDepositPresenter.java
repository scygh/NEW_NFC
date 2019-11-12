package com.lianxi.dingtu.newsnfc.mvp.presenter;

import android.app.Application;
import android.util.Log;

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
import com.lianxi.dingtu.newsnfc.mvp.contract.QRDepositContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaiduGoodsTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.CardInfoTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.QRDepositParam;


@ActivityScope
public class QRDepositPresenter extends BasePresenter<QRDepositContract.Model, QRDepositContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public QRDepositPresenter(QRDepositContract.Model model, QRDepositContract.View rootView) {
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
    public void getCardInfo(int number) {
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
                                if (cardInfoToBaseResponse.getContent() != null)
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

    public void getQRDposit(QRDepositParam param){
        mModel.getQRDeposit(param)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse>() {
                    @Override public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.getStatusCode()!=200){
                            mRootView.showMessage(baseResponse.getMessage());
                            Log.e(TAG, "onNext: "+baseResponse.getMessage());
                        }else {
                            if(baseResponse.isSuccess())
                                getCardInfo(param.getNumber());
                        }

                    }
                });
    }
}
