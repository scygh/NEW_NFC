package com.lianxi.dingtu.newsnfc.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.lianxi.dingtu.newsnfc.app.utils.RxUtils;
import com.lianxi.dingtu.newsnfc.mvp.contract.StepThreeContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.DepartmentTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.RegisterParam;

import java.util.List;


@ActivityScope
public class StepThreePresenter extends BasePresenter<StepThreeContract.Model, StepThreeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public StepThreePresenter(StepThreeContract.Model model, StepThreeContract.View rootView) {
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

    public void onDepartment() {
        mModel.getDepartment()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<List<DepartmentTo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponse<List<DepartmentTo>> listBaseResponse) {
                        if (listBaseResponse.getStatusCode()!=200){
                            mRootView.showMessage(listBaseResponse.getMessage());
                        }else {
                            if (listBaseResponse.isSuccess())
                                if (listBaseResponse.getContent() != null)
                                    mRootView.onDepartment(listBaseResponse.getContent());
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

    public void onRegister(RegisterParam param) {
        mModel.addRegister(param)
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
                                mRootView.onRegister();
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

    public void getNextNumber(){
        mModel.getNextNumber()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponse<Integer> integerBaseResponse) {
                        if (integerBaseResponse.getStatusCode()!=200) {
                            mRootView.showMessage(integerBaseResponse.getMessage());

                        }else {
                            if(integerBaseResponse.isSuccess())
                                mRootView.onNextNumber(integerBaseResponse.getContent());
                            Log.e(TAG, "onNext: "+ integerBaseResponse.getContent());

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
