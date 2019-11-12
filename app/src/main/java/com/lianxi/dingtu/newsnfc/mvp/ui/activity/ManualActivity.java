package com.lianxi.dingtu.newsnfc.mvp.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.newsnfc.R;
import com.lianxi.dingtu.newsnfc.app.base.NfcJellyBeanActivity;
import com.lianxi.dingtu.newsnfc.app.utils.AppConstant;
import com.lianxi.dingtu.newsnfc.app.utils.AudioUtils;
import com.lianxi.dingtu.newsnfc.app.utils.DoubleUtil;
import com.lianxi.dingtu.newsnfc.app.utils.PrinterUtils;
import com.lianxi.dingtu.newsnfc.app.utils.ShakeAnimation;
import com.lianxi.dingtu.newsnfc.app.utils.SpUtils;
import com.lianxi.dingtu.newsnfc.app.utils.UserInfoHelper;
import com.lianxi.dingtu.newsnfc.app.utils.card.Commands;
import com.lianxi.dingtu.newsnfc.di.component.DaggerManualComponent;
import com.lianxi.dingtu.newsnfc.di.module.ManualModule;
import com.lianxi.dingtu.newsnfc.mvp.contract.ManualContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.CardInfoBean;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.CardInfoTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.ReadCardTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.SimpleExpenseParam;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.SimpleExpenseTo;
import com.lianxi.dingtu.newsnfc.mvp.presenter.ManualPresenter;
import com.lianxi.dingtu.newsnfc.mvp.ui.widget.LoadDialog;
import com.lianxi.dingtu.newsnfc.mvp.ui.widget.MyAnimation;
import com.lianxi.dingtu.newsnfc.mvp.ui.widget.PayDialog;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ManualActivity extends NfcJellyBeanActivity<ManualPresenter> implements ManualContract.View {
    LoadDialog loadDialog;
    CardInfoBean mCardInfoBean;
    @BindView(R.id.root) LinearLayout root;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.number) TextView number;
    @BindView(R.id.balance) TextView balance;
    @BindView(R.id.cost) EditText cost;
    @BindView(R.id.btn_print) Button btnPrint;
    @BindView(R.id.cardview) CardView cardview;
    StringBuilder printer = new StringBuilder();
    private String pwd = "";
    private int payCount = 0;
    SimpleExpenseParam param = new SimpleExpenseParam();
    int company = 0;
    int device = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerManualComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .manualModule(new ManualModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return R.layout.activity_manual; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("手动消费");
        setupWindowAnimations();

        loadDialog = LoadDialog.getInstance();
        loadDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.load_dialog);
        cost.setInputType(InputType.TYPE_DATETIME_VARIATION_NORMAL);
        cost.setCursorVisible(false);
        company = UserInfoHelper.getInstance(this).getCode();
        String _device = (String) SpUtils.get(this, AppConstant.Receipt.NO, "");
        device = Integer.valueOf(TextUtils.isEmpty(_device) ? "1" : _device);
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
//            System.out.println(Arrays.toString(tag.getTechList()));
            UserInfoHelper mUserInfoHelper = UserInfoHelper.getInstance(ManualActivity.this);
            mCardInfoBean = Commands.getInstance(ManualActivity.this).readTag(tag);
            Timber.wtf("M1卡信息：" + mCardInfoBean.getCode());
            if (TextUtils.isEmpty(mCardInfoBean.getCode())) {
                return;
            } else {
                if (TextUtils.equals("0000", mCardInfoBean.getCode())) {
                    onFailed();
                    AudioUtils.getInstance().speakText("空白卡");
                    showMessage("空白卡");
                    return;
                } else if (Integer.parseInt(mCardInfoBean.getCode(), 16) != mUserInfoHelper.getCode()) {
                    onFailed();
                    AudioUtils.getInstance().speakText("非本单位卡");
                    showMessage("非本单位卡");
                    return;
                }
            }
