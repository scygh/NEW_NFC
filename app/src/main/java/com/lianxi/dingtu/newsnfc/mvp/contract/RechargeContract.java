package com.lianxi.dingtu.newsnfc.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.CardInfoTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.MoneyParam;

import io.reactivex.Observable;
import retrofit2.http.Body;


public interface RechargeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void onCardInfo(CardInfoTo cardInfoTo);
        void onDonate(double d);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<CardInfoTo>> getByNumber(int number);
        Observable<BaseResponse<Double>> getDonate(int cardType, double amount);
        Observable<BaseResponse> addDeposit(MoneyParam param);
    }
}
