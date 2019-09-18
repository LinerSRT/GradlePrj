package com.kct.sports.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.kct.sports.R;

public class HorizontalViewPager extends ViewPager {
    private int indicatorHighColor = -1;
    private int indicatorMargin = 20;
    private int indicatorNormalColor = -3355444;
    private int indicatorPadding = 8;
    private int indicatorRadius = 6;
    private Paint paint = new Paint();
    private boolean scrollble = true;
    private boolean showIndicator = true;
    private int showIndicatorCount = 2;

    public HorizontalViewPager(Context context) {
        super(context);
    }

    public HorizontalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.HorizontalViewPager, 0, 0);
        this.showIndicator = a.getBoolean(R.styleable.HorizontalViewPager_showIndicator, true);
        this.showIndicatorCount = a.getInteger(R.styleable.HorizontalViewPager_showCountIndicator, -1);
        this.indicatorHighColor = a.getColor(R.styleable.HorizontalViewPager_indicatorHighColor, this.indicatorHighColor);
        this.indicatorNormalColor = a.getColor(R.styleable.HorizontalViewPager_indicatorNormalColor, this.indicatorNormalColor);
        this.indicatorPadding = a.getDimensionPixelOffset(R.styleable.HorizontalViewPager_indicatorPadding, this.indicatorPadding);
        this.indicatorRadius = a.getDimensionPixelOffset(R.styleable.HorizontalViewPager_indicatorRadius, this.indicatorRadius);
        this.indicatorMargin = a.getDimensionPixelOffset(R.styleable.HorizontalViewPager_indicatorMargin, this.indicatorMargin);
        a.recycle();
    }

    public void setShowIndicator(boolean isShowIndicator) {
        this.showIndicator = isShowIndicator;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.showIndicator) {
            drawCycle(canvas);
        }
    }

    private void drawCycle(Canvas canvas) {
        canvas.save();
        canvas.translate((float) getScrollX(), (float) getScrollY());
        int count = 0;
        if (getAdapter() != null) {
            if (this.showIndicatorCount > 0) {
                count = this.showIndicatorCount;
            } else {
                count = getAdapter().getCount();
            }
        }
        int select = getCurrentItem();
        int itemWidth = (this.indicatorRadius * 2) + this.indicatorPadding;
        int x = ((getWidth() / 2) - (((itemWidth * count) - this.indicatorPadding) / 2)) + this.indicatorRadius;
        int y = (getHeight() - this.indicatorMargin) - this.indicatorRadius;
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Style.FILL);
        for (int i = 0; i < count; i++) {
            if (select == i) {
                this.paint.setColor(this.indicatorHighColor);
                canvas.drawCircle((float) ((itemWidth * i) + x), (float) (y + 10), (float) ((this.indicatorRadius * 3) / 4), this.paint);
            } else {
                this.paint.setColor(this.indicatorNormalColor);
                canvas.drawCircle((float) ((itemWidth * i) + x), (float) (y + 10), (float) (this.indicatorRadius / 2), this.paint);
            }
        }
        canvas.restore();
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (this.scrollble) {
            return super.onTouchEvent(ev);
        }
        return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.scrollble) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    public void setScrollble(boolean scrollble) {
        this.scrollble = scrollble;
    }
}
