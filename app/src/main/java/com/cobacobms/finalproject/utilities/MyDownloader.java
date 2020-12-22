package com.cobacobms.finalproject.utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.cobacobms.finalproject.R;
import com.cobacobms.finalproject.entity.DownloadFile;
import com.cobacobms.finalproject.ui.DownloadResultActivity;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class MyDownloader extends AsyncTask<String, Integer, String> {
    ProgressDialog progressDialog;
    Context context;
    String fileName;
    int id;

    public MyDownloader(final Context context, String fileName, int id) {

        this.context = context;
        this.fileName = fileName;
        this.id = id;

        this.progressDialog = new ProgressDialog(context);
        this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        this.progressDialog.setMax(100);
        this.progressDialog.setTitle("Downloading...");
        this.progressDialog.setIcon(R.drawable.ic_baseline_get_app_24);
        this.progressDialog.setMessage("\n file : " + fileName);
        this.progressDialog.setCancelable(false);
        this.progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDownloader.this.cancel(true);
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.progressDialog.setProgress(0);
        this.progressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        this.progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        this.progressDialog.setProgress(values[0]);
    }

    @Override
    protected String doInBackground(String... strings) {

        String urlAddress = strings[0];
        final String myFolder = "/sdcard/Download/";
        String path = myFolder + fileName;

        try {
            URL url = new URL(urlAddress);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            int length = urlConnection.getContentLength();

            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            OutputStream outputStream = new FileOutputStream(path);

            byte[] buffer = new byte[1024];

            int count = -1;
            int total = 0;
            while ((count = inputStream.read(buffer)) != -1) {
                if (isCancelled())
                    return null;

                total += count;

                publishProgress((total * 100) / length);
                outputStream.write(buffer, 0, count);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();

            String name = fileName.substring(0, fileName.indexOf('.'));
            String extension = fileName.substring(fileName.indexOf('.') + 1);
            String filepath = myFolder;
            String size = "0 MB";
            if (length > 0) {
                float s = length / (1024 * 1024);
                size = String.format("%.2f", s) + " MB";
            }
            DownloadFile file = new DownloadFile(name, extension, filepath, size);
            showNotification(file);

            return "Download finished";

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private void showNotification(DownloadFile file) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Successful Download");
        builder.setContentText("file Name : " + file.getName() + "." + file.getExtension());
        builder.setSmallIcon(R.drawable.ic_baseline_done_24);

        NotificationCompat.Style style = new NotificationCompat.InboxStyle()
                .addLine("file Name : " + file.getName() + "." + file.getExtension())
                .addLine("Folder : " + file.getPath())
                .addLine("File Size : " + file.getSize());
        builder.setStyle(style);

        Intent intent = new Intent(context, DownloadResultActivity.class);
        intent.putExtra("Name", file.getName());
        intent.putExtra("Extension", file.getExtension());
        intent.putExtra("Path", file.getPath());
        intent.putExtra("Size", file.getSize());
        intent.putExtra("notification_id", id);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channelId", "channelName", NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId("channelId");
            notificationManager.createNotificationChannel(channel);
        }

        Notification notify = builder.build();
        notificationManager.notify(id, notify);
    }
}