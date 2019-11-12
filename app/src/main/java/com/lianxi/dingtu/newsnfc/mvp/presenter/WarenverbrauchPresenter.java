package com.lianxi.dingtu.newsnfc.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.lianxi.dingtu.newsnfc.app.utils.AppConstant;
import com.lianxi.dingtu.newsnfc.app.utils.RxUtils;
import com.lianxi.dingtu.newsnfc.app.utils.SpUtils;
import com.lianxi.dingtu.newsnfc.mvp.contract.WarenverbrauchContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.GetDetailList;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.GetEMGoods;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.KeySwitchTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.ReadCardTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.SimpleExpenseParam;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.SimpleExpenseTo;

import javax.inject.Inject;

import es.dmoral.toasty.Toasty;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class WarenverbrauchPresenter extends BasePresenter<WarenverbrauchContract.Model, WarenverbrauchContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public WarenverbrauchPresenter(WarenverbrauchContract.Model model, WarenverbrauchContract.View rootView) {
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<ReadCardTo>>(mErrorHandler) {
                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onFailed();
                    }

                    @Override
                    public void onNext(BaseResponse<ReadCardTo> readCardToBaseResponse) {
                        if (readCardToBaseResponse.isSuccess())
                            if (readCardToBaseResponse.getContent() != null)
                                mRootView.onReadCard(readCardToBaseResponse.getContent());
                    }
                });
    }

    public void getPaySgetPayKeySwitch() {
        mModel.getPayKeySwitch("PayKeySwitch")
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<String> booleanBaseResponse) {
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<KeySwitchTo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<KeySwitchTo> keySwitchToBaseResponse) {
                        if (keySwitchToBaseResponse.isSuccess())
                            if (keySwitchToBaseResponse.getContent() != null)
                                mRootView.creatBill2(keySwitchToBaseResponse.getContent().isKeyEnabled());
                    }
                });
    }

    public void createSimpleExpense(SimpleExpenseParam param) {
        mModel.createSimpleExpense(param)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<SimpleExpenseTo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<SimpleExpenseTo> simpleExpenseToBaseResponse) {
                        if (simpleExpenseToBaseResponse.isSuccess()) {
                            if (simpleExpenseToBaseResponse.getContent() != null) {
                                mRootView.creatSuccess(simpleExpenseToBaseResponse.getContent());
                            }
                        } else {
                            ToastUtils.showShort(simpleExpenseToBaseResponse.getMessage());
                        }
                    }
                });
    }

    public void getEmGoods() {
        mModel.getEmGoods()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<GetEMGoods>(mErrorHandler) {
                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onFailed();
                    }

                    @Override
                    public void onNext(GetEMGoods getEMGoods) {
                        if (getEMGoods != null && getEMGoods.getStatusCode() == 200) {
                            mRootView.onEMGoodsGet(getEMGoods);
                        }
                    }
                });
    }

    public void getEmGoodsDetail(String type) {
        mModel.getEmGoodsdetail(1,50,type)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<GetDetailList>(mErrorHandler) {
                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onFailed();
                    }

                    @Override
                    public void onNext(GetDetailList getDetailList) {
                        if (getDetailList != null && getDetailList.getStatusCode() == 200) {
                            mRootView.onEMGoodsDetailGet(getDetailList);
                        }
                    }
                });
    }

    public void createQRExpense(SimpleExpenseParam param) {
        mModel.createSimpleExpense(param)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<SimpleExpenseTo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<SimpleExpenseTo> simpleExpenseToBaseResponse) {
                        if (simpleExpenseToBaseResponse.isSuccess()) {
                            if (simpleExpenseToBaseResponse.getContent() != null) {
                                mRootView.creatSuccess(simpleExpenseToBaseResponse.getContent());
                            }
                        } else {
                            ToastUtils.showShort(simpleExpenseToBaseResponse.getMessage());
                        }
                    }
                });
    }
}
