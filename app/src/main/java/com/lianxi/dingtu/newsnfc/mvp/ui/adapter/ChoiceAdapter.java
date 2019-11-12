package com.lianxi.dingtu.newsnfc.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lianxi.dingtu.newsnfc.R;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaiduGoodsTo;

import java.util.List;

public class ChoiceAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ChoiceAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tips, item);
    }
}
