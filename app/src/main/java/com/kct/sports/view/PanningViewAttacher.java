package com.kct.sports.view;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import java.lang.ref.WeakReference;

public class PanningViewAttacher implements OnGlobalLayoutListener {
    int i = 1;
    private ValueAnimator mCurrentAnimator;
    /* access modifiers changed from: private */
    public long mCurrentPlayTime;
    private RectF mDisplayRect = new RectF();
    private long mDuration;
    private WeakReference<ImageView> mImageView;
    private boolean mIsPanning;
    /* access modifiers changed from: private */
    public boolean mIsPortrait;
    private int mIvBottom;
    private int mIvLeft;
    private int mIvRight;
    private int mIvTop;
    private LinearInterpolator mLinearInterpolator;
    /* access modifiers changed from: private */
    public Matrix mMatrix;
    private long mTotalTime;
    private ViewTreeObserver mViewTreeObserver;
    private Way mWay;

    private enum Way {
        R2L,
        L2R,
        T2B,
        B2T
    }

    public PanningViewAttacher(ImageView imageView, long duration) {
        boolean z = true;
        if (imageView == null) {
            throw new IllegalArgumentException("imageView must not be null");
        } else if (hasDrawable(imageView)) {
            this.mLinearInterpolator = new LinearInterpolator();
            this.mDuration = duration;
            this.mImageView = new WeakReference(imageView);
            this.mViewTreeObserver = imageView.getViewTreeObserver();
            this.mViewTreeObserver.addOnGlobalLayoutListener(this);
            setImageViewScaleTypeMatrix(imageView);
            this.mMatrix = imageView.getImageMatrix();
            if (this.mMatrix == null) {
                this.mMatrix = new Matrix();
            }
            if (imageView.getResources().getConfiguration().orientation != 1) {
                z = false;
            }
            this.mIsPortrait = z;
            update();
        } else {
            throw new IllegalArgumentException("drawable must not be null");
        }
    }

    public void update() {
        this.mWay = null;
        this.mTotalTime = 0;
        this.mCurrentPlayTime = 0;
        getImageView().post(new Runnable() {
            public void run() {
                PanningViewAttacher.this.scale();
                PanningViewAttacher.this.refreshDisplayRect();
            }
        });
    }

    public boolean isPanning() {
        return this.mIsPanning;
    }

    public void startPanning() {
        if (!this.mIsPanning) {
            this.mIsPanning = true;
            getImageView().post(new Runnable() {
                public void run() {
                    PanningViewAttacher.this.animate_();
                }
            });
        }
    }

    public void stopPanning() {
        if (this.mIsPanning) {
            this.mIsPanning = false;
            Log.d("PanningViewAttacher", "panning animation stopped by user");
            if (this.mCurrentAnimator != null) {
                this.mCurrentAnimator.removeAllListeners();
                this.mCurrentAnimator.cancel();
                this.mCurrentAnimator = null;
            }
            this.mTotalTime += this.mCurrentPlayTime;
            Log.d("PanningViewAttacher", "mTotalTime : " + this.mTotalTime);
        }
    }

