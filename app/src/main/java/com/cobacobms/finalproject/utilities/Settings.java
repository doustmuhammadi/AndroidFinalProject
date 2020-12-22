package com.cobacobms.finalproject.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.cobacobms.finalproject.R;
import com.cobacobms.finalproject.entity.Coordination;

public class Settings {
    public static String getThemeType(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.SharedPreferencesName), context.MODE_PRIVATE);
        String isDefaultTheme = "true";
        isDefaultTheme = sharedPref.getString(context.getString(R.string.isDefault_theme), isDefaultTheme);
        return isDefaultTheme;
    }

    public static void setThemeType(Context context, String isDefaultTheme) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.SharedPreferencesName), context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.isDefault_theme), isDefaultTheme);
        editor.apply();
    }

    public static Integer getTheme(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.SharedPreferencesName), context.MODE_PRIVATE);
        Integer themeColor = R.color.colorPrimaryDark;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            themeColor = sharedPref.getInt(context.getString(R.string.theme_color), context.getColor(themeColor));
        }
        return themeColor;
    }

    public static void setTheme(Context context, int color) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.SharedPreferencesName), context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(context.getString(R.string.theme_color), color);
        editor.apply();
    }

    public static String getOnlyWifi(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.SharedPreferencesName), context.MODE_PRIVATE);
        String status = sharedPref.getString(context.getString(R.string.OnlyWifi), "true");
        return status;
    }

    public static void setOnlyWifi(Context context, String status) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.SharedPreferencesName), context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.OnlyWifi), status);
        editor.apply();
    }

    public static String getHelpStatus(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.SharedPreferencesName), context.MODE_PRIVATE);
        String showHelp = sharedPref.getString(context.getString(R.string.show_help_status), "true");
        return showHelp;
    }

    public static void setHelpStatus(Context context, String status) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.SharedPreferencesName), context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.show_help_status), status);
        editor.apply();
    }

    public static int getListPosition(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.SharedPreferencesName), context.MODE_PRIVATE);
        int position = sharedPref.getInt(context.getString(R.string.listPosition), 0);
        return position;
    }

    public static void setListPosition(Context context, int position) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.SharedPreferencesName), context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(context.getString(R.string.listPosition), position);
        editor.apply();
    }

    public static Coordination getCoordination(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.SharedPreferencesName), context.MODE_PRIVATE);
        String longitude = sharedPref.getString(context.getString(R.string.longitude), "0");
        String latitude = sharedPref.getString(context.getString(R.string.latitude), "0");

        return new Coordination(longitude, latitude);
    }

    public static void setCoordination(Context context, Coordination coordination) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.SharedPreferencesName), context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.longitude), coordination.getLongitude());
        editor.putString(context.getString(R.string.latitude), coordination.getLatitude());

        editor.apply();
    }
}
