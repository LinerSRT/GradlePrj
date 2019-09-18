package com.kct.sports.OtherFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.app.Activity;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.kct.sports.R;
import com.kct.sports.Run.HorizontalMainActivity;
import com.kct.sports.base.NewApplication;
import com.kct.sports.provider.DateProviderService;
import com.kct.sports.provider.StepCountService;

public class ReadyStartActivity extends Activity {
    private ImageView animIV;
    private AnimationDrawable animationDrawable;
    private int duration = 0;
    /* access modifiers changed from: private */
    public Vibrator mVibrator;
    private StepCountService mstepCountService = null;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(1);
        setContentView(R.layout.ready_start);
        initView();
        initEvent();
    }

    private void initEvent() {
        NewApplication.getInstance().addActivity(this);
        this.mVibrator = (Vibrator) getApplication().getSystemService(Context.VIBRATOR_SERVICE);
        this.mVibrator.vibrate(new long[]{100, 10, 1000, 10, 1000, 10, 1000}, -1);
        this.animIV.setImageResource(R.drawable.ready_anima);
        this.animationDrawable = (AnimationDrawable) this.animIV.getDrawable();
        this.animationDrawable.start();
        for (int i = 0; i < this.animationDrawable.getNumberOfFrames(); i++) {
            this.duration += this.animationDrawable.getDuration(i);
        }
        startService(new Intent(this, DateProviderService.class));
        new Handler().postDelayed(new Runnable() {
            public void run() {
                ReadyStartActivity.this.mVibrator.cancel();
                ReadyStartActivity.this.startActivity(new Intent(ReadyStartActivity.this, HorizontalMainActivity.class));
                ReadyStartActivity.this.finish();
            }
        }, (long) this.duration);
    }

    private void initView() {
        this.animIV = (ImageView) findViewById(R.id.animation_iv);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
