package com.kct.sports.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.kct.sports.base.NewApplication;

public class LantingTextView extends AppCompatTextView {
    public LantingTextView(Context context) {
        super(context);
        init(context);
    }

    public LantingTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public LantingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setTypeface(NewApplication.mTypeface_date_name);
    }

    public boolean isFocused() {
        return true;
    }
}
