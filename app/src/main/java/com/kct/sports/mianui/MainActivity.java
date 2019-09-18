package com.kct.sports.mianui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

import com.kct.sports.R;

public class MainActivity extends Activity implements MenuVirticalAdapter.IMenuVerticalClickListen {
    private boolean isApp = false;
    private boolean isStyle = false;
    private MenuVerticalBg mMenuSportBg;
    private MenuVirticalAdapter mVirticalAdapter;
    private MenuVirticalRecyleView menuVirticalRecyleView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /* access modifiers changed from: protected */
    public void initView() {
        this.menuVirticalRecyleView = (MenuVirticalRecyleView) findViewById(R.id.menu_virtical_recyleview);
        this.mMenuSportBg = (MenuVerticalBg) findViewById(R.id.menu_virtical_recyleview_bg);
        this.mVirticalAdapter = new MenuVirticalAdapter(this.isStyle, this.isApp, false);
        if (!this.isStyle) {
            this.mVirticalAdapter.setMenuClickListem(this);
        }
        this.menuVirticalRecyleView.initLayoutManager(1, false);
        this.menuVirticalRecyleView.setAdapter(this.mVirticalAdapter);
        this.mVirticalAdapter.setMenus(SportListUtil.getSportList());
        this.mMenuSportBg.setVisibility(View.VISIBLE);
        this.mMenuSportBg.setSportMenus(SportListUtil.getSportList());
        this.menuVirticalRecyleView.setBg(this.mMenuSportBg);
    }

    /* access modifiers changed from: protected */
    public void doMenuClick(View view) {
        if (view.getTag() instanceof AppMenu) {
            AppMenu menu = (AppMenu) view.getTag();
            Intent i;
            if (menu.getInfo() != null) {
                i = new Intent();
                i.setClassName(menu.getInfo().activityInfo.packageName, menu.getInfo().activityInfo.name);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            } else {
                ResolveInfo info = SysServices.getPkMgr(getApplicationContext()).resolveActivity(new Intent(menu.getIntent()), PackageManager.MATCH_ALL);
                if (info == null) {
                    Intent mainIntent = new Intent("android.intent.action.MAIN", null);
                    mainIntent.addCategory("android.intent.category.LAUNCHER");
                    for (ResolveInfo infoActivity : SysServices.getPkMgr(getApplicationContext()).queryIntentActivities(mainIntent, 0)) {
                        if (menu.getIntent().equals(infoActivity.activityInfo.name)) {
                            info = infoActivity;
                            break;
                        }
                    }
                }
                i = new Intent();
                i.setClassName(info.activityInfo.packageName, info.activityInfo.name);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Integer.MIN_VALUE != menu.getIntentData()) {
                    i.putExtra("model", menu.getIntentData());
                }
                startActivity(i);
            }
        }
    }

    public void onVerticalMenuClick(View view) {
        doMenuClick(view);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }
}