//            mPresenter.getCardInfo(mCardInfoBean.getNum());
            mPresenter.readtCardInfo(company, device, mCardInfoBean.getNum());
        } else {
            System.out.println("intent null");
        }

    }

    @OnClick(R.id.btn_print) public void onViewClicked() {
        if (util.check()) return;
        btnPrint.startAnimation(new MyAnimation());
        String print = (String) SpUtils.get(ManualActivity.this, AppConstant.Print.STUB1, "");
        PrinterUtils.getInstance(ManualActivity.this).printLianxi(new StringBuilder(print));
    }

    @Override public void onCardInfo(CardInfoTo cardInfoTo) {
        param.setNumber(cardInfoTo.getNumber());
        payCount = cardInfoTo.getPayCount();
        name.setText(cardInfoTo.getName());
        number.setText("NO." + cardInfoTo.getSerialNo());
        String amount = String.format("￥%.2f", DoubleUtil.add3(cardInfoTo.getDonate(), cardInfoTo.getCash(), cardInfoTo.getSubsidy()));
        SpannableStringBuilder builder = new SpannableStringBuilder(amount);
        builder.setSpan(new AbsoluteSizeSpan(ArmsUtils.sp2px(this, 27)), amount.indexOf("￥"), amount.indexOf("￥") + 1
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(ArmsUtils.sp2px(this, 27)), amount.indexOf("."), amount.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        balance.setText(builder);


        ObjectAnimator nopeAnimator = ShakeAnimation.nope(cardview);
        nopeAnimator.setRepeatCount(ValueAnimator.INFINITE);
        nopeAnimator.start();
        Flowable.just(1)
                .delay(1500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        nopeAnimator.end();
                    }
                });
    }

    @Override public void creatSuccess(SimpleExpenseTo simpleExpenseTo) {
        printer.append("\n工作模式: 手动扣款");
        printer.append("\n姓    名: " + name.getText().toString().trim());
        printer.append(String.format("\n折扣比率: %s", simpleExpenseTo.getExpenseDetail().getDiscountRate()));
        printer.append(String.format("\n实际消费: %.2f", simpleExpenseTo.getExpenseDetail().getAmount()));
        printer.append(String.format("\n账户余额: %.2f", simpleExpenseTo.getExpenseDetail().getBalance()));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        printer.append("\n" + str);
        SpUtils.put(this, AppConstant.Print.STUB1, printer.toString());
        PrinterUtils.getInstance(ManualActivity.this).printLianxi(printer);
        printer.setLength(0);
        payCount = -1;
        String amount = String.format("￥%.2f", simpleExpenseTo.getExpenseDetail().getBalance());
        SpannableStringBuilder builder = new SpannableStringBuilder(amount);
        builder.setSpan(new AbsoluteSizeSpan(ArmsUtils.sp2px(this, 27)), amount.indexOf("￥"), amount.indexOf("￥") + 1
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(ArmsUtils.sp2px(this, 27)), amount.indexOf("."), amount.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        balance.setText(builder);

        ObjectAnimator nopeAnimator = ShakeAnimation.nope(balance);
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
        AudioUtils.getInstance().speakText("消费" + simpleExpenseTo.getExpenseDetail().getAmount() + "元");
        Log.e(TAG, "creatSuccess: "+"消费" + simpleExpenseTo.getExpenseDetail().getAmount() + "元");
        Toasty.success(this, "消费成功", Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        cost.requestFocus();
        // TODO Auto-generated method stub
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() != KeyEvent.ACTION_UP) {
            mPresenter.getPaySgetPayKeySwitch2();
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_DEL && event.getAction() != KeyEvent.ACTION_UP) {
            cost.setText("");
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    PayDialog payDialog = null;

    void createPayDialog() {
        if (payDialog == null) {
            payDialog = new PayDialog(this);
            payDialog.setPasswordCallback(new PayDialog.PasswordCallback() {
                @Override
                public void callback(String password) {
//                if ("0000".equals(password)) {
//                    payDialog.clearPasswordText();
//                    Toasty.error(ManualActivity.this, "密码为错误，请重试", Toast.LENGTH_SHORT, true).show();
//
//                } else {
//                    Toast.makeText(ManualActivity.this, "密码为：" + password, Toast.LENGTH_SHORT).show();
//                    payDialog.dismiss();
//                }
                    pwd = password;
                    payDialog.dismiss();
                    String deviceID = (String) SpUtils.get(ManualActivity.this, AppConstant.Receipt.NO, "");
                    param.setNumber(mCardInfoBean.getNum());
                    param.setAmount(Double.parseDouble(cost.getText().toString().trim()));
                    param.setDeviceID(Integer.valueOf(TextUtils.isEmpty(deviceID) ? "1" : deviceID));
                    param.setPayCount(payCount + 1);
                    param.setPayKey(pwd);
                    param.setPattern(1);
                    param.setDeviceType(2);
                    mPresenter.createSimpleExpense(param);
                }
            });
        }
        payDialog.clearPasswordText();
        payDialog.setMoney(cost.getText().toString().trim());
        payDialog.show();
        payDialog.setCancelable(false);
        payDialog.setCanceledOnTouchOutside(false);
    }

    private boolean checking() {
        if (param.getNumber() == 0) {
            Toast.makeText(this, "未检测到卡片", Toast.LENGTH_LONG).show();
            return true;
        } else if (TextUtils.isEmpty(cost.getText().toString().trim())) {
            Toast.makeText(this, "似乎您忘记输入消费金额了", Toast.LENGTH_LONG).show();
            return true;
        } else if (!DoubleUtil.isNumber(cost.getText().toString().trim())) {
            Toast.makeText(this, "消费金额不正确", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    @Override
    public void creatBill(String str) {
        if (checking())
            return;
        if (payCount == -1) {
            showMessage("重新放置卡片");
            AudioUtils.getInstance().speakText("重新放置卡片");
            return;
        }
        if (TextUtils.equals("1", str)) {
            createPayDialog();
        } else {
            String deviceID = (String) SpUtils.get(this, AppConstant.Receipt.NO, "");
            param.setAmount(Double.parseDouble(cost.getText().toString().trim()));
            param.setDeviceID(Integer.valueOf(TextUtils.isEmpty(deviceID) ? "1" : deviceID));
            param.setPayCount(payCount + 1);
            param.setPayKey(pwd);
            param.setPattern(1);
            param.setDeviceType(2);
            mPresenter.createSimpleExpense(param);
        }


    }

    @Override public void creatBill2(boolean isOpen) {
        if (checking())
            return;
        if (payCount == -1) {
            showMessage("重新放置卡片");
            AudioUtils.getInstance().speakText("重新放置卡片");
            return;
        }
        if (isOpen) {
            createPayDialog();
        } else {
            String deviceID = (String) SpUtils.get(this, AppConstant.Receipt.NO, "");
            param.setAmount(Double.parseDouble(cost.getText().toString().trim()));
            param.setDeviceID(Integer.valueOf(TextUtils.isEmpty(deviceID) ? "1" : deviceID));
            param.setPayCount(payCount + 1);
            param.setPayKey(pwd);
            param.setPattern(1);
            param.setDeviceType(2);
            mPresenter.createSimpleExpense(param);
        }
    }

    @Override public void onReadCard(ReadCardTo readCardTo) {
        param.setNumber(readCardTo.getNumber());
        payCount = readCardTo.getPayCount();
        name.setText(readCardTo.getUserName());

        String amount = String.format("￥%.2f", readCardTo.getBalance());
        SpannableStringBuilder builder = new SpannableStringBuilder(amount);
        builder.setSpan(new AbsoluteSizeSpan(ArmsUtils.sp2px(this, 27)), amount.indexOf("￥"), amount.indexOf("￥") + 1
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(ArmsUtils.sp2px(this, 27)), amount.indexOf("."), amount.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        balance.setText(builder);


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

    @Override public void onFailed() {
        name.setText("姓名 ****");
        balance.setText("余额 0.00");
    }

}
