package com.kct.sports.mianui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kct.sports.R;

public class MenuVerticalItem extends LinearLayout {
    private static final String TAG = MenuVerticalItem.class.getSimpleName();
    private boolean isAppItem;
    private MenuVirticalRecyleView mParent;
    private int mParentHeight;
    private int translationMax;

    public MenuVerticalItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MenuItem);
        this.isAppItem = a.getBoolean(R.styleable.MenuItem_is_app_item, true);
        this.translationMax = a.getDimensionPixelSize(R.styleable.MenuItem_item_translation_max, 70);
        a.recycle();
        setWillNotDraw(false);
    }

    public void setParentHeight(int parentHeight) {
        this.mParentHeight = parentHeight;
    }


    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            if (this.isAppItem) {
                setPivotX((float) getLeft());
            } else {
                setPivotX((float) getRight());
            }
            setPivotY((float) (getHeight() / 2));
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void offsetTopAndBottom(int offset) {
        super.offsetTopAndBottom(offset);
    }

    public void reSize(float offset) {
        float scal = getSCal(offset, getHeight());
        float translation = getTranslationX(offset, this.isAppItem);
    }

    public static float getCenterY(int parentHeight, int top, int bottom) {
        return (float) (((parentHeight - top) - bottom) / 2);
    }

    public static float getSCal(float offset, int height) {
        return (float) (1.0d - ((((double) offset) * 0.44d) / ((double) height)));
    }

    public float getTranslationX(float offset, boolean isAppItem) {
        if (isAppItem) {
            return (((float) this.translationMax) * offset) / ((float) getHeight());
        }
        return 0.0f - ((((float) this.translationMax) * offset) / ((float) getHeight()));
    }
}
