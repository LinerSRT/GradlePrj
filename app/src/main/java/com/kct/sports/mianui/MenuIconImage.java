package com.kct.sports.mianui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView.ScaleType;
import com.kct.sports.base.NewApplication;

public class MenuIconImage extends StatusIcon {
    private static final String TAG = MenuIconImage.class.getSimpleName();
    private Paint mBitmapPaint;
    private Paint mBoundPaint;
    private int mCenter;
    private Matrix mMatrix;
    private int mRadius;
    private int mSize;

    @SuppressLint({"NewApi"})
    public MenuIconImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mBitmapPaint = new Paint();
        this.mBoundPaint = new Paint();
        this.mMatrix = new Matrix();
        setScaleType(ScaleType.FIT_XY);
    }

    public MenuIconImage(Context context) {
        this(context, null);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mSize = Math.min(getMeasuredHeight(), getMeasuredWidth());
        if (NewApplication.width == 240) {
            setMeasuredDimension(60, 60);
        } else if (NewApplication.width == 360) {
            setMeasuredDimension(80, 80);
        }
        this.mCenter = this.mSize / 2;
        this.mRadius = this.mSize / 2;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
