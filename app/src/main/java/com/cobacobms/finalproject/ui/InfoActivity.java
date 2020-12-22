package com.cobacobms.finalproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.cobacobms.finalproject.R;
import com.cobacobms.finalproject.adapter.InfoViewPagerAdapter;
import com.cobacobms.finalproject.utilities.CobacoApp;
import com.cobacobms.finalproject.utilities.Security;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class InfoActivity extends AppCompatActivity {
    private int defaultColor;

    TabLayout tabInfo;
    ViewPager2 veiwPagerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
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

        veiwPagerInfo = findViewById(R.id.veiwPagerInfo);
        veiwPagerInfo.setAdapter(new InfoViewPagerAdapter(this, 3,veiwPagerInfo));

        tabInfo = findViewById(R.id.tabInfo);
        tabInfo.setInlineLabel(true);
        new TabLayoutMediator(tabInfo, veiwPagerInfo, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0: {
                        tab.setText(R.string.AboutUsCaption);
                        tab.setIcon(R.drawable.ic_baseline_info_24);
                    }
                    break;
                    case 1: {
                        tab.setText(R.string.ContactUsCaption);
                        tab.setIcon(R.drawable.ic_baseline_call_24);
                    }
                    break;
                    case 2: {
                        tab.setText(R.string.Location);
                        tab.setIcon(R.drawable.ic_baseline_location_on_24);
                    }
                    break;
                }
            }
        }).attach();

        veiwPagerInfo.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(position==2)
                    veiwPagerInfo.setUserInputEnabled(false);
                else
                    veiwPagerInfo.setUserInputEnabled(true);
            }
        });
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