package com.kct.sports.History;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.kct.sports.R;
import com.kct.sports.base.BaseFragment;
import com.kct.sports.base.SportsInfo;
import com.kct.sports.db.DBUtil;
import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class HistoryDeleteActivity extends BaseFragment {
    private Button cancel_btn;
    /* access modifiers changed from: private */
    public DBUtil dbUtil;
    /* access modifiers changed from: private */
    public Button delete_btn;
    /* access modifiers changed from: private */
    public LinearLayout delete_switch;
    private Button ok_btn;
    /* access modifiers changed from: private */
    public int position;
    /* access modifiers changed from: private */
    public ArrayList<SportsInfo> sportsInfos = null;
    private View view;

    @SuppressLint("ValidFragment")
    public HistoryDeleteActivity(int position, ArrayList<SportsInfo> sportsInfos) {
        this.position = position;
        this.sportsInfos = sportsInfos;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.delete_activity, container, false);
        initView();
        initEvent();
        return this.view;
    }

    private void initView() {
        this.dbUtil = new DBUtil(getActivity());
        this.delete_btn = (Button) this.view.findViewById(R.id.delete_btn);
        this.ok_btn = (Button) this.view.findViewById(R.id.ok);
        this.cancel_btn = (Button) this.view.findViewById(R.id.cancel);
        this.delete_switch = (LinearLayout) this.view.findViewById(R.id.delete_switch);
        this.delete_btn.setVisibility(View.VISIBLE);
        this.delete_switch.setVisibility(View.GONE);
    }

    private void initEvent() {
        this.delete_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                HistoryDeleteActivity.this.delete_btn.setVisibility(View.GONE);
                HistoryDeleteActivity.this.delete_switch.setVisibility(View.VISIBLE);
            }
        });
        this.ok_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                HistoryDeleteActivity.this.dbUtil.deleteDate(((SportsInfo) HistoryDeleteActivity.this.sportsInfos.get(HistoryDeleteActivity.this.position)).getId());
                HistoryDeleteActivity.this.dbUtil.deleteTraceDate(((SportsInfo) HistoryDeleteActivity.this.sportsInfos.get(HistoryDeleteActivity.this.position)).getStartTime(), ((SportsInfo) HistoryDeleteActivity.this.sportsInfos.get(HistoryDeleteActivity.this.position)).getGetTime());
                HistoryDeleteActivity.this.dbUtil.deleteHVSDate(((SportsInfo) HistoryDeleteActivity.this.sportsInfos.get(HistoryDeleteActivity.this.position)).getStartTime(), ((SportsInfo) HistoryDeleteActivity.this.sportsInfos.get(HistoryDeleteActivity.this.position)).getGetTime());
                HistoryDeleteActivity.this.dbUtil.deletePaceDate(((SportsInfo) HistoryDeleteActivity.this.sportsInfos.get(HistoryDeleteActivity.this.position)).getStartTime(), ((SportsInfo) HistoryDeleteActivity.this.sportsInfos.get(HistoryDeleteActivity.this.position)).getGetTime());
                HistoryDeleteActivity.this.getActivity().finish();
            }
        });
        this.cancel_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                HistoryDeleteActivity.this.getActivity().finish();
            }
        });
    }
}
