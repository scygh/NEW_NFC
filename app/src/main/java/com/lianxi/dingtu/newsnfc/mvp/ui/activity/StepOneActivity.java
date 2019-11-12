package com.lianxi.dingtu.newsnfc.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.newsnfc.R;
import com.lianxi.dingtu.newsnfc.app.EventBusTags;
import com.lianxi.dingtu.newsnfc.app.utils.AppConstant;
import com.lianxi.dingtu.newsnfc.di.component.DaggerStepOneComponent;
import com.lianxi.dingtu.newsnfc.di.module.StepOneModule;
import com.lianxi.dingtu.newsnfc.mvp.contract.StepOneContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.RegisterParam;
import com.lianxi.dingtu.newsnfc.mvp.presenter.StepOnePresenter;
import com.lianxi.dingtu.newsnfc.mvp.ui.widget.LoadDialog;
import com.shuhart.stepview.StepView;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class StepOneActivity extends BaseActivity<StepOnePresenter> implements StepOneContract.View {
    @BindView(R.id.step_view) StepView stepView;
    @BindView(R.id.root) LinearLayout root;
    RegisterParam param = new RegisterParam();
    @BindView(R.id.name) EditText name;
    @BindView(R.id.emp_id) EditText empId;
    @BindView(R.id.id_card) EditText idCard;
    @BindView(R.id.phone) EditText phone;
    @BindView(R.id.address) EditText address;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStepOneComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .stepOneModule(new StepOneModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_step_one; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("开户");
        setupWindowAnimations();
        List<String> steps = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            steps.add("第 " + (i + 1) + " 步");
        }
//        steps.set(steps.size() - 1, steps.get(steps.size() - 1) + "  放置卡片，完成写卡");
        stepView.setSteps(steps);

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

    @OnClick(R.id.next) public void onViewClicked() {
        param.setName(name.getText().toString());
        param.setEmpID(empId.getText().toString().trim());
        param.setIDCard(idCard.getText().toString().trim());
        param.setPhone(phone.getText().toString().trim());
        param.setAddress(address.getText().toString().trim());
        Intent intent = new Intent();
        intent.putExtra(AppConstant.ActivityIntent.STEP1, param);
        intent.setClass(StepOneActivity.this, StepTwoActivity.class);
        launchActivity(intent);
    }

    @Subscriber(tag = EventBusTags.STEP_DONE)
    public void closePage(String s) {
        finish();
    }

    @Override public boolean dispatchKeyEvent(KeyEvent event) {
        Log.e(TAG, "dispatchKeyEvent: " +event.getKeyCode());
        if(event.getKeyCode()==KeyEvent.KEYCODE_ENTER){
            Log.e(TAG, "dispatchKeyEvent: 回车" );
        }
        return super.dispatchKeyEvent(event);
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e(TAG, "onKeyDown: "+keyCode );
        return super.onKeyDown(keyCode, event);
    }
}
