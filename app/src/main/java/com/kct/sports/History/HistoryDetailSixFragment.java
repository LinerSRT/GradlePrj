package com.kct.sports.History;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings.System;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kct.sports.R;
import com.kct.sports.base.BaseFragment;
import com.kct.sports.base.SportsInfo;
import com.kct.sports.base.Trace;
import com.kct.sports.db.DBUtil;
import com.kct.sports.utils.SportDataUtils;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class HistoryDetailSixFragment extends BaseFragment {
    public TextView bottom_item;
    public TextView bottom_unit;
    private DBUtil dbUtil;
    public TextView left_item;
    public TextView left_unit;
    private ArrayList<Integer> mAltitude = new ArrayList();
    private FragmentActivity mContext;
    private SportDataUtils mSportData;
    public int model;
    public TextView needO2;
    public int postion;
    public TextView right_item;
    public TextView right_unit;
    private ArrayList<SportsInfo> sportsInfos = null;
    public TextView sumDown;
    public TextView sumUp;
    public TextView top_item;
    public TextView top_unit;
    private List<Trace> trace_points;
    public TextView train;
    public View view;

    public HistoryDetailSixFragment(int postion, ArrayList<SportsInfo> sportsInfos) {
        this.postion = postion;
        this.sportsInfos = sportsInfos;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.detail_two, container, false);
        this.model = System.getInt(getActivity().getContentResolver(), "model", 4);
        this.mContext = getActivity();
        this.dbUtil = new DBUtil(getActivity());
        this.mSportData = new SportDataUtils(this.mContext);
        initView();
        initEvent();
        return this.view;
    }

    private void initEvent() {
        this.mAltitude.clear();
        if (5 == ((SportsInfo) this.sportsInfos.get(this.postion)).getModel() || 4 == ((SportsInfo) this.sportsInfos.get(this.postion)).getModel()) {
            this.trace_points = this.dbUtil.getTrace(((SportsInfo) this.sportsInfos.get(this.postion)).getStartTime(), ((SportsInfo) this.sportsInfos.get(this.postion)).getGetTime());
            for (int i = 0; i < this.trace_points.size(); i++) {
                this.mAltitude.add(Integer.valueOf((int) ((Trace) this.trace_points.get(i)).getAltitude()));
            }
            for (int i2 = 0; i2 < this.mAltitude.size(); i2++) {
                if (((Integer) this.mAltitude.get(i2)).intValue() == -1) {
                    this.mAltitude.remove(i2);
                }
            }
            this.sumUp.setText(this.mSportData.getAltitudeUp(this.mAltitude) + "");
            TextView textView = this.sumDown;
            StringBuilder stringBuilder = new StringBuilder();
            SportDataUtils sportDataUtils = this.mSportData;
            textView.setText(stringBuilder.append(SportDataUtils.getAltitudeDown(this.mAltitude)).append("").toString());
        } else {
            this.sumUp.setText(getResources().getString(R.string.no_date));
            this.sumDown.setText(getResources().getString(R.string.no_date));
        }
        this.needO2.setText(String.valueOf(((SportsInfo) this.sportsInfos.get(this.postion)).getNeedO2Max()));
        this.train.setText(String.valueOf(((SportsInfo) this.sportsInfos.get(this.postion)).getTrainingIntensity()));
        this.top_item.setText(R.string.stored_climbing);
        this.right_item.setText(R.string.stored_decline);
        this.left_item.setText(R.string.max_o2);
        this.bottom_item.setText(R.string.training_intensity);
        this.top_unit.setText(R.string.meter);
        this.right_unit.setText(R.string.meter);
        this.left_unit.setText(R.string.ml_kg_minute);
        this.bottom_unit.setText(R.string.level);
    }

    private void initView() {
        this.sumUp = (TextView) this.view.findViewById(R.id.detail_two_speed);
        this.sumDown = (TextView) this.view.findViewById(R.id.detail_two_pace);
        this.needO2 = (TextView) this.view.findViewById(R.id.detail_two_step_count);
        this.train = (TextView) this.view.findViewById(R.id.detail_two_energy);
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
