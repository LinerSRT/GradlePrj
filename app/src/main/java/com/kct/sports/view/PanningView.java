package com.kct.sports.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.kct.sports.R;


public class PanningView extends AppCompatImageView {
    private final PanningViewAttacher mAttacher;
    private int mPanningDurationInMs;

    public PanningView(Context context) {
        this(context, null);
    }

    public PanningView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public PanningView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        readStyleParameters(context, attr);
        super.setScaleType(ScaleType.MATRIX);
        this.mAttacher = new PanningViewAttacher(this, (long) this.mPanningDurationInMs);
    }

    private void readStyleParameters(Context context, AttributeSet attributeSet) {
        this.mPanningDurationInMs = 5000;

    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        stopUpdateStartIfNecessary();
    }

    public void setImageResource(int resId) {
        super.setImageResource(resId);
        stopUpdateStartIfNecessary();
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        stopUpdateStartIfNecessary();
    }

    private void stopUpdateStartIfNecessary() {
        if (this.mAttacher != null) {
            boolean wasPanning = this.mAttacher.isPanning();
            this.mAttacher.stopPanning();
            this.mAttacher.update();
            if (wasPanning) {
                this.mAttacher.startPanning();
            }
        }
    }

    public void setScaleType(ScaleType scaleType) {
        throw new UnsupportedOperationException("only matrix scaleType is supported");
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        this.mAttacher.cleanup();
        super.onDetachedFromWindow();
    }

    public void startPanning() {
        this.mAttacher.startPanning();
    }

    public void stopPanning() {
        this.mAttacher.stopPanning();
    }
}
