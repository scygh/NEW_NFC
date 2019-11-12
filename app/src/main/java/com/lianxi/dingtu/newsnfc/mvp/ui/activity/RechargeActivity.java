package com.lianxi.dingtu.newsnfc.mvp.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.newsnfc.R;
import com.lianxi.dingtu.newsnfc.app.base.NfcJellyBeanActivity;
import com.lianxi.dingtu.newsnfc.app.utils.AppConstant;
import com.lianxi.dingtu.newsnfc.app.utils.AudioUtils;
import com.lianxi.dingtu.newsnfc.app.utils.ITypeface;
import com.lianxi.dingtu.newsnfc.app.utils.ShakeAnimation;
import com.lianxi.dingtu.newsnfc.app.utils.SpUtils;
import com.lianxi.dingtu.newsnfc.app.utils.UserInfoHelper;
import com.lianxi.dingtu.newsnfc.app.utils.card.Commands;
import com.lianxi.dingtu.newsnfc.di.component.DaggerRechargeComponent;
import com.lianxi.dingtu.newsnfc.di.module.RechargeModule;
import com.lianxi.dingtu.newsnfc.mvp.contract.RechargeContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.CardInfoBean;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.CardInfoTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.MoneyParam;
import com.lianxi.dingtu.newsnfc.mvp.presenter.RechargePresenter;
import com.lianxi.dingtu.newsnfc.mvp.ui.widget.LoadDialog;
import com.lianxi.dingtu.newsnfc.mvp.ui.widget.label.LabelRelativeLayout;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class RechargeActivity extends NfcJellyBeanActivity<RechargePresenter> implements RechargeContract.View {
    @BindView(R.id.root) LinearLayout root;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.number) TextView number;
    @BindView(R.id.balance) TextView balance;
    @BindView(R.id.donate) TextView donate;
    @BindView(R.id.subsidies) TextView subsidies;
    @BindView(R.id.et_amount) EditText et_amount;
    @BindView(R.id.et_donate) EditText et_donate;
    @BindView(R.id.et_money) EditText et_money;
    @BindView(R.id.submit) Button submit;
    @BindView(R.id.cardview) CardView cardview;
    @BindView(R.id.label_rl) LabelRelativeLayout labelRelativeLayout;
    LoadDialog loadDialog;
    CardInfoBean mCardInfoBean;
    int type;

    private static final int MESSAGE_SEARCH = 0x1;
    private static long INTERVAL = 1000; // 输入变化时间间隔

    CardInfoTo mCardInfoTo;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MESSAGE_SEARCH) {
                if (!TextUtils.isEmpty(et_amount.getText().toString().trim())) {
                    mPresenter.onDonate(type, Double.valueOf(et_amount.getText().toString().trim()));
                    et_money.setText(et_amount.getText().toString().trim());
                } else {
                    et_donate.setText("");
                    et_money.setText("");
                }

            }
        }
    };


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRechargeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .rechargeModule(new RechargeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_recharge; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("充值");
        setupWindowAnimations();
        Typeface iconfont = ITypeface.getTypeface(this);


        loadDialog = LoadDialog.getInstance();
        loadDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.load_dialog);
        // 设置监听
        et_amount.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mHandler.hasMessages(MESSAGE_SEARCH)) {
                    mHandler.removeMessages(MESSAGE_SEARCH);
                }
                mHandler.sendEmptyMessageDelayed(MESSAGE_SEARCH, INTERVAL);
            }
        });

        submit.setEnabled(false);
        submit.setText("请先刷卡，启用按钮");
    }

    private void setupWindowAnimations(){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_left_to_center);   //得到一个LayoutAnimationController对象；
        LayoutAnimationController controller = new LayoutAnimationController(animation);   //设置控件显示的顺序；
        controller.setOrder(LayoutAnimationController.ORDER_REVERSE);   //设置控件显示间隔时间；
        controller.setDelay(0.3f);

        root.setLayoutAnimation(controller);
        root.startLayoutAnimation();
    }

    @Override
    public void showLoading() {
        if (!loadDialog.isAdded() && null == getSupportFragmentManager().findFragmentByTag(TAG))
            loadDialog.show(getSupportFragmentManager(), TAG);
    }

    @Override
    public void hideLoading() {
        loadDialog.dismiss();
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            System.out.println(Arrays.toString(tag.getTechList()));
            UserInfoHelper mUserInfoHelper = UserInfoHelper.getInstance(RechargeActivity.this);
            mCardInfoBean = Commands.getInstance(RechargeActivity.this).readTag(tag);
            Timber.wtf("M1卡信息：" + mCardInfoBean.getCode());
            if (TextUtils.isEmpty(mCardInfoBean.getCode())) {
                return;
            } else {
                if (TextUtils.equals("0000", mCardInfoBean.getCode())) {
                    AudioUtils.getInstance().speakText("空白卡");
                    showMessage("空白卡");
                    return;
                } else if (Integer.parseInt(mCardInfoBean.getCode(), 16) != mUserInfoHelper.getCode()) {
                    AudioUtils.getInstance().speakText("非本单位卡");
                    showMessage("非本单位卡");
                    return;
                }
            }
            mPresenter.getCardInfo(mCardInfoBean.getNum());
            submit.setEnabled(true);
            submit.setText("确认充值");
        } else {
            System.out.println("intent null");
        }

    }

    @Override public void onCardInfo(CardInfoTo cardInfoTo) {
        type = cardInfoTo.getCardType();
        mCardInfoTo = cardInfoTo;
        name.setText(cardInfoTo.getName());
        number.setText("NO." + cardInfoTo.getSerialNo());
        String amount = String.format("￥%.2f", cardInfoTo.getCash());

        SpannableStringBuilder builder = new SpannableStringBuilder(amount);
        builder.setSpan(new AbsoluteSizeSpan(ArmsUtils.sp2px(this, 24)), amount.indexOf("￥"), amount.indexOf("￥") + 1
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(ArmsUtils.sp2px(this, 24)), amount.indexOf("."), amount.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        balance.setText(builder);
        donate.setText(String.format("%.2f", cardInfoTo.getDonate()));
        subsidies.setText(String.format("%.2f", cardInfoTo.getSubsidy()));

        String value ;
        switch (cardInfoTo.getUserState()) {
            case 0:
                value = "未开户";
                break;
            case 1:
                value = "正常";
                break;
            case 2:
                value = "已挂失";
                break;
            case 3:
                value = "已注销";
                break;
            case 4:
                value = "未审核";
                break;
            case 5:
                value = "审核失败";
                break;
            default:
                value = "";
                break;
        }
        labelRelativeLayout.setTextContent(value);
        ObjectAnimator nopeAnimator = ShakeAnimation.nope(cardview);
        nopeAnimator.setRepeatCount(ValueAnimator.INFINITE);
        nopeAnimator.start();
        Flowable.just(1)
                .delay(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        nopeAnimator.end();
                    }
                });
    }

    @Override public void onDonate(double d) {
        et_donate.setText(String.format("%.2f", d));
    }


    @OnClick(R.id.submit) public void onViewClicked() {
        if (util.check()) return;
        submit.setEnabled(false);
        submit.setText("重新刷卡，启用按钮");
        String deviceID = (String) SpUtils.get(this, AppConstant.Receipt.NO, "");
        MoneyParam param = new MoneyParam();

        param.setUserID(mCardInfoTo.getUserID());
        param.setNumber(mCardInfoTo.getNumber());
        param.setDeviceID(Integer.valueOf(TextUtils.isEmpty(deviceID) ? "1" : deviceID));
        param.setAmount(TextUtils.isEmpty(et_amount.getText().toString().trim()) ? 0 : Double.valueOf(et_amount.getText().toString().trim()));
        param.setMoney(TextUtils.isEmpty(et_money.getText().toString().trim()) ? 0 : Double.valueOf(et_money.getText().toString().trim()));
        param.setDonate(TextUtils.isEmpty(et_donate.getText().toString().trim()) ? 0 : Double.valueOf(et_donate.getText().toString().trim()));
        mPresenter.onDeposit(param);
        Log.e(TAG, "onViewClicked: "+ JSON.toJSONString(param));

    }
}
