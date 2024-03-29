package com.kct.sports.setting;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public abstract class AbstractWheelTextAdapter extends AbstractWheelAdapter {
    protected Context context;
    protected int emptyItemResourceId;
    protected LayoutInflater inflater;
    protected int itemResourceId;
    protected int itemTextResourceId;
    private int textColor;
    private int textSize;
    private Typeface typeface;

    public abstract CharSequence getItemText(int i);

    protected AbstractWheelTextAdapter(Context context) {
        this(context, -1);
    }

    protected AbstractWheelTextAdapter(Context context, int itemResource) {
        this(context, itemResource, 0);
    }

    protected AbstractWheelTextAdapter(Context context, int itemResource, int itemTextResource) {
        this.textColor = -10987432;
        this.textSize = 18;
        this.typeface = null;
        this.context = context;
        this.itemResourceId = itemResource;
        this.itemTextResourceId = itemTextResource;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setTextColor(int drawable) {
        this.textColor = drawable;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public View getItem(int index, View convertView, ViewGroup parent) {
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
            textView.setText(text);
            if (this.itemResourceId == -1) {
                configureTextView(textView);
            }
        }
        return convertView;
    }

    public View getEmptyItem(View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getView(this.emptyItemResourceId, parent);
        }
        if (this.emptyItemResourceId == -1 && (convertView instanceof TextView)) {
            configureTextView((TextView) convertView);
        }
        return convertView;
    }

    /* access modifiers changed from: protected */
    public void configureTextView(TextView view) {
        view.setTextColor(this.textColor);
        view.setGravity(17);
        view.setTextSize((float) this.textSize);
        view.setTypeface(this.typeface);
        view.setEllipsize(TruncateAt.END);
        view.setLines(1);
    }

    public TextView getTextView(View view, int textResource) {
        if (textResource == 0) {
            try {
                if (view instanceof TextView) {
                    return (TextView) view;
                }
            } catch (ClassCastException e) {
                Log.e("AbstractWheelAdapter", "You must supply a resource ID for a TextView");
                throw new IllegalStateException("AbstractWheelAdapter requires the resource ID to be a TextView", e);
            }
        }
        if (textResource != 0) {
            return (TextView) view.findViewById(textResource);
        }
        return null;
    }

    public View getView(int resource, ViewGroup parent) {
        switch (resource) {
            case -1:
                return new TextView(this.context);
            case 0:
                return null;
            default:
                return this.inflater.inflate(resource, parent, false);
        }
    }
}
