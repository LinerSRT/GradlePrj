package com.kct.sports.mianui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MenuVirticalRecyleView extends MyRecyclerView {
    public static int FLING_DISABLE = -1;
    private boolean isForApp = true;
    private MenuVerticalBg mMenuSportBg;
    /* access modifiers changed from: private */
    public MenuVirticalAdapter mMenuVirticalAdapter;
    /* access modifiers changed from: private */
    public MenuVerticalLayoutManager mVerticalLayoutManager;
    private int velocityScale = 3;

    private class MenuVerticalLayoutManager extends LinearLayoutManager {
        public MenuVerticalLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public void onLayoutChildren(Recycler arg0, State arg1) {
            super.onLayoutChildren(arg0, arg1);
            MenuVirticalRecyleView.this.freshChildrens(1);
        }

        public void offsetChildrenHorizontal(int dx) {
            super.offsetChildrenHorizontal(dx);
        }

        public void offsetChildrenVertical(int dy) {
            MenuVirticalRecyleView.this.freshChildrens(dy);
        }

        public void onScrollStateChanged(int state) {
            super.onScrollStateChanged(state);
            if (state == 0 && MenuVirticalRecyleView.this.mVerticalLayoutManager.findFirstVisibleItemPosition() > 0) {
                scrollerByItem();
            }
        }

        public void scrollerToLooper() {
            int firstPosition = findFirstVisibleItemPosition();
            int size = MenuVirticalRecyleView.this.mMenuVirticalAdapter.getMenus().size();
            if (firstPosition <= size) {
                if (getOrientation() == 0) {
                    scrollToPositionWithOffset(firstPosition + size, getChildAt(0).getLeft());
                } else {
                    scrollToPositionWithOffset(firstPosition + size, getChildAt(0).getTop());
                }
            } else if (firstPosition <= (size * 2) - 1) {
            } else {
                if (getOrientation() == 0) {
                    scrollToPositionWithOffset((firstPosition % size) + size, getChildAt(0).getLeft());
                } else {
                    scrollToPositionWithOffset((firstPosition % size) + size, getChildAt(0).getTop());
                }
            }
        }

        public void scrollerByItem() {
            View view = MenuVirticalRecyleView.this.findChildViewUnder((float) (getWidth() / 2), (float) (getHeight() / 2));
            if (view != null && getOrientation() != 0) {
                int offset = view.getTop() + (view.getHeight() / 2);
                if (offset >= 1) {
                    MenuVirticalRecyleView.this.smoothScrollBy(0, offset - (getHeight() / 2));
                }
            }
        }
    }

    public MenuVirticalRecyleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public void initLayoutManager(int oration, boolean isResver) {
        this.mVerticalLayoutManager = new MenuVerticalLayoutManager(getContext(), oration, isResver);
        setLayoutManager(this.mVerticalLayoutManager);
        this.mVerticalLayoutManager.scrollToPosition(1);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        int size = Math.min(getMeasuredHeight(), getMeasuredWidth());
        setMeasuredDimension(size, size);
    }

    public void setBg(MenuVerticalBg view) {
        if (!this.isForApp) {
            this.mMenuSportBg = view;
        }
    }

    public void setAdapter(Adapter adapter) {
        if (adapter instanceof MenuVirticalAdapter) {
            this.mMenuVirticalAdapter = (MenuVirticalAdapter) adapter;
            this.isForApp = this.mMenuVirticalAdapter.isApp();
            super.setAdapter(adapter);
            return;
        }
        throw new IllegalArgumentException("MenuVirticalRecyleView can only set a adapter instance of MenuVirticalAdapter");
    }

    public boolean onTouchEvent(MotionEvent arg0) {
        ensureToLooper();
        return super.onTouchEvent(arg0);
    }

    public boolean fling(int velocityX, int velocityY) {
        if (FLING_DISABLE == this.velocityScale) {
            return false;
        }
        return super.fling(velocityX / this.velocityScale, velocityY / this.velocityScale);
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        ensureToLooper();
    }

    private void ensureToLooper() {
        if (!(this.mMenuVirticalAdapter == null || !this.mMenuVirticalAdapter.isLoop() || this.mVerticalLayoutManager == null)) {
            this.mVerticalLayoutManager.scrollerToLooper();
        }
    }

    /* access modifiers changed from: private */
    public void freshChildrens(int dy) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            MenuVerticalItem item = (MenuVerticalItem) getChildAt(i);
            float absOffset = Math.abs(MenuVerticalItem.getCenterY(getHeight(), item.getTop(), item.getBottom()));
            item.reSize(absOffset);
            item.offsetTopAndBottom(dy);
            if (this.mMenuSportBg != null) {
                this.mMenuSportBg.freshBg((AppMenu) item.getTag(), 1.0f - (absOffset / ((float) item.getHeight())));
            }
        }
    }
}