    public final void cleanup() {
        if (this.mImageView != null) {
            getImageView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
        this.mViewTreeObserver = null;
        stopPanning();
        this.mImageView = null;
    }

    public final ImageView getImageView() {
        ImageView imageView = null;
        if (this.mImageView != null) {
            imageView = (ImageView) this.mImageView.get();
        }
        if (imageView != null) {
            return imageView;
        }
        cleanup();
        throw new IllegalStateException("ImageView no longer exists. You should not use this PanningViewAttacher any more.");
    }

    private int getDrawableIntrinsicHeight() {
        return getImageView().getDrawable().getIntrinsicHeight();
    }

    private int getDrawableIntrinsicWidth() {
        return getImageView().getDrawable().getIntrinsicWidth();
    }

    private int getImageViewWidth() {
        return getImageView().getWidth();
    }

    private int getImageViewHeight() {
        return getImageView().getHeight();
    }

    private static void setImageViewScaleTypeMatrix(ImageView imageView) {
        if (imageView != null && !(imageView instanceof PanningView)) {
            imageView.setScaleType(ScaleType.MATRIX);
        }
    }

    private static boolean hasDrawable(ImageView imageView) {
        return (imageView == null || imageView.getDrawable() == null) ? false : true;
    }

    public void onGlobalLayout() {
        ImageView imageView = getImageView();
        if (imageView != null) {
            int top = imageView.getTop();
            int right = imageView.getRight();
            int bottom = imageView.getBottom();
            int left = imageView.getLeft();
            if (top != this.mIvTop || bottom != this.mIvBottom || left != this.mIvLeft || right != this.mIvRight) {
                update();
                this.mIvTop = top;
                this.mIvRight = right;
                this.mIvBottom = bottom;
                this.mIvLeft = left;
            }
        }
    }

    /* access modifiers changed from: private */
    public void animate_() {
        refreshDisplayRect();
        if (this.mWay == null) {
            this.mWay = this.mIsPortrait ? Way.R2L : Way.B2T;
        }
        Log.d("PanningViewAttacher", "mWay : " + this.mWay);
        Log.d("PanningViewAttacher", "mDisplayRect : " + this.mDisplayRect);
        long remainingDuration = this.mDuration - this.mTotalTime;
        if (this.mIsPortrait) {
            if (this.mWay == Way.R2L) {
                animate(this.mDisplayRect.left, this.mDisplayRect.left - (this.mDisplayRect.right - ((float) getImageViewWidth())), remainingDuration);
            } else {
                animate(this.mDisplayRect.left, 0.0f, remainingDuration);
            }
        } else if (this.mWay == Way.B2T) {
            animate(this.mDisplayRect.top, this.mDisplayRect.top - (this.mDisplayRect.bottom - ((float) getImageViewHeight())), remainingDuration);
        } else {
            animate(this.mDisplayRect.top, 0.0f, remainingDuration);
        }
    }

    /* access modifiers changed from: private */
    public void changeWay() {
        if (this.mWay == Way.R2L) {
            this.mWay = Way.L2R;
        } else if (this.mWay == Way.L2R) {
            this.mWay = Way.R2L;
        } else if (this.mWay == Way.T2B) {
            this.mWay = Way.B2T;
        } else if (this.mWay == Way.B2T) {
            this.mWay = Way.T2B;
        }
        this.mCurrentPlayTime = 0;
        this.mTotalTime = 0;
    }

    private void animate(float start, float end, long duration) {
        Log.d("PanningViewAttacher", "startPanning : " + start + " to " + end + ", in " + duration + "ms");
        this.mCurrentAnimator = ValueAnimator.ofFloat(start, end);
        this.mCurrentAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = ((Float) animation.getAnimatedValue()).floatValue();
                PanningViewAttacher.this.mMatrix.reset();
                PanningViewAttacher.this.applyScaleOnMatrix();
                if (!PanningViewAttacher.this.mIsPortrait) {
                    PanningViewAttacher.this.mMatrix.postTranslate(0.0f, value / 2.0f);
                } else if (PanningViewAttacher.this.i == 1) {
                    PanningViewAttacher.this.mMatrix.postTranslate(value, value / 2.0f);
                } else {
                    PanningViewAttacher.this.mMatrix.postTranslate(0.0f, value / 2.0f);
                }
                PanningViewAttacher.this.refreshDisplayRect();
                PanningViewAttacher.this.mCurrentPlayTime = animation.getCurrentPlayTime();
                PanningViewAttacher.this.setCurrentImageMatrix();
            }
        });
        this.mCurrentAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                Log.d("PanningViewAttacher", "animation has finished, startPanning in the other way");
                PanningViewAttacher.this.changeWay();
                PanningViewAttacher.this.animate_();
                PanningViewAttacher.this.mMatrix.postTranslate(0.0f, 0.0f);
            }

            public void onAnimationCancel(Animator animation) {
                Log.d("PanningViewAttacher", "panning animation canceled");
            }
        });
        this.mCurrentAnimator.setDuration(duration);
        this.mCurrentAnimator.setInterpolator(this.mLinearInterpolator);
        this.mCurrentAnimator.start();
    }

    /* access modifiers changed from: private */
    public void setCurrentImageMatrix() {
        getImageView().setImageMatrix(this.mMatrix);
        getImageView().invalidate();
        getImageView().requestLayout();
    }

    /* access modifiers changed from: private */
    public void refreshDisplayRect() {
        this.mDisplayRect.set(0.0f, 0.0f, (float) getDrawableIntrinsicWidth(), (float) getDrawableIntrinsicHeight());
        this.mMatrix.mapRect(this.mDisplayRect);
    }

    /* access modifiers changed from: private */
    public void scale() {
        this.mMatrix.reset();
        applyScaleOnMatrix();
        setCurrentImageMatrix();
    }

    /* access modifiers changed from: private */
    public void applyScaleOnMatrix() {
        float scaleFactor = ((float) (this.mIsPortrait ? getImageViewHeight() : getImageViewWidth())) / ((float) (this.mIsPortrait ? getDrawableIntrinsicHeight() : getDrawableIntrinsicWidth()));
        this.mMatrix.postScale(scaleFactor, scaleFactor);
    }
}
