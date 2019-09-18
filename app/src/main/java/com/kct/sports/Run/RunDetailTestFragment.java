package com.kct.sports.Run;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kct.sports.R;
import com.kct.sports.base.BaseFragment;
import com.kct.sports.model.SportModel;

@SuppressLint("ValidFragment")
public class RunDetailTestFragment extends BaseFragment {
    private TextView date_one;
    private TextView date_one_name;
    private TextView date_one_unit;
    private TextView date_three;
    private TextView date_three_name;
    private TextView date_three_unit;
    private TextView date_two;
    private TextView date_two_name;
    private TextView date_two_unit;
    int index;
    SportModel mSportModel;
    View view;

    public RunDetailTestFragment(SportModel mSportModel, int i) {
        this.mSportModel = mSportModel;
        this.index = i;
    }

    private void initview() {
        this.date_one = (TextView) this.view.findViewById(R.id.detail_one_data);
        this.date_one_unit = (TextView) this.view.findViewById(R.id.detail_one_data_unit);
        this.date_one_name = (TextView) this.view.findViewById(R.id.detail_one_data_name);
        this.date_two = (TextView) this.view.findViewById(R.id.detail_two_data);
        this.date_two_unit = (TextView) this.view.findViewById(R.id.detail_two_data_unit);
        this.date_two_name = (TextView) this.view.findViewById(R.id.detail_two_data_name);
        this.date_three = (TextView) this.view.findViewById(R.id.detail_three_data);
        this.date_three_unit = (TextView) this.view.findViewById(R.id.detail_three_data_unit);
        this.date_three_name = (TextView) this.view.findViewById(R.id.detail_three_data_name);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.sports_detail, container, false);
        initview();
        update();
        return this.view;
    }

    public void update() {
        String[] ss = this.mSportModel.getViewIndexes()[this.index];
        this.date_one_name.setText(this.mSportModel.getNameFromViewIndexes(ss[0]));
        this.date_one_unit.setText(this.mSportModel.getUnitFromViewIndexes(ss[0]));
        this.date_two_name.setText(this.mSportModel.getNameFromViewIndexes(ss[1]));
        this.date_two_unit.setText(this.mSportModel.getUnitFromViewIndexes(ss[1]));
        this.date_three_name.setText(this.mSportModel.getNameFromViewIndexes(ss[2]));
        this.date_three_unit.setText(this.mSportModel.getUnitFromViewIndexes(ss[2]));
        try {
            this.date_one.setText(this.mSportModel.getValueFromViewIndexes(ss[0]));
            this.date_two.setText(this.mSportModel.getValueFromViewIndexes(ss[1]));
            this.date_three.setText(this.mSportModel.getValueFromViewIndexes(ss[2]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
