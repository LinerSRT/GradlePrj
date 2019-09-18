package com.kct.sports.base;


import android.support.v4.app.Fragment;

import com.kct.sports.bll.ISportsBLL;
import com.kct.sports.bll.SportsBLL;

public class BaseFragment extends Fragment {
    private SportsInfo sportsInfo = null;

    public ISportsBLL getSportsBLL() {
        if (!SportsBLL.getInstance().isInit()) {
            SportsBLL.getInstance().initParam(getActivity());
        }
        return SportsBLL.getInstance();
    }
}
