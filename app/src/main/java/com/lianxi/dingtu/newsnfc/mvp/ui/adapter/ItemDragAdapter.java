package com.lianxi.dingtu.newsnfc.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lianxi.dingtu.newsnfc.R;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaiduGoodsTo;

import java.util.List;

public class ItemDragAdapter extends BaseItemDraggableAdapter<BaiduGoodsTo, BaseViewHolder> {

    public ItemDragAdapter(int layoutResId, @Nullable List<BaiduGoodsTo> data) {
        super(layoutResId, data);
    }

    @Override protected void convert(BaseViewHolder helper, BaiduGoodsTo item) {
        helper.setText(R.id.name, item.getGoodsName())
                .setText(R.id.price, String.format("￥ %.2f", item.getPrice()))
                .setText(R.id.num,String.format("x %s",item.getCount()))
                .addOnClickListener(R.id.img_reduce);
    }
}
