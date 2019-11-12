package com.lianxi.dingtu.newsnfc.app.utils.ImageLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class FackMask extends View {

    private static final String TAG = "FackMask";

    private Paint paint;
    private RectF rect;
    Rect _rect;
    private int left;
    private int top;
    private int right;
    private int bottom;
    private String color;

    private Canvas canvas;

    private String text;
    private Path path;


    Paint paintFillAlpha;
    Paint paintText;
    Paint paintFill;

    public FackMask(Context context) {
        super(context);
    }

    public FackMask(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FackMask(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }


    /**
     * 外部调用的接口
     */
    public void setRect(String text, int left, int top, int right, int bottom,String color) {
        this.text = text;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.color = color;
        Log.d(TAG, "setRect: 控件");

        invalidate();//更新调用onDraw重新绘制
    }


    private void initPaint() {
        paint = new Paint();
        paintFillAlpha = new Paint();
        paintText = new Paint();
        paintFill = new Paint();
        path = new Path();
        left = 100;
        top = 100;
        right = 300;
        bottom = 300;
        color="#3B85F5";
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.parseColor(color));
        paint.setStyle(Paint.Style.STROKE);//设置空心
        paint.setTextSize(35);
        paint.setStrokeWidth(5);

        int fontSize = 38;
        int labelPadding = 5;
        int labelHeight = 46 + 2 * labelPadding;

        paintFillAlpha.setStyle(Paint.Style.FILL);
        paintFillAlpha.setColor(Color.parseColor(color));
        paintFillAlpha.setAlpha(50);

        paintFill = new Paint();
        paintFill.setStyle(Paint.Style.FILL);
        paintFill.setColor(Color.parseColor(color));

        paintText.setColor(Color.WHITE);
        paintText.setTextAlign(Paint.Align.LEFT);
        paintText.setTextSize(fontSize);

        rect = new RectF(left, top, right, bottom);
        path.addRect(rect, Path.Direction.CW);
        canvas.drawRect(rect, paint);
        _rect = new Rect(left, top, right, bottom);
        canvas.drawRect(rect, paintFillAlpha);
        canvas.drawRect(new Rect(_rect.left, _rect.top, _rect.right, _rect.top + labelHeight), paintFill);
        canvas.drawText(text,
                rect.left + labelPadding,
                rect.top + fontSize + labelPadding, paintText);

//        canvas.drawTextOnPath(text, path, 0, 0, paint);

    }

}

