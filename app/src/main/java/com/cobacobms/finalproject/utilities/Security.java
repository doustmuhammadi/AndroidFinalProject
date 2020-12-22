package com.cobacobms.finalproject.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.cobacobms.finalproject.R;
import com.cobacobms.finalproject.entity.User;
import com.cobacobms.finalproject.ui.SignUpActivity;


public class Security {
    public static void checkPermission(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.SharedPreferencesName), context.MODE_PRIVATE);
        String result = sharedPref.getString(context.getString(R.string.Authentication), "false");
        if (!result.equals("true")) {
            Intent intent = new Intent(context, SignUpActivity.class);
            context.startActivity(intent);
            ((Activity) context).finish();
        }
    }

    public static User getUser(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.SharedPreferencesName), context.MODE_PRIVATE);
        String userName = sharedPref.getString(context.getString(R.string.UserName), "");
        String userMobile = sharedPref.getString(context.getString(R.string.UserMobile), "");
        User user = new User(userName, userMobile);

        return user;
    }

    public static void setUser(Context context, User user) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.SharedPreferencesName), context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.UserName), user.getUserName());
        editor.putString(context.getString(R.string.UserMobile), user.getMobile());
        editor.putString(context.getString(R.string.UserPassword), user.getPassword());
        editor.apply();
    }

    public static void setAuthentication(Context context, String auth) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.SharedPreferencesName), context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.Authentication), auth);

        editor.apply();
    }
}
