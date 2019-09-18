package com.kct.sports.view;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import com.kct.sports.R;

public class StatusIcon extends AppCompatImageView {
    private static final String TAG = StatusIcon.class.getSimpleName();
    private AnimClickListem mAnimClickListem = new AnimClickListem();

    private class AnimClickListem implements OnClickListener {
        private Animation animation;
        /* access modifiers changed from: private */
        public OnClickListener listen = null;

        private class ClickAnimListen implements AnimationListener {
            /* synthetic */ ClickAnimListen(AnimClickListem this$1, ClickAnimListen clickAnimListen) {
                this();
            }

            private ClickAnimListen() {
            }

            public void onAnimationEnd(Animation animation) {
                if (AnimClickListem.this.listen != null) {
                    AnimClickListem.this.listen.onClick(StatusIcon.this);
                }
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }
        }

        public AnimClickListem() {
            this.animation = AnimationUtils.loadAnimation(StatusIcon.this.getContext(), R.anim.icon_click);
            this.animation.setAnimationListener(new ClickAnimListen(this, null));
        }

        public void setListen(OnClickListener listen) {
            this.listen = listen;
        }

        public void onClick(View v) {
            StatusIcon.this.startAnimation(this.animation);
        }
    }

    public StatusIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
    }

    public final void setOnClickListener(OnClickListener l) {
        this.mAnimClickListem.setListen(l);
        super.setOnClickListener(this.mAnimClickListem);
    }
}
