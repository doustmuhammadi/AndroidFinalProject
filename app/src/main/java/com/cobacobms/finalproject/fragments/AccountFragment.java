package com.cobacobms.finalproject.fragments;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cobacobms.finalproject.R;
import com.cobacobms.finalproject.entity.User;
import com.cobacobms.finalproject.utilities.Security;

public class AccountFragment extends Fragment {
    TextView tvUserName, tvMobileName;
    Button btnSignout;

    public AccountFragment() {
    }

    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
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
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        tvUserName = view.findViewById(R.id.tvUserName);
        tvMobileName = view.findViewById(R.id.tvMobileName);
        btnSignout = view.findViewById(R.id.btnSignout);

        User user = Security.getUser(this.getContext());

        tvUserName.setText(user.getUserName());
        tvMobileName.setText(user.getMobile());

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog;
                final AlertDialog.Builder builder = new AlertDialog.Builder(AccountFragment.this.getContext());
                builder.setTitle("خروج از حساب کاربری");
                builder.setMessage("آیا می خواهید از حساب کاربری خارج شوید؟\n ");
                builder.setIcon(R.drawable.ic_baseline_report_problem_24);
                builder.setCancelable(false);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Security.setAuthentication(AccountFragment.this.getContext(), "false");
                        tvUserName.setText("");
                        tvMobileName.setText("");

                        Toast.makeText(AccountFragment.this.getContext(), "شما از حساب کاربری خارج شدید!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog = builder.create();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                    alertDialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

                alertDialog.show();
            }
        });

        return view;
    }
}