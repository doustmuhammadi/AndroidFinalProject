package com.cobacobms.finalproject.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cobacobms.finalproject.fragments.AccountFragment;
import com.cobacobms.finalproject.fragments.OtherSettingFragment;
import com.cobacobms.finalproject.fragments.SettingStyleFragment;

public class SettingViewPagerAdapter extends FragmentStateAdapter {
    int tabCount;

    public SettingViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, int tabCount) {
        super(fragmentActivity);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new OtherSettingFragment().newInstance();
            case 1:
                return new SettingStyleFragment().newInstance();
            case 2:
                return new AccountFragment().newInstance();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return tabCount;
    }
}
