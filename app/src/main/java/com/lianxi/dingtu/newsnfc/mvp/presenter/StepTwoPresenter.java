package com.lianxi.dingtu.newsnfc.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.lianxi.dingtu.newsnfc.app.utils.RxUtils;
import com.lianxi.dingtu.newsnfc.mvp.contract.StepTwoContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.CardTypeTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.SubsidyTo;

import java.util.List;


@ActivityScope
public class StepTwoPresenter extends BasePresenter<StepTwoContract.Model, StepTwoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public StepTwoPresenter(StepTwoContract.Model model, StepTwoContract.View rootView) {
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

    public void onSubsidyLeve() {
        mModel.getSubsidyLeve()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<List<SubsidyTo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponse<List<SubsidyTo>> listBaseResponse) {
                        if (listBaseResponse.getStatusCode()!=200){
                            mRootView.showMessage(listBaseResponse.getMessage());
                        }else {
                            if (listBaseResponse.isSuccess())
                                if (listBaseResponse.getContent() != null)
                                    mRootView.onChoiceLeve(listBaseResponse.getContent());
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

    public void onCardType() {
        mModel.getCardType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<CardTypeTo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponse<List<CardTypeTo>> listBaseResponse) {
                        if (listBaseResponse.getStatusCode()!=200){
                            mRootView.showMessage(listBaseResponse.getMessage());
                        }else {
                            if (listBaseResponse.isSuccess())
                                if (listBaseResponse.getContent() != null)
                                    mRootView.onChoiceType(listBaseResponse.getContent());
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

    public void onDonate(int id, double d) {
        mModel.getDonate(id, d)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<Double>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponse<Double> doubleBaseResponse) {
                        if (doubleBaseResponse.getStatusCode()!=200){
                            mRootView.showMessage(doubleBaseResponse.getMessage());
                        }else {
                            if (doubleBaseResponse.isSuccess())
                                mRootView.onDonate(doubleBaseResponse.getContent());
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

    public void onDonateSwitch() {
        mModel.getPayKeySwitch("DonateSwitch")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponse<String> booleanBaseResponse) {
                        if (booleanBaseResponse.getStatusCode()!=200){
                            mRootView.showMessage(booleanBaseResponse.getMessage());
                        }else {
                            if (booleanBaseResponse.isSuccess())
                                mRootView.onDonateSwitch(booleanBaseResponse.getContent());
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
