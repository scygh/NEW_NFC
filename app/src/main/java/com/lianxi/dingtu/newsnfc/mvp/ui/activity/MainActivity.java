package com.lianxi.dingtu.newsnfc.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.newsnfc.R;
import com.lianxi.dingtu.newsnfc.app.task.MainTask;
import com.lianxi.dingtu.newsnfc.app.task.TaskParams;
import com.lianxi.dingtu.newsnfc.app.utils.FragmentUtils;
import com.lianxi.dingtu.newsnfc.di.component.DaggerMainComponent;
import com.lianxi.dingtu.newsnfc.di.module.MainModule;
import com.lianxi.dingtu.newsnfc.mvp.contract.MainContract;
import com.lianxi.dingtu.newsnfc.mvp.presenter.MainPresenter;
import com.lianxi.dingtu.newsnfc.mvp.ui.fragment.HomeFragment;
import com.lianxi.dingtu.newsnfc.mvp.ui.fragment.MineFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.lianxi.dingtu.newsnfc.app.utils.AppConstant.ACTIVITY_FRAGMENT_REPLACE;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.main_frame)
    FrameLayout mMainFrame;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;

    private List<Fragment> mFragments;
    private int mReplace = 0;

    private OnTabSelectListener mOnTabSelectListener = tabId -> {
        switch (tabId) {
            case R.id.tab_home:
                mReplace = 0;
                break;
            case R.id.tab_mine:
                mReplace = 1;
                break;
        }
        FragmentUtils.hideAllShowFragment(mFragments.get(mReplace));
    };

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        HomeFragment homeFragment;
        MineFragment mineFragment;

        if (savedInstanceState == null) {
            homeFragment = HomeFragment.newInstance();
            mineFragment = MineFragment.newInstance();
        } else {
            mReplace = savedInstanceState.getInt(ACTIVITY_FRAGMENT_REPLACE);
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            homeFragment = (HomeFragment) FragmentUtils.findFragment(fm, HomeFragment.class);
            mineFragment = (MineFragment) FragmentUtils.findFragment(fm, MineFragment.class);

        }
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            mFragments.add(homeFragment);
            mFragments.add(mineFragment);
        }
        FragmentUtils.addFragments(getSupportFragmentManager(), mFragments, R.id.main_frame, 0);
        mBottomBar.setOnTabSelectListener(mOnTabSelectListener);

        updateApk();
    }

    private void updateApk() {
        TaskParams params = new TaskParams();
        MainTask.UpdateTask dbTask = new MainTask.UpdateTask(this, false);
        dbTask.execute(params);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }

    private static Boolean isExit = false;

    private void exitBy2Click() {

        if (!isExit) {
            isExit = true;
            Toasty.warning(this, "再按一次退出程序", Toast.LENGTH_SHORT, true).show();
            Timer tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            ArmsUtils.obtainAppComponentFromContext(this).appManager().appExit();
        }

    }
}
