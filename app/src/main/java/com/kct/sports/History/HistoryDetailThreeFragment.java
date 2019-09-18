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
import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class HistoryDetailThreeFragment extends BaseFragment {
    public TextView BestWhichDistance;
    public TextView bestPace;
    public TextView bottom_item;
    public TextView bottom_unit;
    public TextView left_item;
    public TextView left_unit;
    public TextView lowestPace;
    public TextView lowestwhichDistance;
    public int postion;
    public TextView right_item;
    public TextView right_unit;
    private ArrayList<SportsInfo> sportsInfos = null;
    public TextView top_item;
    public TextView top_unit;
    public View view;

    public HistoryDetailThreeFragment(int postion, ArrayList<SportsInfo> sportsInfos) {
        this.postion = postion;
        this.sportsInfos = sportsInfos;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.detail_two, container, false);
        initView();
        initEvent();
        return this.view;
    }

    private void initEvent() {
        this.bestPace.setText(String.valueOf(((SportsInfo) this.sportsInfos.get(this.postion)).getBestPace_distance() / 60));
        this.BestWhichDistance.setText(String.valueOf(((SportsInfo) this.sportsInfos.get(this.postion)).getBestPace()));
        this.lowestPace.setText(String.valueOf(((SportsInfo) this.sportsInfos.get(this.postion)).getLowestPace_distance() / 60));
        this.lowestwhichDistance.setText(String.valueOf(((SportsInfo) this.sportsInfos.get(this.postion)).getLowestPace()));
        this.top_item.setText(R.string.best_pace);
        this.right_item.setText(R.string.which_km);
        this.left_item.setText(R.string.lowest_pace);
        this.bottom_item.setText(R.string.which_km);
        this.top_unit.setText(R.string.minute_per_km);
        this.right_unit.setText(R.string.kilometer);
        this.left_unit.setText(R.string.minute_per_km);
        this.bottom_unit.setText(R.string.kilometer);
    }

    private void initView() {
        this.bestPace = (TextView) this.view.findViewById(R.id.detail_two_speed);
        this.BestWhichDistance = (TextView) this.view.findViewById(R.id.detail_two_pace);
        this.lowestPace = (TextView) this.view.findViewById(R.id.detail_two_step_count);
        this.lowestwhichDistance = (TextView) this.view.findViewById(R.id.detail_two_energy);
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
