package com.kct.sports.OtherFragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.System;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kct.sports.R;
import com.kct.sports.Trace.LocationSvc;
import com.kct.sports.base.NewApplication;
import com.kct.sports.provider.DateProviderService;

import java.util.Random;

public class SportsSwitchActivity extends Activity {
    private Button cancelBtn;
    private Button okBtn;
    private LinearLayout sWitchPage = null;
    int state = 0;
    private int[] tipString = {R.string.tip_string1, R.string.tip_string2, R.string.tip_string3, R.string.tip_string4, R.string.tip_string5};
    private int tipStringIndex = -1;
    private TextView tv_summary;
    private TextView tv_tipString_switch;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.sports_swtich);
        NewApplication.getInstance().addActivity(this);
        this.okBtn = (Button) findViewById(R.id.ok);
        this.cancelBtn = (Button) findViewById(R.id.cancel);
        this.tv_tipString_switch = (TextView) findViewById(R.id.tv_tipString_switch);
        this.tv_summary = (TextView) findViewById(R.id.tv_summary);
        initEvent();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    private void initEvent() {
        this.tipStringIndex = new Random().nextInt(this.tipString.length);
        if (this.tipStringIndex >= 0 && this.tipStringIndex < this.tipString.length) {
            this.tv_tipString_switch.setText(this.tipString[this.tipStringIndex]);
        }
        this.okBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ProgressDialog mProgressDialog = new ProgressDialog(SportsSwitchActivity.this);
                mProgressDialog.setMessage(SportsSwitchActivity.this.getResources().getString(R.string.wait_to_save));
                mProgressDialog.show();
                //System.putInt(SportsSwitchActivity.this.getContentResolver(), "isRunning", 0);
                SportsSwitchActivity.this.sendBroadcast(new Intent("com.kct.sports.start.run"));
                SportsSwitchActivity.this.stopService(new Intent(SportsSwitchActivity.this, DateProviderService.class));
                SportsSwitchActivity.this.stopService(new Intent(SportsSwitchActivity.this, LocationSvc.class));
                SportsSwitchActivity.this.turnGPSOff();
                Intent intent = new Intent("com.kct.spots.timerEnd");
                if (SportsSwitchActivity.this.enoughDate()) {
                    intent.putExtra("end", true);
                } else {
                    intent.putExtra("end", false);
                }
                SportsSwitchActivity.this.sendBroadcast(intent);
            }
        });
        this.cancelBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ProgressDialog mProgressDialog = new ProgressDialog(SportsSwitchActivity.this);
                mProgressDialog.setMessage(SportsSwitchActivity.this.getResources().getString(R.string.delete_records));
                mProgressDialog.show();
                SportsSwitchActivity.this.sendBroadcast(new Intent("com.kct.sports.provider.delete_hvs_and_pace"));
                SportsSwitchActivity.this.finish();
            }
        });
        if (!enoughDate()) {
            this.tv_summary.setText(getResources().getString(R.string.cannot_save_tip));
        }
    }

    public boolean enoughDate() {
        if (Float.valueOf(System.getFloat(getContentResolver(), "distance", 0.0f)).floatValue() <= 20.0f) {
            return false;
        }
        return true;
    }

    public void turnGPSOff() {
    }
}
