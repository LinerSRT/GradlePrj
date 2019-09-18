package com.kct.sports.OtherFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.System;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.kct.sports.R;
import com.kct.sports.Trace.LocationSvc;
import com.kct.sports.base.BaseFragment;
import com.kct.sports.provider.DateProviderService;
import java.util.Random;

public class SportsStopFragment extends BaseFragment {
    private Button StopBtn;
    /* access modifiers changed from: private */
    public Context mContext;
    private TextView mSaveTip;
    int state = 0;
    private int[] tipString = {R.string.tip_string1, R.string.tip_string2, R.string.tip_string3, R.string.tip_string4, R.string.tip_string5};
    private int tipStringIndex = -1;
    private TextView tv_tipString;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sports_stop, container, false);
        this.mContext = getActivity();
        this.StopBtn = (Button) view.findViewById(R.id.stop);
        this.tv_tipString = (TextView) view.findViewById(R.id.tv_tipString);
        this.mSaveTip = (TextView) view.findViewById(R.id.tv_save_tip);
        initEvent();
        return view;
    }

    private void initEvent() {
        this.tipStringIndex = new Random().nextInt(this.tipString.length);
        if (this.tipStringIndex >= 0 && this.tipStringIndex < this.tipString.length) {
            this.tv_tipString.setText(this.tipString[this.tipStringIndex]);
        }
        this.StopBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //System.putInt(SportsStopFragment.this.mContext.getContentResolver(), "isRunning", 0);
                SportsStopFragment.this.mContext.sendBroadcast(new Intent("com.kct.sports.start.run"));
                SportsStopFragment.this.mContext.stopService(new Intent(SportsStopFragment.this.mContext, DateProviderService.class));
                SportsStopFragment.this.mContext.stopService(new Intent(SportsStopFragment.this.mContext, LocationSvc.class));
                Intent intent = new Intent("com.kct.spots.timerEnd");
                if (SportsStopFragment.this.enoughDate()) {
                    ProgressDialog mProgressDialog = new ProgressDialog(SportsStopFragment.this.mContext);
                    mProgressDialog.setMessage(SportsStopFragment.this.getResources().getString(R.string.wait_to_save));
                    mProgressDialog.show();
                    intent.putExtra("end", true);
                } else {
                    intent.putExtra("end", false);
                }
                SportsStopFragment.this.mContext.sendBroadcast(intent);
            }
        });
    }

    public void onResume() {
        super.onResume();
        if (enoughDate()) {
            this.mSaveTip.setText(getResources().getString(R.string.save_tip));
        } else {
            this.mSaveTip.setText(getResources().getString(R.string.cannot_save_tip));
        }
    }

    public boolean enoughDate() {
        if (Float.valueOf(System.getFloat(this.mContext.getContentResolver(), "distance", 0.0f)).floatValue() <= 20.0f) {
            return false;
        }
        return true;
    }
}
