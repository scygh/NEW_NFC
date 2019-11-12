package com.lianxi.dingtu.newsnfc.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.newsnfc.mvp.contract.ExpenseContract;
import com.lianxi.dingtu.newsnfc.mvp.model.ExpenseModel;

import dagger.Module;
import dagger.Provides;


@Module
public class ExpenseModule {
    private ExpenseContract.View view;

    /**
     * 构建ExpenseModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ExpenseModule(ExpenseContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ExpenseContract.View provideExpenseView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ExpenseContract.Model provideExpenseModel(ExpenseModel model) {
        return model;
    }
}