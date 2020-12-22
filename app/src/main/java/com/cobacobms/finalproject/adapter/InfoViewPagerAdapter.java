package com.cobacobms.finalproject.adapter;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.cobacobms.finalproject.fragments.AboutusFragment;
import com.cobacobms.finalproject.fragments.ContactusFragment;
import com.cobacobms.finalproject.fragments.MapFragment;
import com.cobacobms.finalproject.fragments.SettingStyleFragment;

public class InfoViewPagerAdapter extends FragmentStateAdapter {
    int tabCount;
    ViewPager2 viewPager;

    public InfoViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, int tabsCount, ViewPager2 viewPager) {
        super(fragmentActivity);
        this.tabCount = tabsCount;
        this.viewPager = viewPager;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AboutusFragment().newInstance();
            case 1:
                return new ContactusFragment().newInstance();
            case 2:
                return new MapFragment().newInstance();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return tabCount;
    }
}