package com.lianxi.dingtu.newsnfc.app.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.mvp.IPresenter;

public abstract class MyFragment<P extends IPresenter> extends BaseFragment<P> {
    private boolean isInitView = false;
    private boolean isVisible = false;

    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isInitView = true;
        isCanLoadData();
        return initView(inflater, container, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见，获取该标志记录下来
        if(isVisibleToUser){
            isVisible = true;
            isCanLoadData();
        }else{
            isVisible = false;
        }
    }

    private void isCanLoadData(){
        //所以条件是view初始化完成并且对用户可见
        if(isInitView && isVisible ){
            lazyLoad();

            //防止重复加载数据
            isInitView = false;
            isVisible = false;
        }
    }

    protected abstract void lazyLoad();
}
