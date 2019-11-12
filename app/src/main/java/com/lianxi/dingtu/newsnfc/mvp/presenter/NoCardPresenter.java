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

import com.lianxi.dingtu.newsnfc.app.utils.RxUtils2;
import com.lianxi.dingtu.newsnfc.mvp.contract.NoCardContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.AggregateTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;

import java.text.Format;
import java.util.Calendar;
import java.util.List;


@ActivityScope
public class NoCardPresenter extends BasePresenter<NoCardContract.Model, NoCardContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public NoCardPresenter(NoCardContract.Model model, NoCardContract.View rootView) {
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
        Format format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        String start = format.format(c.getTime()) + " 00:00:00";
        c.add(Calendar.DAY_OF_WEEK, 1);
        String end = format.format(c.getTime()) + " 00:00:00";
        mModel.getAggregateTo(start, end)
                .compose(RxUtils2.applySchedulers(mRootView, isPullRefresh))
                .subscribe(new Observer<BaseResponse<List<AggregateTo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponse<List<AggregateTo>> listBaseResponse) {
                        if (listBaseResponse.getStatusCode()!=200){
                            mRootView.showMessage(listBaseResponse.getMessage());
                        }else {
                            if (listBaseResponse.isSuccess())
                                if (listBaseResponse.getContent() != null)
                                    mRootView.setData(listBaseResponse.getContent(), isPullRefresh);
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
