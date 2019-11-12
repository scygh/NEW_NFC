package com.lianxi.dingtu.newsnfc.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

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

import com.lianxi.dingtu.newsnfc.app.event.ReplyEvent;
import com.lianxi.dingtu.newsnfc.app.utils.RxUtils;
import com.lianxi.dingtu.newsnfc.app.utils.RxUtils2;
import com.lianxi.dingtu.newsnfc.mvp.contract.TodayContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.AggregateTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;

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

    public void setToday() {
        Format format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        String start = format.format(c.getTime()) + " 00:00:00";
        c.add(Calendar.DAY_OF_WEEK, 1);
        String end = format.format(c.getTime()) + " 00:00:00";
        Timber.wtf("时间setToday    " + start + "#" + end);
        mModel.getAggregateTo(start, end)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<List<AggregateTo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponse<List<AggregateTo>> listBaseResponse) {
                        if (listBaseResponse.getStatusCode()!=200){
                            mRootView.showMessage(listBaseResponse.getMessage());
                        }else {
                            if (listBaseResponse.isSuccess())
                                if (listBaseResponse.getContent() != null) {
                                    double cost = 0;
                                    for (AggregateTo aggregateTo : listBaseResponse.getContent()) {
                                        if (TextUtils.equals("现金消费", aggregateTo.getKey()) || TextUtils.equals("补贴消费", aggregateTo.getKey()) || TextUtils.equals("赠送消费", aggregateTo.getKey())) {
                                            cost += Double.valueOf(aggregateTo.getValue());
                                        }
                                    }
                                    mRootView.onToday(cost);
                                    EventBus.getDefault().post(new ReplyEvent(1));
                                }
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

    public void setYesterday() {
        Format format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_WEEK, -1);
        String start = format.format(c.getTime()) + " 00:00:00";
        c.add(Calendar.DAY_OF_WEEK, 1);
        String end = format.format(c.getTime()) + " 00:00:00";
        Timber.wtf("时间setYesterday" + start + "#" + end);
        mModel.getAggregateTo(start, end)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<List<AggregateTo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponse<List<AggregateTo>> listBaseResponse) {
                        if (listBaseResponse.getStatusCode()!=200){
                            mRootView.showMessage(listBaseResponse.getMessage());
                        }else {
                            if (listBaseResponse.isSuccess())
                                if (listBaseResponse.getContent() != null) {
                                    double cost = 0;
                                    for (AggregateTo aggregateTo : listBaseResponse.getContent()) {
                                        if (TextUtils.equals("现金消费", aggregateTo.getKey()) || TextUtils.equals("补贴消费", aggregateTo.getKey()) || TextUtils.equals("赠送消费", aggregateTo.getKey())) {
                                            cost += Double.valueOf(aggregateTo.getValue());
                                        }
                                    }
                                    mRootView.onYesterday(cost);
                                    EventBus.getDefault().post(new ReplyEvent(1));
                                }
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

    public void setThisMonth() {
        Format format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        String start = format.format(c.getTime()).substring(0, 8) + "01 00:00:00";
        c.add(Calendar.MONTH, 1);
        String end = format.format(c.getTime()).substring(0, 8) + "01 00:00:00";
        Timber.wtf("时间setThisMonth" + start + "#" + end);
        mModel.getAggregateTo(start, end)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<List<AggregateTo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponse<List<AggregateTo>> listBaseResponse) {
                        if (listBaseResponse.getStatusCode()!=200){
                            mRootView.showMessage(listBaseResponse.getMessage());
                        }else {
                            if (listBaseResponse.isSuccess())
                                if (listBaseResponse.getContent() != null) {
                                    double cost = 0;
                                    for (AggregateTo aggregateTo : listBaseResponse.getContent()) {
                                        if (TextUtils.equals("现金消费", aggregateTo.getKey()) || TextUtils.equals("补贴消费", aggregateTo.getKey()) || TextUtils.equals("赠送消费", aggregateTo.getKey())) {
                                            cost += Double.valueOf(aggregateTo.getValue());
                                        }
                                    }
                                    mRootView.onThisMonth(cost);
                                    EventBus.getDefault().post(new ReplyEvent(1));
                                }
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

    public void setLastMonth() {
        Format format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        String start = format.format(c.getTime()).substring(0, 8) + "01 00:00:00";
        c.add(Calendar.MONTH, 1);
        String end = format.format(c.getTime()).substring(0, 8) + "01 00:00:00";
        Timber.wtf("时间setLastMonth" + start + "#" + end);
        mModel.getAggregateTo(start, end)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<List<AggregateTo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponse<List<AggregateTo>> listBaseResponse) {
                        if (listBaseResponse.getStatusCode()!=200){
                            mRootView.showMessage(listBaseResponse.getMessage());
                        }else {
                            if (listBaseResponse.isSuccess())
                                if (listBaseResponse.getContent() != null) {
                                    double cost = 0;
                                    for (AggregateTo aggregateTo : listBaseResponse.getContent()) {
                                        if (TextUtils.equals("现金消费", aggregateTo.getKey()) || TextUtils.equals("补贴消费", aggregateTo.getKey()) || TextUtils.equals("赠送消费", aggregateTo.getKey())) {
                                            cost += Double.valueOf(aggregateTo.getValue());
                                        }
                                    }
                                    mRootView.onLastMonth(cost);
                                    EventBus.getDefault().post(new ReplyEvent(1));
                                }
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
