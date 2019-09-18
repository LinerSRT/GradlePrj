package com.kct.sports.mianui;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings.System;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuVerticalBg extends FrameLayout {
    private static final String TAG = MenuVerticalBg.class.getSimpleName();
    private MenuBgObserve mMenuBgObserve = new MenuBgObserve(new Handler());
    private HashMap<AppMenu, BgImage> mSportBgs = new HashMap();
    /* access modifiers changed from: private */
    public int menuSportBgType = 0;

    private class BgImage extends AppCompatImageView {
        private AppMenu menu;

        public BgImage(Context context, AppMenu menu) {
            super(context);
            this.menu = menu;
            setVisibility(GONE);
        }

        public void setAlpha(float alpha) {
            if (((double) alpha) < 0.1d) {
                setVisibility(GONE);
                return;
            }
            setVisibility(VISIBLE);
            setImageResource(MenuVerticalBg.this.menuSportBgType == 0 ? this.menu.getBgImageRes() : this.menu.getBgImageBwRes());
            super.setAlpha(alpha);
        }
    }

    private class MenuBgObserve extends ContentObserver {
        private Uri menuSportenuBgUri;

        public MenuBgObserve(Handler handler) {
            super(handler);
        }

        public void register(Context context) {
            this.menuSportenuBgUri = getMenuSportBgUri(context);
            if (this.menuSportenuBgUri != null) {
                context.getContentResolver().registerContentObserver(this.menuSportenuBgUri, true, this);
            }
        }

        public void unRegister(Context context) {
            context.getContentResolver().unregisterContentObserver(this);
        }

        private Uri getMenuSportBgUri(Context context) {
            if (System.getUriFor("kct_menu_sport_bg") == null) {
                SysServices.setSystemSettingInt(context, "kct_menu_sport_bg", 0);
            }
            return System.getUriFor("kct_menu_sport_bg");
        }

        public void onChange(boolean selfChange, Uri uri) {
            if (this.menuSportenuBgUri.equals(uri)) {
                MenuVerticalBg.this.menuSportBgType = SysServices.getSystemSettingInt(MenuVerticalBg.this.getContext(), "kct_menu_sport_bg", MenuVerticalBg.this.menuSportBgType);
                for (int i = 0; i < MenuVerticalBg.this.getChildCount(); i++) {
                }
            }
        }
    }

    public MenuVerticalBg(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mMenuBgObserve.register(getContext());
        this.menuSportBgType = SysServices.getSystemSettingInt(getContext(), "kct_menu_sport_bg", this.menuSportBgType);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        this.mMenuBgObserve.unRegister(getContext());
        super.onDetachedFromWindow();
    }

    public void setSportMenus(ArrayList<AppMenu> menus) {
        LayoutParams params = new LayoutParams(-1, -1);
        this.mSportBgs.clear();
        for (AppMenu menu : menus) {
            BgImage image = new BgImage(getContext(), menu);
            this.mSportBgs.put(menu, image);
            addView(image, params);
        }
    }

    public void freshBg(AppMenu menu, float alpha) {
        if (menu != null) {
            if (alpha <= 0.0f) {
                alpha = 0.0f;
            }
            BgImage image = (BgImage) this.mSportBgs.get(menu);
            if (image != null) {
                image.setAlpha(alpha);
            }
        }
    }
}
