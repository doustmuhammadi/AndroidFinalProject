package com.cobacobms.finalproject.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cobacobms.finalproject.R;

public class ContactusFragment extends Fragment {
    Button btnTeleName, btnMobileName;

    public ContactusFragment() {
    }

    public static ContactusFragment newInstance() {
        ContactusFragment fragment = new ContactusFragment();
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

        View view = inflater.inflate(R.layout.fragment_contactus, container, false);

        btnTeleName = view.findViewById(R.id.btnTeleName);
        btnMobileName = view.findViewById(R.id.btnMobileName);

        btnTeleName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tele = "tel:" + getString(R.string.CompanyTele);
                Intent callIntent = new Intent();
                callIntent.setAction(Intent.ACTION_VIEW);
                callIntent.setData(Uri.parse(tele));
                startActivity(callIntent);
            }
        });

        btnMobileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = "tel:" + getString(R.string.MyMobile);
                Intent textIntent = new Intent();
                textIntent.setAction(Intent.ACTION_VIEW);
                textIntent.setData(Uri.parse(mobile));
                startActivity(textIntent);
            }
        });

        return view;
    }
}