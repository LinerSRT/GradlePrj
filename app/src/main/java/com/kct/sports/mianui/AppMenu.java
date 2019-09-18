package com.kct.sports.mianui;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.kct.sports.R;
import java.util.ArrayList;

public class AppMenu implements Parcelable {
    public static final Creator<AppMenu> CREATOR = new Creator<AppMenu>() {
        public AppMenu createFromParcel(Parcel source) {
            AppMenu menu = new AppMenu();
            menu.setCustonName(source.readString());
            menu.setCustomImageName(source.readString());
            menu.setIntent(source.readString());
            menu.setInfo((ResolveInfo) source.readParcelable(ResolveInfo.class.getClassLoader()));
            ArrayList<AppMenu> childrens = new ArrayList();
            source.readTypedList(childrens, AppMenu.CREATOR);
            if (childrens != null && childrens.size() > 0) {
                menu.setChildrenList(childrens);
            }
            ArrayList<String> filts = new ArrayList();
            source.readStringList(filts);
            if (filts != null && filts.size() > 0) {
                menu.setFiltChildren(filts);
            }
            return menu;
        }

        public AppMenu[] newArray(int size) {
            return new AppMenu[size];
        }
    };
    private static final String TAG = AppMenu.class.getSimpleName();
    private int bgImageRes = Integer.MIN_VALUE;
    private int bgImagesBwRes = Integer.MIN_VALUE;
    private ArrayList<AppMenu> childrenList;
    private String customImageName;
    private int customImageRes = Integer.MIN_VALUE;
    private int customNameRes = Integer.MIN_VALUE;
    private String custonName;
    private ArrayList<String> filtChildren;
    private ResolveInfo info;
    private String intent;
    private int intentData = Integer.MIN_VALUE;
    private int menuType = 0;

    public void setFiltChildren(ArrayList<String> filtChildren) {
        this.filtChildren = filtChildren;
    }

    public int getIntentData() {
        return this.intentData;
    }

    public void setIntentData(int intentData) {
        this.intentData = intentData;
    }

    public void setCustomNameRes(int customNameRes) {
        this.customNameRes = customNameRes;
    }

    public void setCustomImageRes(int customImageRes) {
        this.customImageRes = customImageRes;
    }

    public int getBgImageRes() {
        return this.bgImageRes;
    }

    public void setBgImageRes(int bgImageRes) {
        this.bgImageRes = bgImageRes;
    }

    public int getBgImageBwRes() {
        return this.bgImagesBwRes;
    }

    public void setBgImageBwRes(int bgImageBwRes) {
        this.bgImagesBwRes = bgImageBwRes;
    }

    public String getName(Context context) {
        if (this.customNameRes != Integer.MIN_VALUE) {
            return context.getString(this.customNameRes);
        }
        if (getCustonName() != null) {
            return getCustonName();
        }
        if (getInfo() != null) {
            return (String) getInfo().loadLabel(SysServices.getPkMgr(context));
        }
        return context.getString(R.string.alt_unit);
    }

    public Drawable getIconWithoutCache(Context context) {
        if (this.customImageRes != Integer.MIN_VALUE) {
            return context.getResources().getDrawable(this.customImageRes);
        }
        return context.getResources().getDrawable(R.drawable.ic_launcher);
    }

    public Drawable getIcon(Context context) {
        if (this.customImageRes != Integer.MIN_VALUE) {
            return context.getResources().getDrawable(this.customImageRes);
        }
        return context.getResources().getDrawable(R.drawable.ic_launcher);
    }

    public String getCustonName() {
        return this.custonName;
    }

    public void setCustonName(String custonName) {
        this.custonName = custonName;
    }

    public void setCustomImageName(String customImageName) {
        this.customImageName = customImageName;
    }

    public String getIntent() {
        return this.intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public void setChildrenList(ArrayList<AppMenu> childrenList) {
        this.childrenList = childrenList;
    }

    public ResolveInfo getInfo() {
        return this.info;
    }

    public void setInfo(ResolveInfo info) {
        this.info = info;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.custonName);
        dest.writeString(this.customImageName);
        dest.writeString(this.intent);
        dest.writeParcelable(this.info, flags);
        dest.writeTypedList(this.childrenList);
        dest.writeStringList(this.filtChildren);
    }
}
