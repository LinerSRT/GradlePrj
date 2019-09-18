package com.kct.sports.setting;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.kct.sports.R;
import com.kct.sports.utils.RingInsist;

public class TimeToRunActivity extends Activity {
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    TimeToRunActivity.this.ringstop();
                    return;
                default:
                    return;
            }
        }
    };
    private ImageButton mImageButton;
    private WakeLock mWakeLock = null;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_to_run);
        wakeUpAndUnlock(this);
        ringStart();
        this.handler.sendEmptyMessageDelayed(0, 35000);
        this.mImageButton = (ImageButton) findViewById(R.id.cancleAlarm);
        this.mImageButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                TimeToRunActivity.this.ringstop();
                TimeToRunActivity.this.finish();
            }
        });
    }

    public void ringStart() {
        try {
            RingInsist.startRing(this);
        } catch (Exception e) {
        }
    }

    private void wakeUpAndUnlock(Context context) {
        this.mWakeLock = ((PowerManager) context.getSystemService(POWER_SERVICE)).newWakeLock(268435482, "bright:2");
        this.mWakeLock.acquire();
    }

    private void releaseWakeLock() {
        if (this.mWakeLock != null) {
            this.mWakeLock.release();
            this.mWakeLock = null;
        }
    }

    public void ringstop() {
        RingInsist.stopRing();
        this.handler.removeMessages(0);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        releaseWakeLock();
        this.handler.removeMessages(0);
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onUserLeaveHint() {
        ringstop();
        super.onUserLeaveHint();
    }
}
