package com.lianxi.dingtu.newsnfc.mvp.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lianxi.dingtu.newsnfc.mvp.model.entity.SearchTo;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;


public class BaiduResultView extends View {
    private float sizeRatio;
    private List<SearchTo.ResultBean> mDetectResultModelList;
    private Point originPt = new Point();
    private int imgWidth;
    private int imgHeight;

    private Handler handler;

    public void setHandler(Handler mHandler) {
        handler = mHandler;
    }

    public BaiduResultView(Context context) {
        super(context);
    }

    public BaiduResultView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaiduResultView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }


    public void setRectListInfo(List<SearchTo.ResultBean> modelList, int width, int height) {
        imgWidth = width;
        imgHeight = height;
        mDetectResultModelList = modelList;
        handler.post(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });
    }

    public void clear() {
        mDetectResultModelList = null;
        handler.post(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });
    }

    private void preCaculate() {
        float ratio = (float) getMeasuredWidth() / (float) getMeasuredHeight();
        float ratioBitmap = (float) imgWidth / (float) imgHeight;
        // | |#####| |模式
        if (ratioBitmap < ratio) {
            sizeRatio = (float) getMeasuredHeight() / (float) imgHeight;
            int x = (int) (getMeasuredWidth() - sizeRatio * imgWidth) / 2;
            originPt.set(x, 0);
        } else {
            // ------------
            //
            // ------------
            // ############
            // ------------
            //
            // ------------
            sizeRatio = (float) getMeasuredWidth() / (float) imgWidth;
            int y = (int) (getMeasuredHeight() - sizeRatio * imgHeight) / 2;
            originPt.set(0, y);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 实时识别的时候第一次渲染
        if (mDetectResultModelList == null) {
            super.onDraw(canvas);
            return;
        }

        preCaculate();

        int stokeWidth = 5;

        int fontSize = 38;
        int labelPadding = 5;
        int labelHeight = 46 + 2 * labelPadding;
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#3B85F5"));
        paint.setStrokeWidth(stokeWidth);
        paint.setStyle(Paint.Style.STROKE);

        Paint paintFillAlpha = new Paint();
        paintFillAlpha.setStyle(Paint.Style.FILL);
        paintFillAlpha.setColor(Color.parseColor("#3B85F5"));
        paintFillAlpha.setAlpha(50);

        Paint paintFill = new Paint();
        paintFill.setStyle(Paint.Style.FILL);
        paintFill.setColor(Color.parseColor("#3B85F5"));

        Paint paintText = new Paint();
        paintText.setColor(Color.WHITE);
        paintText.setTextAlign(Paint.Align.LEFT);
        paintText.setTextSize(fontSize);
        DecimalFormat df = new DecimalFormat("0.00");
        for (int i = 0; i < mDetectResultModelList.size(); i++) {
            SearchTo.ResultBean model = mDetectResultModelList.get(i);
            Rect rect = getBounds(sizeRatio, originPt, model.getLocation());
            canvas.drawRect(rect, paint);
            canvas.drawRect(rect, paintFillAlpha);
            canvas.drawRect(new Rect(rect.left, rect.top, rect.right,
                    rect.top + labelHeight), paintFill);
            canvas.drawText(getTextName(model.getDishes().get(0).getBrief()) + String.format(" 置信度 %.2f", model.getDishes().get(0).getScore()),
                    rect.left + labelPadding,
                    rect.top + fontSize + labelPadding, paintText);
        }

        super.onDraw(canvas);
    }

    public Rect getBounds(float ratio, Point origin, SearchTo.ResultBean.LocationBean locationBean) {
        return new Rect((int) (origin.x + locationBean.getLeft() * ratio),
                (int) (origin.y + locationBean.getTop() * ratio),
                (int) (origin.x + (locationBean.getLeft() + locationBean.getWidth()) * ratio),
                (int) (origin.y + (locationBean.getTop() + locationBean.getHeight()) * ratio));
    }

    public String getTextName(String string) {
        try {
            JSONObject json = new JSONObject(string);
            return json.getString("GoodsName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "未知";

    }
}
