package com.kct.sports.History;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
public class HistoryDetailFiveFragment extends BaseFragment {
    public TextView aveStep;
    public TextView aveStepSpeed;
    public TextView bottom_item;
    public TextView bottom_unit;
    public TextView left_item;
    public TextView left_unit;
    public TextView maxStep;
    public TextView maxStepSpeed;
    public int postion;
    public TextView right_item;
    public TextView right_unit;
    private ArrayList<SportsInfo> sportsInfos = null;
    public TextView top_item;
    public TextView top_unit;
    public View view;

    @SuppressLint("ValidFragment")
    public HistoryDetailFiveFragment(int postion, ArrayList<SportsInfo> sportsInfos) {
        this.postion = postion;
        this.sportsInfos = sportsInfos;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.detail_two, container, false);
        initView();
        initEvent();
        return this.view;
    }

    private void initEvent() {
        DecimalFormat df = new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.ENGLISH));
        this.maxStepSpeed.setText(String.valueOf(((SportsInfo) this.sportsInfos.get(this.postion)).getMaxStepSpeed()));
        this.aveStepSpeed.setText(String.valueOf(((SportsInfo) this.sportsInfos.get(this.postion)).getMinStepSpeed()));
        this.maxStep.setText(String.valueOf(df.format(((SportsInfo) this.sportsInfos.get(this.postion)).getMaxStepWidth() * 100.0d)));
        this.aveStep.setText(String.valueOf(df.format(((SportsInfo) this.sportsInfos.get(this.postion)).getMinStepWidth() * 100.0d)));
        this.top_item.setText(R.string.max_stepSpeed);
        this.right_item.setText(R.string.min_stepSpeed);
        this.left_item.setText(R.string.maxStep);
        this.bottom_item.setText(R.string.minStep);
        this.top_unit.setText(R.string.step_per_minuter);
        this.right_unit.setText(R.string.step_per_minuter);
        this.left_unit.setText(R.string.cm);
        this.bottom_unit.setText(R.string.cm);
    }

    private void initView() {
        this.maxStepSpeed = (TextView) this.view.findViewById(R.id.detail_two_speed);
        this.aveStepSpeed = (TextView) this.view.findViewById(R.id.detail_two_pace);
        this.maxStep = (TextView) this.view.findViewById(R.id.detail_two_step_count);
        this.aveStep = (TextView) this.view.findViewById(R.id.detail_two_energy);
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
