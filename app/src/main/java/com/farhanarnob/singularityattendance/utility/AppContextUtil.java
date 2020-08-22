package com.farhanarnob.singularityattendance.utility;

import android.content.Context;

public class AppContextUtil {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        AppContextUtil.context = context;
    }


}
