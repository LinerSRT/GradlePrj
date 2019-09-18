package com.kct.sports.History;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.kct.sports.R;
import com.kct.sports.base.BaseFragment;
import com.kct.sports.base.SportsInfo;
import com.kct.sports.db.DBUtil;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class HistoryLVFragment extends BaseFragment {
    DBUtil dbUtil;
    private DecimalFormat df;
    private View footView;
    /* access modifiers changed from: private */
    public ListView history_lv = null;
    public Handler mHandle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    HistoryLVFragment.this.history_lv.setVisibility(View.VISIBLE);
                    HistoryLVFragment.this.no_record_tv.setVisibility(View.GONE);
                    HistoryLVFragment.this.mHistoryInfoAdapter = new HistoryInfoAdapter(HistoryLVFragment.this.sportsInfos, HistoryLVFragment.this.getActivity());
                    HistoryLVFragment.this.history_lv.setAdapter(HistoryLVFragment.this.mHistoryInfoAdapter);
                    HistoryLVFragment.this.mHistoryInfoAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    HistoryLVFragment.this.history_lv.setVisibility(View.GONE);
                    HistoryLVFragment.this.no_record_tv.setVisibility(View.VISIBLE);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    /* access modifiers changed from: private */
    public HistoryInfoAdapter mHistoryInfoAdapter;
    /* access modifiers changed from: private */
    public TextView no_record_tv;
    /* access modifiers changed from: private */
    public ArrayList<SportsInfo> sportsInfos = null;
    private View view;

    private class ReadStepCountTask extends AsyncTask<Void, Void, Void> {
        /* synthetic */ ReadStepCountTask(HistoryLVFragment this$0, ReadStepCountTask readStepCountTask) {
            this();
        }

        private ReadStepCountTask() {
        }

        /* access modifiers changed from: protected|varargs */
        public Void doInBackground(Void... params) {
            HistoryLVFragment.this.readDBInfo();
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.history_listview, container, false);
        initView(this.view);
        initEvent();
        return this.view;
    }

    private void initEvent() {
        this.dbUtil = new DBUtil(getActivity());
        this.df = new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.ENGLISH));
        this.df.setRoundingMode(RoundingMode.HALF_UP);
    }

    private void initView(View view) {
        this.history_lv = (ListView) view.findViewById(R.id.history_lv);
        this.no_record_tv = (TextView) view.findViewById(R.id.no_record_tv);
        this.footView = LayoutInflater.from(getActivity()).inflate(R.layout.listview_footerview, null);
        this.history_lv.addFooterView(this.footView);
        this.footView.setEnabled(false);
    }

    public void onResume() {
        this.sportsInfos = this.dbUtil.queryAllInDB();
        int size = this.sportsInfos.size();
        for (int i = 0; i < size / 2; i++) {
            SportsInfo spi2 = (SportsInfo) this.sportsInfos.get((size - i) - 1);
            this.sportsInfos.set((size - i) - 1, (SportsInfo) this.sportsInfos.get(i));
            this.sportsInfos.set(i, spi2);
        }
        new ReadStepCountTask(this, null).execute(new Void[0]);
        this.history_lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (position < HistoryLVFragment.this.sportsInfos.size()) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("sportsInfos", HistoryLVFragment.this.sportsInfos);
                    bundle.putInt("position", position);
                    Intent intent = new Intent(HistoryLVFragment.this.getActivity(), DetailHorizontalActivity.class);
                    intent.putExtras(bundle);
                    HistoryLVFragment.this.startActivity(intent);
                }
            }
        });
        super.onResume();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void readDBInfo() {
        if (this.sportsInfos.size() > 0) {
            this.mHandle.sendEmptyMessage(1);
        } else {
            this.mHandle.sendEmptyMessage(2);
        }
    }
}
