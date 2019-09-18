package com.kct.sports.History;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kct.sports.R;
import com.kct.sports.base.BaseFragment;
import com.kct.sports.base.SportsInfo;
import com.kct.sports.utils.ShowStringUitls;
import com.kct.sports.utils.SportDataUtils;
import com.kct.sports.utils.Utils;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

@SuppressLint("ValidFragment")
public class HistoryDetailTwoFragment extends BaseFragment {
    public TextView bottom_item;
    public TextView bottom_unit;
    public TextView detail_two_energy;
    public TextView detail_two_heatRate;
    public TextView detail_two_pace;
    public TextView detail_two_speed;
    private DecimalFormat df;
    public TextView left_item;
    public TextView left_unit;
    private FragmentActivity mContext;
    private SportDataUtils mSportData;
    public int postion;
    public TextView right_item;
    public TextView right_unit;
    private ArrayList<SportsInfo> sportsInfos = null;
    public TextView top_item;
    public TextView top_unit;
    public View view;

    public HistoryDetailTwoFragment(int postion, ArrayList<SportsInfo> sportsInfos) {
        this.postion = postion;
        this.sportsInfos = sportsInfos;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.detail_two, container, false);
        this.mContext = getActivity();
        this.mSportData = new SportDataUtils(this.mContext);
        initView();
        initEvent();
        return this.view;
    }

    private void initEvent() {
        this.df = new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.ENGLISH));
        this.df.setRoundingMode(RoundingMode.HALF_UP);
        double mSpeed = (((SportsInfo) this.sportsInfos.get(this.postion)).getDistance() / 1000.0d) / (((double) ((SportsInfo) this.sportsInfos.get(this.postion)).getTimeTake()) / 3600.0d);
        long mTimeTake2 = ((SportsInfo) this.sportsInfos.get(this.postion)).getTimeTake();
        double getDistance = Utils.decimalTo2(((SportsInfo) this.sportsInfos.get(this.postion)).getDistance() * 0.001d, 3);
        this.detail_two_speed.setText(String.valueOf(Utils.decimalTo2(mSpeed, 2)));
        this.detail_two_pace.setText(String.valueOf(ShowStringUitls.formatPace(Integer.valueOf(this.mSportData.DoubToInt(((double) mTimeTake2) / getDistance)).intValue())));
        this.detail_two_heatRate.setText(String.valueOf(((SportsInfo) this.sportsInfos.get(this.postion)).getHeatRate()));
        this.detail_two_energy.setText(String.valueOf(this.df.format(((SportsInfo) this.sportsInfos.get(this.postion)).getKcal())));
        this.top_item.setText(R.string.velocity);
        this.right_item.setText(R.string.pace);
        this.left_item.setText(R.string.heart_rate);
        this.bottom_item.setText(R.string.energy_cost);
        this.top_unit.setText(R.string.km_per_hours);
        this.right_unit.setText(R.string.minute_per_km);
        this.left_unit.setText(R.string.times_per_minute);
        this.bottom_unit.setText(R.string.kcal);
    }

    private void initView() {
        this.detail_two_speed = (TextView) this.view.findViewById(R.id.detail_two_speed);
        this.detail_two_pace = (TextView) this.view.findViewById(R.id.detail_two_pace);
        this.detail_two_heatRate = (TextView) this.view.findViewById(R.id.detail_two_step_count);
        this.detail_two_energy = (TextView) this.view.findViewById(R.id.detail_two_energy);
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
