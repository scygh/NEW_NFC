package com.lianxi.dingtu.newsnfc.di.module;

import com.lianxi.dingtu.newsnfc.mvp.contract.ImageContract;
import com.lianxi.dingtu.newsnfc.mvp.model.ImageModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/17/2019 15:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class ImageModule {

    @Binds
    abstract ImageContract.Model bindImageModel(ImageModel model);
}