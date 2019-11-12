package com.lianxi.dingtu.newsnfc.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.newsnfc.R;
import com.lianxi.dingtu.newsnfc.app.utils.ImageUtil;
import com.lianxi.dingtu.newsnfc.di.component.DaggerImageComponent;
import com.lianxi.dingtu.newsnfc.mvp.contract.ImageContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.EMGoodsTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.UserInfoTo;
import com.lianxi.dingtu.newsnfc.mvp.presenter.ImagePresenter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.functions.Consumer;
import me.shaohui.advancedluban.Luban;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/17/2019 15:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ImageActivity extends BaseActivity<ImagePresenter> implements ImageContract.View {
    @BindView(R.id.image_view) ImageView imageView;
    private String image_url;
    private EMGoodsTo mEMGoodsTo;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerImageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_image; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("采集模式");
        image_url = getIntent().getStringExtra("startWithUri");
        mEMGoodsTo = (EMGoodsTo) getIntent().getSerializableExtra("materials");
        if (!TextUtils.isEmpty(image_url))
            GlideArms.with(this).load(image_url)
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(imageView);


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


    @OnClick({R.id.submit_baidu, R.id.submit_1001, R.id.submit_6672}) public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit_baidu:
//                Intent intent = new Intent(ImageActivity.this, ImageAnalysisActivity.class);
//                intent.putExtra("startWithUri", image_url);
//                startActivity(intent);
//                finish();
                break;
            case R.id.submit_1001:
                OkGo.<String>get("http://machine_api.dt128.com/Api/Token/GetToken")
                        .tag(this)
                        .headers("AccessToken", "")
                        .headers("content-type", "application/json;charset:utf-8")
                        .params("account", "菜品测试1")
                        .params("password", "123456")
                        .params("oldAccessToken", "")
                        .execute(new StringCallback() {
                            @Override public void onSuccess(Response<String> response) {
                                Log.e(TAG, "onSuccess: 1001-->>" + response.body());
                                String string = response.body();
                                try {
                                    JSONObject object = new JSONObject(string);
                                    if (object != null) {
                                        String str = object.getString("Content");
                                        Gson gson = new Gson();
                                        UserInfoTo userInfoTo = gson.fromJson(str, UserInfoTo.class);
                                        Log.e(TAG, "onSuccess: UserInfoTo-->>" + JSON.toJSONString(userInfoTo));
                                        if (TextUtils.isEmpty(image_url) || mEMGoodsTo == null)
                                            return;
                                        File file1 = new File(image_url);

                                        List<File> fils = new ArrayList<>();
                                        fils.add(file1);
                                        Luban.compress(ImageActivity.this, fils)
                                                .setMaxSize(100)                // 限制最终图片大小（单位：Kb）
                                                .setCompressFormat(Bitmap.CompressFormat.JPEG)            // 自定义压缩图片格式，目前只支持：JEPG和WEBP，因为png不支持压缩图片品质
                                                .putGear(Luban.CUSTOM_GEAR)     // 使用 CUSTOM_GEAR 压缩模式
                                                .asListObservable()
                                                .subscribe(new Consumer<List<File>>() {
                                                    @Override public void accept(List<File> files) throws Exception {

                                                        OkGo.<String>post("http://api.goods.dt128.com/DishGoodsAdd")
                                                                .tag(ImageActivity.this)
                                                                .headers("AccessToken", userInfoTo.getAccessToken())
                                                                .headers("content-type", "application/json;charset:utf-8")
                                                                .params("GoodsNo", mEMGoodsTo.getGoodsNo())
                                                                .params("Base64Img", ImageUtil.getImgStr(files.get(0).getPath()))
                                                                .params("GoodsName", mEMGoodsTo.getGoodsName())
                                                                .execute(new StringCallback() {
                                                                    @Override public void onSuccess(Response<String> response) {
                                                                        Log.e(TAG, "onSuccess: 1001提交-->>" + response.body());
                                                                        String string = response.body();
                                                                        try {
                                                                            JSONObject object = new JSONObject(string);
                                                                            if (object.has("ReturnCode")) {
                                                                                String str = object.getString("ReturnCode");
                                                                                if ("0".equals(str)) {
                                                                                    Toasty.success(ImageActivity.this, "上传成功", Toast.LENGTH_SHORT, true).show();
                                                                                    finish();
                                                                                } else {
                                                                                    Toasty.warning(ImageActivity.this, "上传失败：" + string, Toast.LENGTH_SHORT, true).show();
                                                                                }
                                                                            } else {
                                                                                Toasty.warning(ImageActivity.this, "上传失败：" + string, Toast.LENGTH_SHORT, true).show();
                                                                            }

                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }

                                                                    }

                                                                    @Override public void onError(Response<String> response) {
                                                                        super.onError(response);
                                                                        Log.e(TAG, "onError: 1001提交-->>" + response.body());
                                                                        Toasty.error(ImageActivity.this, "上传失败：" + response.body(), Toast.LENGTH_SHORT, true).show();
                                                                        finish();
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        });


                break;
            case R.id.submit_6672:
                OkGo.<String>get("http://machine_api.dt128.com/Api/Token/GetToken")
                        .tag(this)
                        .headers("AccessToken", "")
                        .headers("content-type", "application/json;charset:utf-8")
                        .params("account", "菜品测试2")
                        .params("password", "123456")
                        .params("oldAccessToken", "")
                        .execute(new StringCallback() {
                            @Override public void onSuccess(Response<String> response) {
                                Log.e(TAG, "onSuccess: 优客香米-->>" + response.body());
                                String string = response.body();
                                try {
                                    JSONObject object = new JSONObject(string);
                                    if (object != null) {
                                        String str = object.getString("Content");
                                        Gson gson = new Gson();
                                        UserInfoTo userInfoTo = gson.fromJson(str, UserInfoTo.class);
                                        if (TextUtils.isEmpty(image_url) || mEMGoodsTo == null)
                                            return;
                                        File file2 = new File(image_url);

                                        List<File> fils2 = new ArrayList<>();
                                        fils2.add(file2);
                                        Luban.compress(ImageActivity.this, fils2)
                                                .setMaxSize(100)                // 限制最终图片大小（单位：Kb）
                                                .setCompressFormat(Bitmap.CompressFormat.JPEG)            // 自定义压缩图片格式，目前只支持：JEPG和WEBP，因为png不支持压缩图片品质
                                                .putGear(Luban.CUSTOM_GEAR)     // 使用 CUSTOM_GEAR 压缩模式
                                                .asListObservable()
                                                .subscribe(new Consumer<List<File>>() {
                                                    @Override public void accept(List<File> files) throws Exception {

                                                        OkGo.<String>post("http://api.goods.dt128.com/DishGoodsAdd")
                                                                .tag(ImageActivity.this)
                                                                .headers("AccessToken", userInfoTo.getAccessToken())
                                                                .headers("content-type", "application/json;charset:utf-8")
                                                                .params("GoodsNo", mEMGoodsTo.getGoodsNo())
                                                                .params("Base64Img", ImageUtil.getImgStr(files.get(0).getPath()))
                                                                .params("GoodsName", mEMGoodsTo.getGoodsName())
                                                                .execute(new StringCallback() {
                                                                    @Override public void onSuccess(Response<String> response) {
                                                                        Log.e(TAG, "onSuccess: 优客香米提交==" + response.body());
                                                                        String string = response.body();
                                                                        try {
                                                                            JSONObject object = new JSONObject(string);
                                                                            if (object.has("ReturnCode")) {
                                                                                String str = object.getString("ReturnCode");
                                                                                if ("0".equals(str)) {
                                                                                    Toasty.success(ImageActivity.this, "上传成功", Toast.LENGTH_SHORT, true).show();
                                                                                    finish();
                                                                                } else {
                                                                                    Toasty.warning(ImageActivity.this, "上传失败：" + string, Toast.LENGTH_SHORT, true).show();
                                                                                }
                                                                            } else {
                                                                                Toasty.warning(ImageActivity.this, "上传失败：" + string, Toast.LENGTH_SHORT, true).show();
                                                                            }

                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    @Override public void onError(Response<String> response) {
                                                                        super.onError(response);
                                                                        Log.e(TAG, "onError: 优客香米提交==" + response.body());
                                                                        Toasty.error(ImageActivity.this, "上传失败：" + response.body(), Toast.LENGTH_SHORT, true).show();
                                                                        finish();
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        });

                break;
        }
    }
}
