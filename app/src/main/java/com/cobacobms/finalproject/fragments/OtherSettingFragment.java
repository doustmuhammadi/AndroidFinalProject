package com.cobacobms.finalproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.cobacobms.finalproject.R;
import com.cobacobms.finalproject.utilities.Settings;
import com.google.android.material.switchmaterial.SwitchMaterial;

import static com.cobacobms.finalproject.utilities.Settings.getHelpStatus;
import static com.cobacobms.finalproject.utilities.Settings.setHelpStatus;


public class OtherSettingFragment extends Fragment {

    SwitchMaterial swWifiOnly, swResetHelp;

    public OtherSettingFragment() {
    }


    public static OtherSettingFragment newInstance() {
        OtherSettingFragment fragment = new OtherSettingFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_setting, container, false);

        String onlyWifi = Settings.getOnlyWifi(this.getContext());
        swWifiOnly = view.findViewById(R.id.swWifiOnly);

        if (onlyWifi.equals("true"))
            swWifiOnly.setChecked(true);

        swWifiOnly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Settings.setOnlyWifi(OtherSettingFragment.this.getContext(), "true");
                else
                    Settings.setOnlyWifi(OtherSettingFragment.this.getContext(), "false");
            }
        });

        swResetHelp = view.findViewById(R.id.swResetHelp);
        String showHelp = getHelpStatus(OtherSettingFragment.this.getContext());
        if (showHelp.equals("true"))
            swResetHelp.setChecked(true);
        else
            swResetHelp.setChecked(false);

        swResetHelp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    setHelpStatus(OtherSettingFragment.this.getContext(), "true");
                else
                    setHelpStatus(OtherSettingFragment.this.getContext(), "false");
            }
        });


        return view;
    }
}