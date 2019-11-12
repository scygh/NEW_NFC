package com.lianxi.dingtu.newsnfc.mvp.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
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
import com.lianxi.dingtu.newsnfc.di.component.DaggerCloseComponent;
import com.lianxi.dingtu.newsnfc.di.module.CloseModule;
import com.lianxi.dingtu.newsnfc.mvp.contract.CloseContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.CardInfoBean;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.CardInfoTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.MoneyParam;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.ReadCardTo;
import com.lianxi.dingtu.newsnfc.mvp.presenter.ClosePresenter;
import com.lianxi.dingtu.newsnfc.mvp.ui.widget.HollowTextView;
import com.lianxi.dingtu.newsnfc.mvp.ui.widget.LoadDialog;
import com.lianxi.dingtu.newsnfc.mvp.ui.widget.label.LabelRelativeLayout;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class CloseActivity extends NfcJellyBeanActivity<ClosePresenter> implements CloseContract.View {
    @BindView(R.id.root) LinearLayout root;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.number) TextView number;
    @BindView(R.id.balance) TextView balance;
    @BindView(R.id.donate) TextView donate;
    @BindView(R.id.subsidies) TextView subsidies;
    @BindView(R.id.cost) TextView cost;
    @BindView(R.id.warn) TextView warn;
    @BindView(R.id.cardview) CardView cardview;
    @BindView(R.id.cardview0) CardView cardview0;
    @BindView(R.id.label_rl) LabelRelativeLayout labelRelativeLayout;
    @BindView(R.id.submit) Button submit;
    LoadDialog loadDialog;
    CardInfoBean mCardInfoBean;
    Tag tag;
    CardInfoTo mCardInfoTo;
    @BindView(R.id.edit_cost) EditText editCost;
    @BindView(R.id.edit_amount) EditText editAmount;
    private AnimatorSet inSet;
    private AnimatorSet outSet;

    private boolean showingGray = true;
    int company = 0;
    int device = 0;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCloseComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .closeModule(new CloseModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_close; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("注销");
        setupWindowAnimations();
        Typeface iconfont = ITypeface.getTypeface(this);


        loadDialog = LoadDialog.getInstance();
        loadDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.load_dialog);

        submit.setEnabled(false);
        submit.setText("请先刷卡，启用按钮");

        inSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_in);
        outSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_out);

        String _device = (String) SpUtils.get(this, AppConstant.Receipt.NO, "");
        device = Integer.valueOf(TextUtils.isEmpty(_device) ? "1" : _device);

        company = UserInfoHelper.getInstance(this).getCode();
    }

    private void setupWindowAnimations() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_left_to_center);   //得到一个LayoutAnimationController对象；
        LayoutAnimationController controller = new LayoutAnimationController(animation);   //设置控件显示的顺序；
        controller.setOrder(LayoutAnimationController.ORDER_REVERSE);   //设置控件显示间隔时间；
        controller.setDelay(0.3f);

        root.setLayoutAnimation(controller);
        root.startLayoutAnimation();
    }

    private void flipToGray() {
        if (!showingGray && !outSet.isRunning() && !inSet.isRunning()) {
            showingGray = true;

            cardview0.setCardElevation(0);
            cardview.setCardElevation(0);

            outSet.setTarget(cardview0);
            outSet.start();

            inSet.setTarget(cardview);
            inSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    cardview.setCardElevation(ArmsUtils.dip2px(CloseActivity.this, 12));
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            inSet.start();
        }
    }

    private void flipToBlue() {
        if (showingGray && !outSet.isRunning() && !inSet.isRunning()) {
            showingGray = false;

            cardview.setCardElevation(0);
            cardview0.setCardElevation(0);

            outSet.setTarget(cardview);
            outSet.start();

            inSet.setTarget(cardview0);
            inSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    cardview0.setCardElevation(ArmsUtils.dip2px(CloseActivity.this, 12));
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            inSet.start();
        }
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
            tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            System.out.println(Arrays.toString(tag.getTechList()));
            UserInfoHelper mUserInfoHelper = UserInfoHelper.getInstance(CloseActivity.this);
            mCardInfoBean = Commands.getInstance(CloseActivity.this).readTag(tag);
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
            mPresenter.readtCardInfo(company, device, mCardInfoBean.getNum());

        } else {
            System.out.println("intent null");
        }

    }


    @Override public void onCardInfo(CardInfoTo cardInfoTo) {
        mCardInfoTo = cardInfoTo;
        name.setText(cardInfoTo.getName());
        number.setText("NO." + cardInfoTo.getSerialNo());
        String amount = String.format("￥%.2f", cardInfoTo.getCash());
        SpannableStringBuilder builder = new SpannableStringBuilder(amount);
        builder.setSpan(new AbsoluteSizeSpan(ArmsUtils.sp2px(this, 24)), amount.indexOf("￥"), amount.indexOf("￥") + 1
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(ArmsUtils.sp2px(this, 24)), amount.indexOf("."), amount.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        balance.setText(builder);
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
        donate.setText(String.format("%.2f", cardInfoTo.getDonate()));
        subsidies.setText(String.format("%.2f", cardInfoTo.getSubsidy()));
        cost.setText(String.format("工本费 %.2f", cardInfoTo.getCost()));
        editAmount.setText(String.format("%.2f", cardInfoTo.getCash()));
        editCost.setText(String.format("%.2f", cardInfoTo.getCost()));
        flipToGray();
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

    @Override public void onDeregister() {
        mCardInfoBean.setCode("0000");
        mCardInfoBean.setNum(0);
        mCardInfoBean.setType(0);
        mCardInfoBean.setSpending_limit(0);
        mCardInfoBean.setCash_account(0.00);
        mCardInfoBean.setCard_validity("2000-00-00");
        mCardInfoBean.setConsumption_num(0);
        mCardInfoBean.setSubsidies_time("2000-00");
        mCardInfoBean.setName("");
        mCardInfoBean.setLevel(0);
        mCardInfoBean.setGuaranteed_amount(0);
        mCardInfoBean.setAllowance_account(0.00);
        mCardInfoBean.setSpending_time("2000-00-00");
        mCardInfoBean.setMeal_times(0);
        mCardInfoBean.setDiscount(0);
        Timber.wtf("写卡参数：" + JSON.toJSONString(mCardInfoBean));
        boolean isWrite = Commands.getInstance(CloseActivity.this).writeTag(tag, mCardInfoBean);
        flipToBlue();
        if (isWrite)
            warn.setVisibility(View.INVISIBLE);
        else
            warn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onReadCard(ReadCardTo content) {
        mPresenter.getCardInfo(mCardInfoBean.getNum());
        submit.setEnabled(true);
        submit.setText("确认注销");
    }

    @OnClick({R.id.submit, R.id.tv_back}) public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (util.check()) return;
                submit.setEnabled(false);
                submit.setText("重新刷卡，启用按钮");
                String deviceID = (String) SpUtils.get(this, AppConstant.Receipt.NO, "");
                MoneyParam param = new MoneyParam();

                param.setUserID(mCardInfoTo.getUserID());
                param.setNumber(mCardInfoTo.getNumber());
                param.setDeviceID(Integer.valueOf(TextUtils.isEmpty(deviceID) ? "1" : deviceID));
                param.setAmount(TextUtils.isEmpty(editAmount.getText().toString().trim()) ? 0 : Double.valueOf(editAmount.getText().toString().trim()));
                param.setCost(TextUtils.isEmpty(editCost.getText().toString().trim()) ? 0 : Double.valueOf(editCost.getText().toString().trim()));
                mPresenter.onDeregister(param);
                break;
            case R.id.tv_back:
                break;
        }


    }


}
