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
import timber.log.Timber;

import javax.inject.Inject;

import com.jess.arms.utils.RxLifecycleUtils;
import com.lianxi.dingtu.newsnfc.app.event.ReplyEvent;
import com.lianxi.dingtu.newsnfc.app.utils.AppConstant;
import com.lianxi.dingtu.newsnfc.app.utils.RxUtils;
import com.lianxi.dingtu.newsnfc.app.utils.RxUtils2;
import com.lianxi.dingtu.newsnfc.app.utils.SpUtils;
import com.lianxi.dingtu.newsnfc.mvp.contract.TodayContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.AggregateTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.DepositTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.MachineAmountTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.UserInfoTo;

import org.simple.eventbus.EventBus;

import java.math.BigDecimal;
import java.text.Format;
import java.util.Calendar;
import java.util.List;


@ActivityScope
public class TodayPresenter extends BasePresenter<TodayContract.Model, TodayContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public TodayPresenter(TodayContract.Model model, TodayContract.View rootView) {
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
    public void getMachineAmount(){
        Integer deviceID = Integer.parseInt((String) SpUtils.get(mApplication, AppConstant.Receipt.NO, "1"));

        mModel.getMachineAmount(deviceID)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<MachineAmountTo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<MachineAmountTo> machineAmountToBaseResponse) {
                        if (machineAmountToBaseResponse.isSuccess()){
                            mRootView.MachineAmount(machineAmountToBaseResponse.getContent());
                            EventBus.getDefault().post(new ReplyEvent(1));
                            Log.e(TAG, "MachineAmount: 成功" );

                        }
                    }
                });
    }
    public void getMachineTimeCount(){
        Integer deviceID = Integer.parseInt((String) SpUtils.get(mApplication, AppConstant.Receipt.NO, "1"));

        mModel.getMachineTimeCount(deviceID)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<MachineAmountTo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<MachineAmountTo> machineAmountToBaseResponse) {
                        if (machineAmountToBaseResponse.isSuccess()){
                            mRootView.MachineTimeCount(machineAmountToBaseResponse.getContent());
                            EventBus.getDefault().post(new ReplyEvent(1));
                            Log.e(TAG, "MachineTimeCount: 成功" );

                        }
                    }
                });
    }
}
