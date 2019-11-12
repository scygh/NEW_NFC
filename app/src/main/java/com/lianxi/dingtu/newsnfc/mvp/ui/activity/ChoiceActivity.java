package com.lianxi.dingtu.newsnfc.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.newsnfc.R;
import com.lianxi.dingtu.newsnfc.app.utils.AppConstant;
import com.lianxi.dingtu.newsnfc.di.component.DaggerChoiceComponent;
import com.lianxi.dingtu.newsnfc.di.module.ChoiceModule;
import com.lianxi.dingtu.newsnfc.mvp.contract.ChoiceContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.ExpenseReportTo;
import com.lianxi.dingtu.newsnfc.mvp.presenter.ChoicePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ChoiceActivity extends BaseActivity<ChoicePresenter> implements ChoiceContract.View {

    @BindView(R.id.name) TextView name;
    @BindView(R.id.balance) TextView balance;
    @BindView(R.id.trade_date_time) TextView tradeDateTime;
    @BindView(R.id.pattern_name) TextView patternName;
    @BindView(R.id.finance) TextView finance;
    @BindView(R.id.amount) TextView amount;
    @BindView(R.id.original_amount) TextView originalAmount;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChoiceComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .choiceModule(new ChoiceModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_choice; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("消费详情");
        ExpenseReportTo reportTo = (ExpenseReportTo) getIntent().getSerializableExtra(AppConstant.ActivityIntent.MODEL_EXPENSE);
        if (reportTo != null) {
            name.setText(reportTo.getName());
            tradeDateTime.setText("交易时间 " + reportTo.getTradeDateTime());
            balance.setText(String.format("￥%.2f", reportTo.getBalance()));
            amount.setText(String.format("%.2f", reportTo.getOriginalAmount()));
            originalAmount.setText(String.format("%.2f", reportTo.getAmount()));
            String value;
            switch (reportTo.getFinance()) {
                case 0:
                    value = "现金";
                    break;
                case 1:
                    value = "补贴";
                    break;
                case 2:
                    value = "计次";
                    break;
                case 3:
                    value = "赠送";
                    break;
                case 4:
                    value = "积分";
                    break;
                default:
                    value = "";
                    break;
            }
            finance.setText("账户类型：" + value);
            patternName.setText("模式：" + reportTo.getPatternName());
        }
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


}
