package com.cobacobms.finalproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.cobacobms.finalproject.R;
import com.cobacobms.finalproject.utilities.CobacoApp;
import com.google.android.material.switchmaterial.SwitchMaterial;

import top.defaults.colorpicker.ColorPickerPopup;

import static com.cobacobms.finalproject.utilities.Settings.getThemeType;
import static com.cobacobms.finalproject.utilities.Settings.setTheme;
import static com.cobacobms.finalproject.utilities.Settings.setThemeType;

public class SettingStyleFragment extends Fragment {

    private Button btnPicColor;
    SwitchMaterial swDefaultTheme;
    int defaultColor;

    public SettingStyleFragment() {
    }

    public static SettingStyleFragment newInstance() {
        SettingStyleFragment fragment = new SettingStyleFragment();
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
        View view = inflater.inflate(R.layout.fragment_style, container, false);

        swDefaultTheme = view.findViewById(R.id.swDefaultTheme);
        btnPicColor = view.findViewById(R.id.btnPicColor);
        defaultColor = ((CobacoApp) this.getContext().getApplicationContext()).getDefaultColor(getContext());
        btnPicColor.setBackgroundColor(defaultColor);

        String isDefaultTheme = "true";
        isDefaultTheme = getThemeType(this.getContext());
        if (isDefaultTheme.equals("true")) {
            swDefaultTheme.setChecked(true);
            btnPicColor.setEnabled(false);
        }
        swDefaultTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setThemeType(SettingStyleFragment.this.getContext(), "true");
                    setTheme(SettingStyleFragment.this.getContext(), ContextCompat.getColor(SettingStyleFragment.this.getContext(), R.color.colorPrimaryDark));
                    btnPicColor.setEnabled(false);
                } else {
                    setThemeType(SettingStyleFragment.this.getContext(), "false");
                    btnPicColor.setEnabled(true);
                }
            }
        });

        btnPicColor.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        new ColorPickerPopup.Builder(SettingStyleFragment.this.getContext())
                                .initialColor(defaultColor)
                                .enableBrightness(true)
                                .enableAlpha(true)
                                .okTitle("Select")
                                .cancelTitle("Cancel")
                                .showIndicator(true)
                                .showValue(true)
                                .build()
                                .show(v, new ColorPickerPopup.ColorPickerObserver() {
                                    @Override
                                    public void
                                    onColorPicked(int color) {
                                        defaultColor = color;
                                        btnPicColor.setBackgroundColor(defaultColor);
                                        setTheme(SettingStyleFragment.this.getContext(), defaultColor);
                                        Toast.makeText(SettingStyleFragment.this.getContext(), "تنظیم رنگ انجام شد.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });


        return view;
    }
}