package com.cobacobms.finalproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.cobacobms.finalproject.R;
import com.cobacobms.finalproject.adapter.SettingViewPagerAdapter;
import com.cobacobms.finalproject.utilities.CobacoApp;
import com.cobacobms.finalproject.utilities.Security;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class SettingActivity extends AppCompatActivity {
    private int defaultColor;

    TabLayout tabSetting;
    ViewPager2 veiwPagerSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        init();
    }

    protected void init() {
        defaultColor = ((CobacoApp) this.getApplication()).getDefaultColor(this);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            window.setStatusBarColor(defaultColor);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(defaultColor));


        Security.checkPermission(this);

        getSupportActionBar().setTitle(R.string.ReturnButtonCaption);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        veiwPagerSetting = findViewById(R.id.veiwPagerSetting);
        veiwPagerSetting.setAdapter(new SettingViewPagerAdapter(this, 3));

        tabSetting = findViewById(R.id.tabSetting);
        tabSetting.setInlineLabel(true);
        new TabLayoutMediator(tabSetting, veiwPagerSetting, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0: {
                        tab.setText("Setting");
                        tab.setIcon(R.drawable.ic_baseline_settings_24);
                    }
                    break;
                    case 1: {
                        tab.setText("Style");
                        tab.setIcon(R.drawable.ic_baseline_brush_24);
                    }
                    break;
                    case 2: {
                        tab.setText("User");
                        tab.setIcon(R.drawable.ic_baseline_how_to_reg_24);
                    }
                    break;
                }
            }
        }).attach();

        Intent intent = getIntent();
        if (intent != null) {
            int tabId = intent.getIntExtra("tabId", 0);
            tabSetting.getTabAt(tabId).select();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}