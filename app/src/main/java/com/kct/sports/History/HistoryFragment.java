package com.kct.sports.History;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kct.sports.R;
import com.kct.sports.base.BaseFragment;
import com.kct.sports.base.NewApplication;
import com.kct.sports.base.SportsInfo;
import com.kct.sports.db.DBUtil;
import com.kct.sports.utils.ShowStringUitls;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class HistoryFragment extends BaseFragment {
    DBUtil dbUtil;
    /* access modifiers changed from: private */
    public DecimalFormat df;
    /* access modifiers changed from: private */
    public double history_all_distance;
    /* access modifiers changed from: private */
    public double history_all_kcal;
    /* access modifiers changed from: private */
    public int history_record_times;
    public Handler mHandle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    HistoryFragment.this.tv_history_all_distance.setText(String.valueOf(HistoryFragment.this.df.format(HistoryFragment.this.history_all_distance / 1000.0d)));
                    HistoryFragment.this.tv_history_all_kcal.setText(String.valueOf(HistoryFragment.this.df.format(HistoryFragment.this.history_all_kcal)));
                    HistoryFragment.this.tv_history_record_time.setText(String.valueOf(ShowStringUitls.formatTimer(HistoryFragment.this.history_record_times)));
                    HistoryFragment.this.tv_total_times.setText(String.valueOf(HistoryFragment.this.sportsInfos.size()));
                    break;
            }
            super.handleMessage(msg);
        }
    };
    /* access modifiers changed from: private */
    public ArrayList<SportsInfo> sportsInfos = null;
    /* access modifiers changed from: private */
    public TextView tv_history_all_distance;
    /* access modifiers changed from: private */
    public TextView tv_history_all_kcal;
    /* access modifiers changed from: private */
    public TextView tv_history_record_time;
    /* access modifiers changed from: private */
    public TextView tv_total_times;
    private View view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.history_main, container, false);
        initView(this.view);
        return this.view;
    }

    private void initEvent() {
        this.dbUtil = new DBUtil(getActivity());
        this.sportsInfos = this.dbUtil.queryAllInDB();
        this.df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH));
        this.df.setRoundingMode(RoundingMode.HALF_UP);
        readDBInfo();
    }

    private void initView(View view) {
        this.tv_history_all_distance = (TextView) view.findViewById(R.id.history_all_distance);
        this.tv_history_all_distance.setTypeface(NewApplication.mTypeface_date);
        this.tv_history_all_kcal = (TextView) view.findViewById(R.id.history_all_kcal);
        this.tv_history_all_kcal.setTypeface(NewApplication.mTypeface_date);
        this.tv_history_record_time = (TextView) view.findViewById(R.id.history_all_time);
        this.tv_history_record_time.setTypeface(NewApplication.mTypeface_date);
        this.tv_total_times = (TextView) view.findViewById(R.id.total_times);
        this.tv_total_times.setTypeface(NewApplication.mTypeface_date);
    }

    public void onResume() {
        this.history_all_distance = 0.0d;
        this.history_all_kcal = 0.0d;
        this.history_record_times = 0;
        initEvent();
        super.onResume();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void readDBInfo() {
        if (this.sportsInfos.size() > 0) {
            for (SportsInfo mSportsInfo : this.sportsInfos) {
                this.history_all_distance += mSportsInfo.getDistance();
                this.history_all_kcal += mSportsInfo.getKcal();
                this.history_record_times = (int) (((long) this.history_record_times) + mSportsInfo.getTimeTake());
            }
        } else {
            this.history_all_distance = 0.0d;
            this.history_all_kcal = 0.0d;
            this.history_record_times = 0;
        }
        this.tv_history_all_distance.setText(String.valueOf(this.df.format(this.history_all_distance / 1000.0d)));
        this.tv_history_all_kcal.setText(String.valueOf(this.df.format(this.history_all_kcal)));
        this.tv_history_record_time.setText(String.valueOf(ShowStringUitls.formatTimer(this.history_record_times)));
        this.tv_total_times.setText(String.valueOf(this.sportsInfos.size()));
    }
}
