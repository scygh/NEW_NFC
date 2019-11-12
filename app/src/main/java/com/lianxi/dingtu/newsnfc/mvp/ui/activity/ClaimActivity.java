package com.lianxi.dingtu.newsnfc.mvp.ui.activity;

import android.content.Intent;
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
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.newsnfc.R;
import com.lianxi.dingtu.newsnfc.app.EventBusTags;
import com.lianxi.dingtu.newsnfc.app.base.NfcJellyBeanActivity;
import com.lianxi.dingtu.newsnfc.app.utils.AppConstant;
import com.lianxi.dingtu.newsnfc.app.utils.AudioUtils;
import com.lianxi.dingtu.newsnfc.app.utils.DoubleUtil;
import com.lianxi.dingtu.newsnfc.app.utils.UserInfoHelper;
import com.lianxi.dingtu.newsnfc.app.utils.card.Commands;
import com.lianxi.dingtu.newsnfc.di.component.DaggerClaimComponent;
import com.lianxi.dingtu.newsnfc.di.module.ClaimModule;
import com.lianxi.dingtu.newsnfc.mvp.contract.ClaimContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.CardInfoBean;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.CardInfoTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.RegisterParam;
import com.lianxi.dingtu.newsnfc.mvp.presenter.ClaimPresenter;
import com.lianxi.dingtu.newsnfc.mvp.ui.widget.LoadDialog;
import com.shuhart.stepview.StepView;

import org.simple.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ClaimActivity extends NfcJellyBeanActivity<ClaimPresenter> implements ClaimContract.View {
    @BindView(R.id.root) LinearLayout root;
    @BindView(R.id.step_view) StepView stepView;
    RegisterParam param;
    CardInfoBean mCardInfoBean;
    Tag tag;
    @BindView(R.id.cardview1) CardView cardview1;
    @BindView(R.id.cardview2) CardView cardview2;
    @BindView(R.id.cardview3) CardView cardview3;
    @BindView(R.id.time) TextView time;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.type) TextView type;
    @BindView(R.id.number) TextView number;
    @BindView(R.id.balance) TextView balance;
    @BindView(R.id.tv1) TextView tv1;
    @BindView(R.id.donate) TextView donate;
    @BindView(R.id.subsidies) TextView subsidies;

    boolean isFirst = true;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerClaimComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .claimModule(new ClaimModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_claim; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("开户领卡");
        setupWindowAnimations();
        param = (RegisterParam) getIntent().getSerializableExtra(AppConstant.ActivityIntent.STEP3);

        List<String> steps = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            steps.add("第 " + (i + 1) + " 步");
        }
//        steps.set(steps.size() - 1, steps.get(steps.size() - 1) + "  放置卡片，完成写卡");
        stepView.setSteps(steps);
        if (1 < stepView.getStepCount() - 1) {
            stepView.go(3, true);
        } else {
            stepView.done(true);
        }


    }

    private void setupWindowAnimations() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_right_to_center);   //得到一个LayoutAnimationController对象；
        LayoutAnimationController controller = new LayoutAnimationController(animation);   //设置控件显示的顺序；
        controller.setOrder(LayoutAnimationController.ORDER_REVERSE);   //设置控件显示间隔时间；
        controller.setDelay(0.3f);

        root.setLayoutAnimation(controller);
        root.startLayoutAnimation();
    }

    @Override
    public void showLoading() {
        LoadDialog loadDialog = LoadDialog.getInstance();
        loadDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.load_dialog);
        if (!LoadDialog.getInstance().isAdded() && null == getSupportFragmentManager().findFragmentByTag(TAG)) {
            LoadDialog.getInstance().show(getSupportFragmentManager(), TAG);
        }
    }

    @Override
    public void hideLoading() {
        LoadDialog.getInstance().dismiss();
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
            UserInfoHelper mUserInfoHelper = UserInfoHelper.getInstance(ClaimActivity.this);
            mCardInfoBean = Commands.getInstance(ClaimActivity.this).readTag(tag);
            Timber.wtf("M1卡信息：" + mCardInfoBean.getCode());
            if (!TextUtils.equals("0000", mCardInfoBean.getCode())) {
                AudioUtils.getInstance().speakText("非空卡片，操作失败");
                showMessage("非空卡片，操作失败");
                return;
            }
            if (!isFirst) {
                AudioUtils.getInstance().speakText("请勿重复领卡");
                showMessage("请勿重复领卡");
                return;
            }

            mPresenter.onByNumber(param.getNumber());
        } else {
            System.out.println("intent null");
        }

    }

    @Override public void onCardInfo(CardInfoTo cardInfoTo) {

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

        type.setText(cardInfoTo.getCardTypeName());
        time.setText("开户时间 " + cardInfoTo.getCardCreateTime());

        mCardInfoBean.setNum(cardInfoTo.getNumber());
        mCardInfoBean.setName(cardInfoTo.getName());
        String _cod = Integer.toHexString(UserInfoHelper.getInstance(this).getCode());
        String str = addZeroForNum(_cod, 4);
        mCardInfoBean.setCode(str);

        mCardInfoBean.setCash_account(DoubleUtil.add2(cardInfoTo.getCash(), cardInfoTo.getDonate()));
        mCardInfoBean.setAllowance_account(0);
        mCardInfoBean.setConsumption_num(0);

        mCardInfoBean.setType(cardInfoTo.getCardType());
        mCardInfoBean.setLevel(cardInfoTo.getSubsidyLevel());
        mCardInfoBean.setGuaranteed_amount(cardInfoTo.getForegift());
        mCardInfoBean.setCard_validity(cardInfoTo.getDeadline().substring(0, 10));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String _str = formatter.format(curDate);
        mCardInfoBean.setSpending_time(_str);
        Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
        ca.setTime(new Date()); // 设置时间为当前时间
        ca.add(Calendar.MONTH, -1);// 月份减1
        Date resultDate = ca.getTime(); // 结果
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        System.out.println(sdf.format(resultDate));
        mCardInfoBean.setSubsidies_time(sdf.format(resultDate));
        Log.e(TAG, "comfirm: " + JSON.toJSONString(mCardInfoBean));
        boolean success = Commands.getInstance(ClaimActivity.this).writeTag(tag, mCardInfoBean);

        if (success) {
            mPresenter.onObtainByNumber(param.getNumber());
        }

    }

    @Override public void onSuccess(boolean yes) {
        isFirst = false;
        cardview1.setVisibility(View.GONE);
        cardview2.setVisibility(View.VISIBLE);
        cardview3.setVisibility(View.VISIBLE);
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom);
        cardview2.setLayoutAnimation(controller);
        cardview2.scheduleLayoutAnimation();
        cardview3.setLayoutAnimation(controller);
        cardview3.scheduleLayoutAnimation();
        EventBus.getDefault().post("step_done", EventBusTags.STEP_DONE);
    }

    public static String addZeroForNum(String str, int strLength) {

        if (TextUtils.isEmpty(str)) {
            return "";
        }

        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
            sb.append("0").append(str);// 左补0
//            sb.append(str).append("0");//右补0
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }
}
