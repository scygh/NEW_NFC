package com.lianxi.dingtu.newsnfc.app.utils;

import android.content.Context;
import android.graphics.Typeface;


public class ITypeface {

    private static Typeface iconfont;

    public static Typeface getTypeface(Context context) {
        if (iconfont == null) {
            iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont/iconfont.ttf");
        }
        return iconfont;
    }
}
