package com.kct.sports.mianui;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;

public class ScrollerTextView extends AppCompatTextView {
    private boolean needFit;

    public ScrollerTextView(Context context) {
        this(context, null);
    }

    public ScrollerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.needFit = true;
        initView();
    }

    /* access modifiers changed from: protected */
    public void initView() {
        setEllipsize(TruncateAt.MARQUEE);
        setSingleLine(true);
        setMarqueeRepeatLimit(-1);
        setFocusable(true);
        setFocusableInTouchMode(false);
    }

    public boolean isFocused() {
        if (isInTouchMode()) {
        }
        return isAttachedToWindow();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /* access modifiers changed from: protected */
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.needFit = true;
    }
}
