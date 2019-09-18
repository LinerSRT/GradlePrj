package com.kct.sports.mianui;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.kct.sports.R;
import java.util.ArrayList;

public class MenuVirticalAdapter extends Adapter<MenuVirticalAdapter.MenuVirtalHolder> {
    private static final String TAG = MenuVirticalAdapter.class.getSimpleName();
    private boolean isApp;
    private boolean isLoop = false;
    private boolean isStyle = false;
    private ArrayList<AppMenu> mMenuList;
    /* access modifiers changed from: private */
    public IMenuVerticalClickListen menuVerticalClickListen;
    private boolean needLoop = false;

    public interface IMenuVerticalClickListen {
        void onVerticalMenuClick(View view);
    }

    public class MenuVirtalHolder extends ViewHolder {
        /* access modifiers changed from: private */
        public MenuIconImage mIcon;
        private int mIconSize;
        /* access modifiers changed from: private */
        public ScrollerTextView mName;

        public MenuVirtalHolder(MenuVerticalItem view, int iconSize) {
            super(view);
            this.mIcon = (MenuIconImage) view.findViewById(R.id.applist_icon);
            this.mName = (ScrollerTextView) view.findViewById(R.id.applist_name);
            this.mIconSize = iconSize;
            this.mIcon.getLayoutParams().width = this.mIconSize;
            this.mIcon.getLayoutParams().height = this.mIconSize;
        }
    }

    private class OnItemClick implements OnClickListener {
        /* synthetic */ OnItemClick(MenuVirticalAdapter this$0, OnItemClick onItemClick) {
            this();
        }

        private OnItemClick() {
        }

        public void onClick(View v) {
            if (MenuVirticalAdapter.this.menuVerticalClickListen != null) {
                MenuVirticalAdapter.this.menuVerticalClickListen.onVerticalMenuClick(v);
            }
        }
    }

    public MenuVirticalAdapter(boolean isStyle, boolean isApp, boolean needLoop) {
        this.isStyle = isStyle;
        this.isApp = isApp;
        this.needLoop = needLoop;
        this.mMenuList = new ArrayList();
    }

    public void setMenuClickListem(IMenuVerticalClickListen listen) {
        this.menuVerticalClickListen = listen;
    }

    public boolean isLoop() {
        return this.isLoop;
    }

    public boolean isApp() {
        return this.isApp;
    }

    public void setMenus(ArrayList<AppMenu> menuList) {
        this.mMenuList.clear();
        this.mMenuList.addAll(menuList);
        if (!this.needLoop || this.mMenuList.size() <= 3) {
            this.isLoop = false;
        } else {
            this.isLoop = true;
        }
        notifyDataSetChanged();
    }

    private AppMenu getItem(int position) {
        if (this.isLoop) {
            return (AppMenu) this.mMenuList.get(position % this.mMenuList.size());
        }
        return (AppMenu) this.mMenuList.get(position);
    }

    public ArrayList<AppMenu> getMenus() {
        return this.mMenuList;
    }

    public int getItemCount() {
        if (this.isLoop) {
            return this.mMenuList.size() * 3;
        }
        return (this.mMenuList.size() + 1) + 1;
    }

    public int getItemViewType(int position) {
        if (this.isLoop) {
            return 0;
        }
        if (position == 0 || position == getItemCount() - 1) {
            return 1;
        }
        return 0;
    }

    public void onBindViewHolder(MenuVirtalHolder arg0, int arg1) {
        if (1 != getItemViewType(arg1)) {
            if (!this.isLoop) {
                if (arg1 >= 1 && arg1 <= (this.mMenuList.size() + 1) - 1) {
                    arg1--;
                } else {
                    return;
                }
            }
            AppMenu menu = getItem(arg1 % this.mMenuList.size());
            arg0.mName.setText(menu.getName(arg0.itemView.getContext()));
            if (this.isStyle) {
                arg0.mIcon.setImageDrawable(menu.getIconWithoutCache(arg0.itemView.getContext()));
            } else {
                arg0.mIcon.setImageDrawable(menu.getIcon(arg0.itemView.getContext()));
            }
            arg0.mIcon.setTag(menu);
            arg0.mIcon.setOnClickListener(new OnItemClick(this, null));
            if (!this.isApp) {
                arg0.itemView.setTag(menu);
            }
        }
    }

    public MenuVirtalHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        MenuVerticalItem view;
        if (this.isApp) {
            view = (MenuVerticalItem) LayoutInflater.from(arg0.getContext()).inflate(R.layout.list_item_applist, arg0, false);
        } else {
            view = (MenuVerticalItem) LayoutInflater.from(arg0.getContext()).inflate(R.layout.list_item_applist, arg0, false);
        }
        if (this.isStyle) {
            view.setPadding(0, 0, 0, 0);
        }
        if (1 == arg1) {
            view.setVisibility(View.INVISIBLE);
        }
        view.setParentHeight(arg0.getHeight());
        if (!this.isApp) {
            //view.setParentView(arg0);
        }
        int iconSize = arg0.getHeight() / 3;
        view.getLayoutParams().height = iconSize;
        return new MenuVirtalHolder(view, iconSize);
    }
}
