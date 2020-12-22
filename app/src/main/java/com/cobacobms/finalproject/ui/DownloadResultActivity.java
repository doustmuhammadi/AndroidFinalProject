package com.cobacobms.finalproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import com.cobacobms.finalproject.R;
import com.cobacobms.finalproject.utilities.CobacoApp;
import com.cobacobms.finalproject.utilities.Security;

import java.io.File;

public class DownloadResultActivity extends AppCompatActivity {
    private int defaultColor;

    TextView tvFileName, tvExtensionName, tvSizeName, tvPathName;
    String name;
    String extension;
    String filepath;
    String size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_result);

        init();
    }

    private void init() {
        defaultColor = ((CobacoApp) this.getApplication()).getDefaultColor(this);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            window.setStatusBarColor(defaultColor);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(defaultColor));

        Security.checkPermission(this);

        getSupportActionBar().setTitle(R.string.ReturnButtonCaption);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvFileName = findViewById(R.id.tvFileName);
        tvExtensionName = findViewById(R.id.tvExtensionName);
        tvSizeName = findViewById(R.id.tvSizeName);
        tvPathName = findViewById(R.id.tvPathName);

        bindPage();
    }

    private void bindPage() {
        Intent intent = getIntent();
        if (intent == null)
            return;

        name = intent.getStringExtra("Name");
        extension = intent.getStringExtra("Extension");
        filepath = intent.getStringExtra("Path");
        size = intent.getStringExtra("Size");

        tvFileName.setText(name);
        tvExtensionName.setText(extension);
        tvSizeName.setText(size);
        tvPathName.setText(filepath);

        int id = intent.getIntExtra("notification_id", 1);
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Service.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void btnShowFileClick(View view) {
        MimeTypeMap myMime = MimeTypeMap.getSingleton();
        Intent newIntent = new Intent(Intent.ACTION_VIEW);
        String mimeType = myMime.getMimeTypeFromExtension(extension);
        Uri uri = Uri.parse(filepath + name + "." + extension);
        newIntent.setDataAndType(uri, mimeType);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            this.startActivity(newIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No handler for this type of file.", Toast.LENGTH_LONG).show();
        }
    }
}