package com.lianxi.dingtu.newsnfc.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.CardInfoTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.GetDetailList;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.GetEMGoods;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.KeySwitchTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.ReadCardTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.SimpleExpenseParam;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.SimpleExpenseTo;

import io.reactivex.Observable;


public interface WarenverbrauchContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void onCardInfo(CardInfoTo cardInfoTo);

        void creatSuccess(SimpleExpenseTo simpleExpenseTo);

        void creatBill2(boolean isOpen);

        void onReadCard(ReadCardTo readCardTo);

        void onFailed();

        void onEMGoodsGet(GetEMGoods getEMGoods);

        void onEMGoodsDetailGet(GetDetailList detailList);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<CardInfoTo>> getByNumber(int number);

        Observable<BaseResponse<SimpleExpenseTo>> createSimpleExpense(SimpleExpenseParam param);

        Observable<BaseResponse<String>> getPayKeySwitch(String key);

        Observable<BaseResponse<KeySwitchTo>> getEMDevice(int id);

        Observable<BaseResponse<ReadCardTo>> addReadCard(int companyCode, int deviceID, int number);

        Observable<GetEMGoods> getEmGoods(String state);

        Observable<GetDetailList> getEmGoodsdetail(int index,int pagesize,String type);
    }
    
}
