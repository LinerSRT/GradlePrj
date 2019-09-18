package com.kct.sports.mianui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.util.AttributeSet;
import android.view.View;

public class MyRecyclerView extends RecyclerView {
    private static final String TAG = MyRecyclerView.class.getSimpleName();
    private AdapterDataObserver mAdapterDataObserver = new AdapterDataObserver() {
        public void onChanged() {
            super.onChanged();
            MyRecyclerView.this.checkEmpty();
        }
    };
    private View mEmptyView;

    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAdapter(Adapter adapter) {
        Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(this.mAdapterDataObserver);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(this.mAdapterDataObserver);
        }
        checkEmpty();
    }

    /* access modifiers changed from: private */
    public void checkEmpty() {
        int i = 8;
        if (this.mEmptyView != null && getAdapter() != null) {
            int i2;
            boolean emptyViewVisible = getAdapter().getItemCount() == 0;
            View view = this.mEmptyView;
            if (emptyViewVisible) {
                i2 = 0;
            } else {
                i2 = 8;
            }
            view.setVisibility(i2);
            if (!emptyViewVisible) {
                i = 0;
            }
            setVisibility(i);
        }
    }

    public void requestLayout() {
        super.requestLayout();
        if (this.mEmptyView != null && getAdapter() != null) {
            if (getAdapter().getItemCount() == 0) {
                this.mEmptyView.setVisibility(VISIBLE);
            } else {
                this.mEmptyView.setVisibility(GONE);
            }
        }
    }
}
