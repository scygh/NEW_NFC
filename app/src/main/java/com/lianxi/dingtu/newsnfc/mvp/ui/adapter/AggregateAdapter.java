package com.lianxi.dingtu.newsnfc.mvp.ui.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.newsnfc.R;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.AggregateTo;

import java.util.List;

public class AggregateAdapter extends BaseQuickAdapter<AggregateTo, BaseViewHolder> {
    public AggregateAdapter(int layoutResId, @Nullable List<AggregateTo> data) {
        super(layoutResId, data);
    }

    @Override protected void convert(BaseViewHolder helper, AggregateTo item) {
        String amount = String.format("￥%s", item.getValue());
        SpannableStringBuilder builder = new SpannableStringBuilder(amount);
        builder.setSpan(new AbsoluteSizeSpan(ArmsUtils.sp2px(helper.itemView.getContext(), 12)), amount.indexOf("￥"), amount.indexOf("￥") + 1
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(ArmsUtils.sp2px(helper.itemView.getContext(), 12)), amount.indexOf("."), amount.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        helper.setText(R.id.name, item.getKey())
                .setText(R.id.times, item.getCount() + "次")
                .setText(R.id.money, builder);
        if (!TextUtils.isEmpty(item.getValue()))
            helper.setBackgroundColor(R.id.money, Double.valueOf(item.getValue()) > 0 ? Color.parseColor("#e24747") : Color.parseColor("#4eb128"));
    }
}
