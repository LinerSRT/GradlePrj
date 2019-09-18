package com.kct.sports.History;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.System;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.kct.sports.R;
import com.kct.sports.base.NewApplication;
import com.kct.sports.base.SportsInfo;
import com.kct.sports.base.Trace;
import com.kct.sports.db.DBUtil;
import com.kct.sports.utils.ShowStringUitls;
import com.kct.sports.utils.Utils;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@SuppressLint("ValidFragment")
public class HistoryDetailOneFragment extends Fragment {
    private static HistoryDetailOneFragment mHistoryDetailOneFragment = null;
    private Circle circle;
    /* access modifiers changed from: private */
    public DBUtil dbUtil;
    private TextView detail_one_model;
    private TextView detail_one_record_time;
    private TextView detail_one_time;
    private TextView detail_one_total_distance;
    private DecimalFormat df;
    /* access modifiers changed from: private */
    public FrameLayout frameLayout;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    HistoryDetailOneFragment.this.relativeLayout.setVisibility(View.VISIBLE);
                    HistoryDetailOneFragment.this.frameLayout.setVisibility(View.GONE);
                    if ("zh".equals(Utils.getCurrentLauguage())) {
                        HistoryDetailOneFragment.this.drawLine();
                        HistoryDetailOneFragment.this.mMap.setMapType(1);
                        HistoryDetailOneFragment.this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((LatLng) HistoryDetailOneFragment.this.latlngList.get(HistoryDetailOneFragment.this.latlngList.size() - 1), 16.0f));
                        HistoryDetailOneFragment.this.mMapView.setVisibility(View.VISIBLE);
                        return;
                    } else if (HistoryDetailOneFragment.this.isGoogleService) {
                        HistoryDetailOneFragment.this.mGoogleMapView.setVisibility(View.VISIBLE);
                        HistoryDetailOneFragment.this.gooleDrawLine();
                        return;
                    } else {
                        HistoryDetailOneFragment.this.drawLine();
                        HistoryDetailOneFragment.this.mMap.setMapType(1);
                        HistoryDetailOneFragment.this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((LatLng) HistoryDetailOneFragment.this.latlngList.get(HistoryDetailOneFragment.this.latlngList.size() - 1), 16.0f));
                        HistoryDetailOneFragment.this.mMapView.setVisibility(View.VISIBLE);
                        return;
                    }
                case 1:
                    HistoryDetailOneFragment.this.relativeLayout.setVisibility(View.GONE);
                    HistoryDetailOneFragment.this.frameLayout.setVisibility(View.VISIBLE);
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public boolean isGoogleService = false;
    /* access modifiers changed from: private */
    public ArrayList<LatLng> latlngList = new ArrayList();
    private Marker mGoogleEndMarker;
    List<com.google.android.gms.maps.model.LatLng> mGoogleLatlngList = new ArrayList();
    private GoogleMap mGoogleMap;
    /* access modifiers changed from: private */
    public MapView mGoogleMapView;
    private Polyline mGooglePolyline;
    private Marker mGoogleStartMarker;
    /* access modifiers changed from: private */
    public AMap mMap;
    /* access modifiers changed from: private */
    public com.amap.api.maps2d.MapView mMapView = null;
    private com.amap.api.maps2d.model.Marker marker;
    private int model = 0;
    /* access modifiers changed from: private */
    public int position;
    /* access modifiers changed from: private */
    public RelativeLayout relativeLayout;
    private SimpleDateFormat sdf;
    private SimpleDateFormat sdf_takeTime;
    /* access modifiers changed from: private */
    public ArrayList<SportsInfo> sportsInfos = new ArrayList();
    /* access modifiers changed from: private */
    public List<Trace> trace_points = new ArrayList();
    private View view;

    private class ReadTraceCountTask extends AsyncTask<Void, Void, Void> {
        /* synthetic */ ReadTraceCountTask(HistoryDetailOneFragment this$0, ReadTraceCountTask readTraceCountTask) {
            this();
        }

        private ReadTraceCountTask() {
        }

        /* access modifiers changed from: protected|varargs */
        public Void doInBackground(Void... params) {
            readDBInfo();
            return null;
        }

        private void readDBInfo() {
            HistoryDetailOneFragment.this.trace_points = HistoryDetailOneFragment.this.dbUtil.getTrace(((SportsInfo) HistoryDetailOneFragment.this.sportsInfos.get(HistoryDetailOneFragment.this.position)).getStartTime(), ((SportsInfo) HistoryDetailOneFragment.this.sportsInfos.get(HistoryDetailOneFragment.this.position)).getGetTime());
            Log.e("gg", ((SportsInfo) HistoryDetailOneFragment.this.sportsInfos.get(HistoryDetailOneFragment.this.position)).getStartTime() + "===" + ((SportsInfo) HistoryDetailOneFragment.this.sportsInfos.get(HistoryDetailOneFragment.this.position)).getGetTime());
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void aVoid) {
            for (int i = 0; i < HistoryDetailOneFragment.this.trace_points.size(); i++) {
                HistoryDetailOneFragment.this.latlngList.add(new LatLng(((Trace) HistoryDetailOneFragment.this.trace_points.get(i)).getLatitude(), ((Trace) HistoryDetailOneFragment.this.trace_points.get(i)).getLongitude()));
                HistoryDetailOneFragment.this.mGoogleLatlngList.add(new com.google.android.gms.maps.model.LatLng(((Trace) HistoryDetailOneFragment.this.trace_points.get(i)).getLatitude(), ((Trace) HistoryDetailOneFragment.this.trace_points.get(i)).getLongitude()));
            }
            if (HistoryDetailOneFragment.this.latlngList.size() > 1) {
                HistoryDetailOneFragment.this.handler.sendEmptyMessage(0);
            } else {
                HistoryDetailOneFragment.this.handler.sendEmptyMessage(1);
            }
            super.onPostExecute(aVoid);
        }
    }

    public HistoryDetailOneFragment(int position, ArrayList<SportsInfo> sportsInfos) {
        this.position = position;
        this.sportsInfos = sportsInfos;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.view == null) {
            this.view = inflater.inflate(R.layout.detail_one, container, false);
            this.isGoogleService = Utils.isGooglePlayServiceAvailable(getActivity());
            if ("zh".equals(Utils.getCurrentLauguage())) {
                initGdMap(this.view, savedInstanceState);
            } else if (this.isGoogleService) {
                MapsInitializer.initialize(getActivity());
                this.mGoogleMapView = (MapView) this.view.findViewById(R.id.google_map);
                this.mGoogleMapView.onCreate(savedInstanceState);
            } else {
                initGdMap(this.view, savedInstanceState);
            }
        } else if (this.view.getParent() != null) {
            ((ViewGroup) this.view.getParent()).removeView(this.view);
        }
        initView();
        initEvent();
        return this.view;
    }

    private void initGdMap(View view, Bundle savedInstanceState) {
        this.mMapView = (com.amap.api.maps2d.MapView) view.findViewById(R.id.history_map);
        this.mMapView.onCreate(savedInstanceState);
        if (this.mMap == null) {
            this.mMap = this.mMapView.getMap();
            setUpMap();
        }
    }

    private void setUpMap() {
        this.mMap.moveCamera(CameraUpdateFactory.zoomTo(16.0f));
        this.mMap.getUiSettings().setMyLocationButtonEnabled(false);
        this.mMap.getUiSettings().setZoomControlsEnabled(false);
        this.mMap.setMyLocationEnabled(true);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onResume() {
        if ("zh".equals(Utils.getCurrentLauguage())) {
            this.mMapView.onResume();
        } else if (this.isGoogleService) {
            this.mGoogleMapView.onResume();
            setUpMapIfNeeded();
        } else {
            this.mMapView.onResume();
        }
        super.onResume();
    }

    private void setUpMapIfNeeded() {
        if (this.mGoogleMap == null) {
            this.mGoogleMap = ((MapView) this.view.findViewById(R.id.google_map)).getMap();
            if (this.mGoogleMap != null) {
                setUpGoogMap();
            }
        }
    }

    private void setUpGoogMap() {
        this.mGoogleMap.setMapType(1);
        this.mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
        this.mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
    }

    public void onPause() {
        super.onPause();
        if ("zh".equals(Utils.getCurrentLauguage())) {
            this.mMapView.onPause();
        } else if (this.isGoogleService) {
            this.mGoogleMapView.onPause();
        } else {
            this.mMapView.onPause();
        }
    }

    public void onLowMemory() {
        super.onLowMemory();
        if (!"zh".equals(Utils.getCurrentLauguage()) && this.isGoogleService) {
            this.mGoogleMapView.onLowMemory();
        }
    }

    public void onDestroy() {
        if ("zh".equals(Utils.getCurrentLauguage())) {
            this.mMapView.onDestroy();
        } else if (this.isGoogleService) {
            this.mGoogleMapView.onDestroy();
        } else {
            this.mMapView.onDestroy();
        }
        super.onDestroy();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if ("zh".equals(Utils.getCurrentLauguage())) {
            this.mMapView.onSaveInstanceState(outState);
        } else if (this.isGoogleService) {
            this.mGoogleMapView.onSaveInstanceState(outState);
        } else {
            this.mMapView.onSaveInstanceState(outState);
        }
    }

    /* access modifiers changed from: private */
    public void gooleDrawLine() {
        this.mGooglePolyline = this.mGoogleMap.addPolyline(new PolylineOptions().color(-13989377).width(8.0f).addAll(this.mGoogleLatlngList));
        this.mGoogleStartMarker = this.mGoogleMap.addMarker(new MarkerOptions().position((com.google.android.gms.maps.model.LatLng) this.mGoogleLatlngList.get(0)).icon(BitmapDescriptorFactory.fromResource(R.drawable.green_start)).anchor(0.5f, 0.5f));
        this.mGoogleEndMarker = this.mGoogleMap.addMarker(new MarkerOptions().position((com.google.android.gms.maps.model.LatLng) this.mGoogleLatlngList.get(this.mGoogleLatlngList.size() - 1)).icon(BitmapDescriptorFactory.fromResource(R.drawable.red_end)).anchor(0.5f, 0.5f));
        try {
            this.mGoogleMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom((com.google.android.gms.maps.model.LatLng) this.mGoogleLatlngList.get(0), 17.0f));
            this.mGoogleMap.animateCamera(com.google.android.gms.maps.CameraUpdateFactory.zoomTo(17.0f), 2000, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void drawLine() {
        LatLng replayGeoPoint = (LatLng) this.latlngList.get(this.latlngList.size() - 1);
        this.mMap.clear();
        if (this.marker != null) {
            this.marker.destroy();
        }
        if (!"zh".equals(Utils.getCurrentLauguage())) {
            this.circle = this.mMap.addCircle(new CircleOptions().center((LatLng) this.latlngList.get(this.latlngList.size() - 1)).radius(100000.0d).fillColor(Color.argb(255, 0, 0, 0)).strokeColor(Color.argb(255, 0, 0, 0)).strokeWidth(25.0f));
        }
        if (this.latlngList.size() > 1) {
            this.mMap.addPolyline(new com.amap.api.maps2d.model.PolylineOptions().addAll(this.latlngList).color(Color.rgb(9, 129, 240)).width(6.0f));
        }
        com.amap.api.maps2d.model.MarkerOptions markerOptions = new com.amap.api.maps2d.model.MarkerOptions();
        markerOptions.position(replayGeoPoint).icon(com.amap.api.maps2d.model.BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.red_end))).anchor(0.5f, 0.5f);
        this.marker = this.mMap.addMarker(markerOptions);
        com.amap.api.maps2d.model.Marker mOriginStartMarker = this.mMap.addMarker(new com.amap.api.maps2d.model.MarkerOptions().position((LatLng) this.latlngList.get(0)).icon(com.amap.api.maps2d.model.BitmapDescriptorFactory.fromResource(R.drawable.green_start)).anchor(0.5f, 0.5f));
    }

    private void initEvent() {
        this.df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH));
        this.sdf = new SimpleDateFormat("yyyy-MM-dd   HH:mm", Locale.ENGLISH);
        this.sdf_takeTime = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        this.sdf_takeTime.setTimeZone(TimeZone.getTimeZone("GMT"));
        this.model = System.getInt(getActivity().getContentResolver(), "model", 4);
        this.detail_one_total_distance.setText(String.valueOf(this.df.format(((SportsInfo) this.sportsInfos.get(this.position)).getDistance() / 1000.0d)));
        this.detail_one_record_time.setText(String.valueOf(this.sdf_takeTime.format(Long.valueOf(((SportsInfo) this.sportsInfos.get(this.position)).getTimeTake() * 1000))));
        this.detail_one_record_time.setTypeface(NewApplication.mTypeface_date);
        this.detail_one_time.setText(String.valueOf(this.sdf.format(Long.valueOf(((SportsInfo) this.sportsInfos.get(this.position)).getGetTime()))));
        this.detail_one_time.setTypeface(NewApplication.mTypeface_DIN1451EF);
        this.detail_one_model.setText(ShowStringUitls.getModel(getActivity(), ((SportsInfo) this.sportsInfos.get(this.position)).getModel()));
        this.dbUtil = new DBUtil(getActivity());
        this.latlngList.clear();
        new ReadTraceCountTask(this, null).execute(new Void[0]);
    }

    private void initView() {
        this.detail_one_model = (TextView) this.view.findViewById(R.id.detail_one_model);
        this.detail_one_time = (TextView) this.view.findViewById(R.id.detail_one_time);
        this.detail_one_record_time = (TextView) this.view.findViewById(R.id.detail_one_record_time);
        this.detail_one_total_distance = (TextView) this.view.findViewById(R.id.detail_one_total_distance);
        this.detail_one_total_distance.setTypeface(NewApplication.mTypeface_date);
        this.frameLayout = (FrameLayout) this.view.findViewById(R.id.show_trace);
        this.relativeLayout = (RelativeLayout) this.view.findViewById(R.id.show_map);
    }

    public View getMapView() {
        if ("zh".equals(Utils.getCurrentLauguage())) {
            return this.mMapView;
        }
        if (this.isGoogleService) {
            return this.mGoogleMapView;
        }
        return this.mMapView;
    }
}
