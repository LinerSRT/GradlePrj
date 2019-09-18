package com.kct.sports.History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.kct.sports.R;
import com.kct.sports.base.NewApplication;
import com.kct.sports.base.SportsInfo;
import com.kct.sports.utils.ShowStringUitls;
import com.kct.sports.utils.SportDataUtils;
import com.kct.sports.utils.Utils;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HistoryInfoAdapter extends BaseAdapter {
    private DecimalFormat df;
    private LayoutInflater inflater;
    private Context mContext;
    private SportDataUtils mSportData;
    private SimpleDateFormat sdf;
    private ArrayList<SportsInfo> sportsInfos = new ArrayList();

    class ViewHolder {
        TextView tv_history_distance;
        TextView tv_history_kcal_used;
        TextView tv_history_pace;
        TextView tv_history_time;
        TextView tv_model;
        TextView tv_recordTime;

        ViewHolder() {
        }
    }

    public HistoryInfoAdapter(ArrayList<SportsInfo> sportsInfos, Context mContext) {
        this.sportsInfos = sportsInfos;
        this.mContext = mContext;
        this.mSportData = new SportDataUtils(mContext);
        this.inflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return this.sportsInfos.size();
    }

    public Object getItem(int position) {
        return Integer.valueOf(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.history_item, null);
            viewHolder.tv_recordTime = (TextView) convertView.findViewById(R.id.record_time);
            viewHolder.tv_history_distance = (TextView) convertView.findViewById(R.id.history_distance);
            viewHolder.tv_history_time = (TextView) convertView.findViewById(R.id.history_time);
            viewHolder.tv_model = (TextView) convertView.findViewById(R.id.model);
            viewHolder.tv_history_pace = (TextView) convertView.findViewById(R.id.history_pace);
            viewHolder.tv_history_kcal_used = (TextView) convertView.findViewById(R.id.history_kcal_used);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        this.df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH));
        this.sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.ENGLISH);
        viewHolder.tv_recordTime.setText(String.valueOf(this.sdf.format(new Date(((SportsInfo) this.sportsInfos.get(position)).getGetTime()))));
        viewHolder.tv_recordTime.setTypeface(NewApplication.mTypeface_DIN1451EF);
        viewHolder.tv_history_distance.setText(String.valueOf(this.df.format(((SportsInfo) this.sportsInfos.get(position)).getDistance() / 1000.0d)));
        viewHolder.tv_history_distance.setTypeface(NewApplication.mTypeface_DIN1451EF);
        viewHolder.tv_history_time.setText(ShowStringUitls.formatTimer((int) ((SportsInfo) this.sportsInfos.get(position)).getTimeTake()));
        viewHolder.tv_history_time.setTypeface(NewApplication.mTypeface_DIN1451EF);
        viewHolder.tv_model.setText(ShowStringUitls.getModel(this.mContext, ((SportsInfo) this.sportsInfos.get(position)).getModel()));
        long mTimeTake2 = ((SportsInfo) this.sportsInfos.get(position)).getTimeTake();
        viewHolder.tv_history_pace.setText(String.valueOf(ShowStringUitls.formatPace(Integer.valueOf(this.mSportData.DoubToInt(((double) mTimeTake2) / Utils.decimalTo2(((SportsInfo) this.sportsInfos.get(position)).getDistance() * 0.001d, 3))).intValue())));
        viewHolder.tv_history_pace.setTypeface(NewApplication.mTypeface_DIN1451EF);
        viewHolder.tv_history_kcal_used.setText(String.valueOf(this.df.format(((SportsInfo) this.sportsInfos.get(position)).getKcal())));
        viewHolder.tv_history_kcal_used.setTypeface(NewApplication.mTypeface_DIN1451EF);
        return convertView;
    }
}
