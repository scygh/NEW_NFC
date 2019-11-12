package com.lianxi.dingtu.newsnfc.app.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.lianxi.dingtu.newsnfc.R;


/**
 * Created by Admin on 2015-01-28
 */
public class ShowImageActivity extends FragmentActivity implements OnClickListener {

    private ImageView mBack;
    private ZoomImageView mZoomImageView;
    private String mPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        findById();
        initIntentDatas();
        initDatas();

    }


    private void findById() {
        mBack = findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mZoomImageView = findViewById(R.id.zoomImage);
    }

    private void initIntentDatas() {
        mPath = getIntent().getStringExtra("path");

    }

    private void initDatas() {
        if (!TextUtils.isEmpty(mPath)) {
            mZoomImageView.setImageURI(Uri.parse(mPath));
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;

        }
    }

    public Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
