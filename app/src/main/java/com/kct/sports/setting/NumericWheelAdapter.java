package com.kct.sports.setting;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NumericWheelAdapter extends AbstractWheelTextAdapter {
    private String format;
    private String label;
    private Context mContext;
    private Typeface mOriginalHoursTypeface;
    private Typeface mOriginalMinutesTypeface;
    private int maxValue;
    private int minValue;
    private int multiple;

    public NumericWheelAdapter(Context context, int minValue, int maxValue, String format) {
        super(context);
        this.mContext = context;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.format = format;
    }

    public CharSequence getItemText(int index) {
        if (index < 0 || index >= getItemsCount()) {
            return null;
        }
        int value;
        CharSequence format;
        if (this.multiple != 0) {
            value = this.minValue + (this.multiple * index);
        } else {
            value = this.minValue + index;
        }
        if (this.format != null) {
            format = String.format(this.format, new Object[]{Integer.valueOf(value)});
        } else {
            format = Integer.toString(value);
        }
        return format;
    }

    public int getItemsCount() {
        return (this.maxValue - this.minValue) + 1;
    }

    public View getItem(int index, View convertView, ViewGroup parent) {
        if (this.mOriginalHoursTypeface == null) {
        }
        if (this.mOriginalMinutesTypeface == null) {
        }
        if (index < 0 || index >= getItemsCount()) {
            return null;
        }
        if (convertView == null) {
            convertView = getView(this.itemResourceId, parent);
        }
        TextView textView = getTextView(convertView, this.itemTextResourceId);
        if (textView != null) {
            CharSequence text = getItemText(index);
            if (text == null) {
                text = "";
            }
            textView.setTypeface(this.mOriginalHoursTypeface);
            textView.setText(text + this.label);
            textView.setPadding(-32, -4, -32, -4);
            if (this.itemResourceId == -1) {
                configureTextView(textView);
            }
        }
        return convertView;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
