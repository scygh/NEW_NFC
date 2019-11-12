package com.lianxi.dingtu.newsnfc.mvp.ui.adapter;

import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.base.App;
import com.jess.arms.di.component.AppComponent;
import com.lianxi.dingtu.newsnfc.R;
import com.lianxi.dingtu.newsnfc.app.utils.ITypeface;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.ExpenseReportTo;

import java.util.List;

public class ExpenseAdapter extends BaseQuickAdapter<ExpenseReportTo, BaseViewHolder> {
    public ExpenseAdapter(int layoutResId, @Nullable List<ExpenseReportTo> data) {
        super(layoutResId, data);
    }

    @Override protected void convert(BaseViewHolder helper, ExpenseReportTo item) {
//        AppComponent mAppComponent = ((App) helper.itemView.getContext().getApplicationContext()).getAppComponent();
//        Typeface iconfont = ITypeface.getTypeface(mAppComponent.application());
//        ((TextView) helper.itemView.findViewById(R.id.wode)).setTypeface(iconfont);
        helper.setText(R.id.name, item.getName())
                .setText(R.id.time, item.getTradeDateTime())
                .setText(R.id.money, String.format("- %.2f", item.getAmount()));
    }
}
