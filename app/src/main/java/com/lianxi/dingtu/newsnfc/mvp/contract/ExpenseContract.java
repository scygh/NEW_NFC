package com.lianxi.dingtu.newsnfc.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.DepositTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.ExpenseReportTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.ExpenseTo;

import java.util.List;

import io.reactivex.Observable;


public interface ExpenseContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setData(List<ExpenseReportTo> list, boolean isPullRefresh);
        void isErr();
        void noData();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<ExpenseTo>> getExpenseReport(int pageIndex, int pageSize, String deviceIDs);
    }
}
