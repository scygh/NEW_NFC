package com.lianxi.dingtu.newsnfc.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lianxi.dingtu.newsnfc.app.utils.AppConstant;
import com.lianxi.dingtu.newsnfc.app.utils.SpUtils;
import com.lianxi.dingtu.newsnfc.mvp.contract.PaymentContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.QRExpenseParam;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.QRExpenseTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.QRReadTo;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class PaymentPresenter extends BasePresenter<PaymentContract.Model, PaymentContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public PaymentPresenter(PaymentContract.Model model, PaymentContract.View rootView) {
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

    public void onScanQR(String str, double dou) {
        String deviceID = (String) SpUtils.get(mApplication, AppConstant.Receipt.NO, "");
        int id = Integer.valueOf(TextUtils.isEmpty(deviceID) ? "1" : deviceID);
        mModel.getQRRead(str, id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Observer<BaseResponse<QRReadTo>>() {
                    @Override
                    public void onError(Throwable t) {
                        mRootView.hideLoading();
                        mRootView.onPayFailure();
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<QRReadTo> qrReadToBaseResponse) {
                        if (qrReadToBaseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(qrReadToBaseResponse.getMessage());
                            mRootView.onPayFailure();
                        } else {
                            if (qrReadToBaseResponse.isSuccess()) {
                                QRExpenseParam param = new QRExpenseParam();
                                param.setQRContent(str);
                                param.setNumber(qrReadToBaseResponse.getContent().getNumber());
                                param.setAmount(dou);
                                param.setPattern(1);
                                param.setPayCount(qrReadToBaseResponse.getContent().getPayCount());
                                param.setDeviceID(id);
                                param.setDeviceType(2);
                                param.setQRType(qrReadToBaseResponse.getContent().getQRType());
                                onExpenseQR(param);
                            }
                        }

                    }
                });
    }

    private void onExpenseQR(QRExpenseParam param) {
        mModel.addQRExpense(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Observer<BaseResponse<QRExpenseTo>>() {
                    @Override
                    public void onError(Throwable t) {
                        mRootView.onPayFailure();
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<QRExpenseTo> qrExpenseToBaseResponse) {
                        if (qrExpenseToBaseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(qrExpenseToBaseResponse.getMessage());
                            mRootView.onPayFailure();

                        } else {
                            if (qrExpenseToBaseResponse.isSuccess())
                                mRootView.onPaySuccess(qrExpenseToBaseResponse.getContent());
                        }

                    }
                });
    }
}
