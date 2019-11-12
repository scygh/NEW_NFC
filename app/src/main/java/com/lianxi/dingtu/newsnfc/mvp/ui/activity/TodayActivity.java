package com.lianxi.dingtu.newsnfc.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.newsnfc.R;
import com.lianxi.dingtu.newsnfc.app.event.ReplyEvent;
import com.lianxi.dingtu.newsnfc.di.component.DaggerTodayComponent;
import com.lianxi.dingtu.newsnfc.di.module.TodayModule;
import com.lianxi.dingtu.newsnfc.mvp.contract.TodayContract;
import com.lianxi.dingtu.newsnfc.mvp.presenter.TodayPresenter;

import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class TodayActivity extends BaseActivity<TodayPresenter> implements TodayContract.View {
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.loading) ConstraintLayout loading;
    @BindView(R.id.content) ConstraintLayout content;
    @BindView(R.id.today) TextView today;
    @BindView(R.id.yesterday) TextView yesterday;
    @BindView(R.id.this_month) TextView thisMonth;
    @BindView(R.id.last_month) TextView lastMonth;
    int flag = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTodayComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .todayModule(new TodayModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_today; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("当前收益");
        mPresenter.setToday();
        mPresenter.setYesterday();
        mPresenter.setThisMonth();
        mPresenter.setLastMonth();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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

    @Override public void onToday(double cost) {
        Timber.wtf("时间onToday    " + cost);
        today.setText(String.format("%.2f", cost));
    }

    @Override public void onThisMonth(double cost) {
        Timber.wtf("时间onThisMonth" + cost);
        thisMonth.setText(String.format("当月消费：%.2f", cost));
    }

    @Override public void onYesterday(double cost) {
        Timber.wtf("时间onYesterday" + cost);
        yesterday.setText(String.format("%.2f", cost));
    }

    @Override public void onLastMonth(double cost) {
        Timber.wtf("时间onLastMonth" + cost);
        lastMonth.setText(String.format("上月消费：%.2f", cost));
    }

    @Subscriber
    private void onReplyEvent(ReplyEvent event) {
        flag += event.hasDone;
        if(flag == 4 ){
            loading.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            content.setVisibility(View.VISIBLE);
        }
    }
}
