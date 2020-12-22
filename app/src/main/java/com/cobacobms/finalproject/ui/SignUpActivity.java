package com.cobacobms.finalproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cobacobms.finalproject.R;
import com.cobacobms.finalproject.entity.User;
import com.cobacobms.finalproject.utilities.DataValidation;
import com.cobacobms.finalproject.utilities.Security;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {

    TextInputLayout metUserName, metMobile, metPassword, metRepeatPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }

    private void init() {
        getSupportActionBar().hide();

        metUserName = findViewById(R.id.metUserName);
        metMobile = findViewById(R.id.metMobile);
        metPassword = findViewById(R.id.metPassword);
        metRepeatPassword = findViewById(R.id.metRepeatPassword);
    }

    public void btnSubmitUserClick(View view) {
        try {
            DataValidation.CheckName(metUserName.getEditText().getText().toString(), "UserName");
            DataValidation.CheckMobile(metMobile.getEditText().getText().toString());
            DataValidation.CheckPassword(metPassword.getEditText().getText().toString(), metRepeatPassword.getEditText().getText().toString());

            String userNmae = metUserName.getEditText().getText().toString();
            String mobile = metMobile.getEditText().getText().toString();
            String password = metPassword.getEditText().getText().toString();

            User user = new User(userNmae, mobile, password);
            Security.setUser(this, user);
            Security.setAuthentication(this, "true");

            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);
            finish();

            Toast.makeText(this, "اطلاعات کاربری ثبت گردید", Toast.LENGTH_LONG).show();
        } catch (NullPointerException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}