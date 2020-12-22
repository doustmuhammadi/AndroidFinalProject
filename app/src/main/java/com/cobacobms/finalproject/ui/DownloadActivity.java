package com.cobacobms.finalproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.cobacobms.finalproject.R;
import com.cobacobms.finalproject.fragments.AccountFragment;
import com.cobacobms.finalproject.utilities.CobacoApp;
import com.cobacobms.finalproject.utilities.MyDownloader;
import com.cobacobms.finalproject.utilities.Security;
import com.cobacobms.finalproject.utilities.Settings;

public class DownloadActivity extends AppCompatActivity {

    private int defaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        init();
    }

    private void init() {

        defaultColor = ((CobacoApp) this.getApplication()).getDefaultColor(this);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            window.setStatusBarColor(defaultColor);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(defaultColor));

        Security.checkPermission(this);
        checkPermission();

        getSupportActionBar().setTitle(R.string.ReturnButtonCaption);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    protected boolean checkWifi() {
        String wifiOnly = Settings.getOnlyWifi(this);
        if (wifiOnly.equals("true")) {
            WifiManager manager = (WifiManager) this.getApplicationContext().getSystemService(Service.WIFI_SERVICE);
            if (manager.isWifiEnabled())
                return true;
            else
                return false;
        } else
            return true;
    }

    public void btnDownloadClick(View view) {
        if (!checkWifi()) {
            final AlertDialog alertDialog;
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("عدم اتصال به wifi");
            builder.setMessage("دانلود فقط از طریق اتصال به شبکه wifi\n");
            builder.setIcon(R.drawable.ic_baseline_wifi_off_24);
            builder.setPositiveButton("تنظیمات", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(DownloadActivity.this, SettingActivity.class);
                    intent.putExtra("tabId", 0);
                    startActivity(intent);
                }
            });
            alertDialog = builder.create();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                alertDialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);


            alertDialog.show();
        } else {
            String btnText = ((Button) view).getText().toString();
            final String url = "http://api.cobacobms.com/download/";
            String fileName = "";
            int id = 0;

            if (btnText.equals(getString(R.string.catalog))) {
                fileName = "cobacocatalog.pdf";
                id = 1;
            } else if (btnText.equals(getString(R.string.clip))) {
                fileName = "cobacoclip.mp4";
                id = 2;
            } else if (btnText.equals(getString(R.string.ExhibitionClip))) {
                fileName = "buildingexhibition.mp4";
                id = 3;
            }

            MyDownloader myDownloader = new MyDownloader(this, fileName, id);

            if (fileName != "")
                myDownloader.execute(url + fileName);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}