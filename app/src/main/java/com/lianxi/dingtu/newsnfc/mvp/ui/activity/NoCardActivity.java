package com.lianxi.dingtu.newsnfc.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.newsnfc.R;
import com.lianxi.dingtu.newsnfc.di.component.DaggerNoCardComponent;
import com.lianxi.dingtu.newsnfc.di.module.NoCardModule;
import com.lianxi.dingtu.newsnfc.mvp.contract.NoCardContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.AggregateTo;
import com.lianxi.dingtu.newsnfc.mvp.presenter.NoCardPresenter;
import com.lianxi.dingtu.newsnfc.mvp.ui.adapter.AggregateAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class NoCardActivity extends BaseActivity<NoCardPresenter> implements NoCardContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_target) RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout mSwipeRefresh;
    private BaseQuickAdapter adapter;
    private List<AggregateTo> data = new ArrayList<>();
    View footView;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerNoCardComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .noCardModule(new NoCardModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_no_card; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("当日收益");
        mRecyclerView.setHasFixedSize(true);
        adapter = new AggregateAdapter(R.layout.item_list_no,data);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefresh.setProgressViewOffset(true, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, getResources()
                        .getDisplayMetrics()));

        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setAdapter(adapter);
        onRefresh();
    }


    @Override
    public void showLoading() {
        if (!mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @Override public void onRefresh() {
        mPresenter.setList(true);
    }

    @Override public void setData(List<AggregateTo> list, boolean isPullRefresh) {
        if (isPullRefresh) {
            data.clear();
            adapter.removeFooterView(footView);
            adapter.setEnableLoadMore(true);
            data.addAll(list);
            adapter.setNewData(list);

        } else {
            adapter.loadMoreComplete();
            if (list.size() < 1) {
                adapter.loadMoreEnd();
            } else {
                data.addAll(list);
                adapter.addData(list);
            }
        }
    }

    @Override public void isErr() {
        adapter.loadMoreFail();
    }
}
