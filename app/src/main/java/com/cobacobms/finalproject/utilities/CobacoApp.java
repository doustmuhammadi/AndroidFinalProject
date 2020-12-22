package com.cobacobms.finalproject.utilities;

import android.app.Application;
import android.content.Context;

public class CobacoApp extends Application {
    private int defaultColor;

    public int getDefaultColor(Context context) {
        return com.cobacobms.finalproject.utilities.Settings.getTheme(context);
    }

    public void setDefaultColor(int defaultColor) {
        this.defaultColor = defaultColor;
    }
}
