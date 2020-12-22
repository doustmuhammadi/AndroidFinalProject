package com.cobacobms.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.cobacobms.finalproject.services.GetDataService;
import com.cobacobms.finalproject.ui.ProductListActivity;
import com.cobacobms.finalproject.utilities.CobacoApp;
import com.cobacobms.finalproject.views.SplashView;

public class SplashActivity extends AppCompatActivity {

    private int defaultColor;
    ConnectivityBroadCast connectivityBroadCast;
    InjectDataBroadCast injectDataBroadCast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new SplashView(this));
        init();
    }

    private void init() {
        getSupportActionBar().hide();

        defaultColor = ((CobacoApp) this.getApplication()).getDefaultColor(this);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            window.setStatusBarColor(defaultColor);

        connectivityBroadCast = new ConnectivityBroadCast();
        injectDataBroadCast = new InjectDataBroadCast();

        registerReceiver(connectivityBroadCast, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        registerReceiver(injectDataBroadCast, new IntentFilter("InjectData"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(connectivityBroadCast);
        unregisterReceiver(injectDataBroadCast);
    }

    private class ConnectivityBroadCast extends BroadcastReceiver {
        AlertDialog alertDialog;

        @Override
        public void onReceive(Context context, Intent intent) {

            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
            builder.setTitle("عدم اتصال به اینترنت");
            builder.setIcon(R.drawable.ic_baseline_wifi_off_24);
            builder.setMessage("\n برای اجرای برنامه نیاز به اتصال به اینترنت از طریق شبکه وای فای یا دیتا می باشد.");
            builder.setCancelable(false);
            alertDialog = builder.create();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                alertDialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);
            if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting()) {
                if (alertDialog != null)
                    alertDialog.dismiss();
                Intent intent1 = new Intent(context, GetDataService.class);
                startService(intent1);

            } else {
                if (alertDialog != null)
                    alertDialog.show();
            }
        }
    }

    private class InjectDataBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = intent.getStringExtra("status");
            if (status.equals("success")) {
                Intent intent1 = new Intent(SplashActivity.this, ProductListActivity.class);
                startActivity(intent1);
                finish();
            } else {
                Toast.makeText(SplashActivity.this, "خطا در اتصال یه وب سایت", Toast.LENGTH_LONG).show();
            }
        }
    }
}
