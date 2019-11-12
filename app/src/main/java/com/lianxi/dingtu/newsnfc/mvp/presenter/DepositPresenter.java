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

import com.lianxi.dingtu.newsnfc.app.utils.RxUtils2;
import com.lianxi.dingtu.newsnfc.mvp.contract.DepositContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.DepositReportTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.DepositTo;

import java.util.List;


@ActivityScope
public class DepositPresenter extends BasePresenter<DepositContract.Model, DepositContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    int index = 1;
    @Inject
    public DepositPresenter(DepositContract.Model model, DepositContract.View rootView) {
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

    public void setList(boolean isPullRefresh) {

        if (isPullRefresh) index = 1;
        mModel.getDepositReport(index,10,"tradedatetime","desc")
                .compose(RxUtils2.applySchedulers(mRootView,isPullRefresh))
                .subscribe(new Observer<BaseResponse<DepositTo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponse<DepositTo> depositToBaseResponse) {

                            if (depositToBaseResponse.getStatusCode()!=200){
                                mRootView.showMessage(depositToBaseResponse.getMessage());
                                Log.e(TAG, "onNext: "+depositToBaseResponse.getMessage() );

                            }else {
                                if(depositToBaseResponse.isSuccess()){
                                if (depositToBaseResponse.getContent() != null){
                                    if(index==1&&depositToBaseResponse.getContent().getRows()==null){
                                        mRootView.noData();
                                    }
                                    if(depositToBaseResponse.getContent().getRows()!=null){
                                        mRootView.setData(depositToBaseResponse.getContent().getRows(), isPullRefresh);
                                        index++;
                                    }
                                }
                            }

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
