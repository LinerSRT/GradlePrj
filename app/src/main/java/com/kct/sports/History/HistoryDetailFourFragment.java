package com.kct.sports.History;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings.System;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kct.sports.R;
import com.kct.sports.base.BaseFragment;
import com.kct.sports.base.SportsInfo;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

@SuppressLint("ValidFragment")
public class HistoryDetailFourFragment extends BaseFragment {
    public TextView bottom_item;
    public TextView bottom_unit;
    public TextView horizontalSpeed;
    public TextView left_item;
    public TextView left_unit;
    public TextView maxHeartRate;
    public TextView minHeartRate;
    public int model;
    public int postion;
    public TextView right_item;
    public TextView right_unit;
    private ArrayList<SportsInfo> sportsInfos = null;
    public TextView top_item;
    public TextView top_unit;
    public TextView verticalSpeed;
    public View view;

    public HistoryDetailFourFragment(int postion, ArrayList<SportsInfo> sportsInfos) {
        this.postion = postion;
        this.sportsInfos = sportsInfos;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.detail_two, container, false);
        this.model = System.getInt(getActivity().getContentResolver(), "model", 4);
        initView();
        initEvent();
        return this.view;
    }

    private void initEvent() {
        double mSpeed = (((SportsInfo) this.sportsInfos.get(this.postion)).getDistance() / 1000.0d) / (((double) ((SportsInfo) this.sportsInfos.get(this.postion)).getTimeTake()) / 3600.0d);
        double maxSpeed = ((SportsInfo) this.sportsInfos.get(this.postion)).getMaxSpeed();
        double minSpeed = ((SportsInfo) this.sportsInfos.get(this.postion)).getMinSpeed();
        DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH));
        if (mSpeed > maxSpeed) {
            this.horizontalSpeed.setText(String.valueOf(df.format(mSpeed)));
        } else {
            this.horizontalSpeed.setText(String.valueOf(df.format(((SportsInfo) this.sportsInfos.get(this.postion)).getMaxSpeed())));
        }
        if (mSpeed < minSpeed) {
            this.verticalSpeed.setText(String.valueOf(df.format(mSpeed)));
        } else {
            this.verticalSpeed.setText(String.valueOf(df.format(((SportsInfo) this.sportsInfos.get(this.postion)).getMinSpeed())));
        }
        this.maxHeartRate.setText(String.valueOf(((SportsInfo) this.sportsInfos.get(this.postion)).getMaxHeatRate()));
        this.minHeartRate.setText(String.valueOf(((SportsInfo) this.sportsInfos.get(this.postion)).getMinHeatRate()));
        this.top_item.setText(R.string.max_speed);
        this.right_item.setText(R.string.min_speed);
        this.left_item.setText(R.string.max_heartRate);
        this.bottom_item.setText(R.string.min_heartRate);
        this.top_unit.setText(R.string.km_per_hours);
        this.right_unit.setText(R.string.km_per_hours);
        this.left_unit.setText(R.string.times_per_minute);
        this.bottom_unit.setText(R.string.times_per_minute);
    }

    private void initView() {
        this.horizontalSpeed = (TextView) this.view.findViewById(R.id.detail_two_speed);
        this.verticalSpeed = (TextView) this.view.findViewById(R.id.detail_two_pace);
        this.maxHeartRate = (TextView) this.view.findViewById(R.id.detail_two_step_count);
        this.minHeartRate = (TextView) this.view.findViewById(R.id.detail_two_energy);
        this.top_item = (TextView) this.view.findViewById(R.id.top_item);
        this.right_item = (TextView) this.view.findViewById(R.id.right_item);
        this.left_item = (TextView) this.view.findViewById(R.id.left_item);
        this.bottom_item = (TextView) this.view.findViewById(R.id.bottom_item);
        this.top_unit = (TextView) this.view.findViewById(R.id.top_unit);
        this.right_unit = (TextView) this.view.findViewById(R.id.right_unit);
        this.left_unit = (TextView) this.view.findViewById(R.id.left_unit);
        this.bottom_unit = (TextView) this.view.findViewById(R.id.bottom_unit);
    }
}
