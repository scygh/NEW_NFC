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

import com.lianxi.dingtu.newsnfc.app.utils.AppConstant;
import com.lianxi.dingtu.newsnfc.app.utils.RxUtils2;
import com.lianxi.dingtu.newsnfc.app.utils.SpUtils;
import com.lianxi.dingtu.newsnfc.mvp.contract.ExpenseContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.DepositReportTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.ExpenseReportTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.ExpenseTo;

import java.util.List;


@ActivityScope
public class ExpensePresenter extends BasePresenter<ExpenseContract.Model, ExpenseContract.View> {
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
    public ExpensePresenter(ExpenseContract.Model model, ExpenseContract.View rootView) {
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
        String deviceID = (String) SpUtils.get(mApplication, AppConstant.Receipt.NO, "1");
        mModel.getExpenseReport(index, 10, deviceID)
                .compose(RxUtils2.applySchedulers(mRootView, isPullRefresh))
                .subscribe(new Observer<BaseResponse<ExpenseTo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<ExpenseTo> expenseToBaseResponse) {
                        if (expenseToBaseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(expenseToBaseResponse.getMessage());
                        } else {
                            if (expenseToBaseResponse.isSuccess()) {
                                if (index == 1 && expenseToBaseResponse.getContent().getRows() == null) {
                                    mRootView.noData();
                                }
                                if (expenseToBaseResponse.getContent().getRows() != null) {
                                    mRootView.setData(expenseToBaseResponse.getContent().getRows(), isPullRefresh);
                                    index++;
                                }
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
