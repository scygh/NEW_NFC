package com.lianxi.dingtu.newsnfc.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;
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

import com.lianxi.dingtu.newsnfc.app.utils.AppConstant;
import com.lianxi.dingtu.newsnfc.app.utils.RxUtils;
import com.lianxi.dingtu.newsnfc.app.utils.SpUtils;
import com.lianxi.dingtu.newsnfc.mvp.contract.AutoContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.CardInfoTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.KeySwitchTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.ReadCardTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.SimpleExpenseParam;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.SimpleExpenseTo;


@ActivityScope
public class AutoPresenter extends BasePresenter<AutoContract.Model, AutoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public AutoPresenter(AutoContract.Model model, AutoContract.View rootView) {
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

//    public void getCardInfo(int number) {
//        mModel.getByNumber(number)
//                .compose(RxUtils.applySchedulers(mRootView))
//                .subscribe(new ErrorHandleSubscriber<BaseResponse<CardInfoTo>>(mErrorHandler) {
//                    @Override public void onNext(BaseResponse<CardInfoTo> cardInfoToBaseResponse) {
//                        if (cardInfoToBaseResponse.isSuccess())
//                            if (cardInfoToBaseResponse.getContent() != null)
//                                mRootView.onCardInfo(cardInfoToBaseResponse.getContent());
//                    }
//                });
//    }

    public void readtCardInfo(int company, int id, int number) {
        mModel.addReadCard(company, id, number)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<ReadCardTo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponse<ReadCardTo> readCardToBaseResponse) {
                        if (readCardToBaseResponse.getStatusCode()!=200){
                            mRootView.showMessage(readCardToBaseResponse.getMessage());
                        }else {
                            if (readCardToBaseResponse.isSuccess())
                                if (readCardToBaseResponse.getContent() != null)
                                    mRootView.onReadCard(readCardToBaseResponse.getContent());
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

    public void getPaySgetPayKeySwitch() {
        mModel.getPayKeySwitch("PayKeySwitch")
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<String>>(mErrorHandler) {
                    @Override public void onNext(BaseResponse<String> booleanBaseResponse) {
                        if (booleanBaseResponse.isSuccess())
                            mRootView.creatBill(booleanBaseResponse.getContent());
                    }
                });
    }

    public void getPaySgetPayKeySwitch2() {
        String _device = (String) SpUtils.get(mApplication, AppConstant.Receipt.NO, "");
        int id = Integer.valueOf(TextUtils.isEmpty(_device) ? "1" : _device);
        mModel.getEMDevice(id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<KeySwitchTo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponse<KeySwitchTo> keySwitchToBaseResponse) {
                        if (keySwitchToBaseResponse.getStatusCode()!=200){
                            mRootView.showMessage(keySwitchToBaseResponse.getMessage());
                        }else {
                            if (keySwitchToBaseResponse.isSuccess())
                                if (keySwitchToBaseResponse.getContent() != null)
                                    mRootView.creatBill2(keySwitchToBaseResponse.getContent().isKeyEnabled());
                            Log.e(TAG, "onNext: "+keySwitchToBaseResponse.getContent().isKeyEnabled() );
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

    public void createSimpleExpense(SimpleExpenseParam param) {
        mModel.createSimpleExpense(param)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<SimpleExpenseTo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponse<SimpleExpenseTo> simpleExpenseToBaseResponse) {
                        if (simpleExpenseToBaseResponse.getStatusCode()!=200){
                            mRootView.showMessage(simpleExpenseToBaseResponse.getMessage());
                        }else {
                            if (simpleExpenseToBaseResponse.isSuccess())
                                if (simpleExpenseToBaseResponse.getContent() != null)
                                    mRootView.creatSuccess(simpleExpenseToBaseResponse.getContent());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: "+e );
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
