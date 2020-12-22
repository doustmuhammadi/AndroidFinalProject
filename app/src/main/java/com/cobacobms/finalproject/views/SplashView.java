package com.cobacobms.finalproject.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.cobacobms.finalproject.R;
import com.cobacobms.finalproject.SplashActivity;
import com.cobacobms.finalproject.ui.ProductListActivity;
import com.cobacobms.finalproject.utilities.CobacoApp;

import java.util.Timer;
import java.util.TimerTask;

public class SplashView extends View {
    Context context;
    public float pageWidth, pageHeight, cx, cy, logoWidth, logoHeight;
    Timer timer = new Timer();
    Bitmap logbitmap;
    Boolean isContinue = true;

    public SplashView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        context.registerReceiver(new InjectDataBroadCast(), new IntentFilter("InjectData"));

        logbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logowhite);
        logoWidth = logbitmap.getWidth();
        logoHeight = logbitmap.getHeight();

        moveLogo();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        final Paint paint = new Paint();
        int color = ((CobacoApp) this.getContext().getApplicationContext()).getDefaultColor(getContext());
        paint.setColor(color);
        canvas.drawPaint(paint);
        if (cx != 0 || !isContinue)
            canvas.drawBitmap(logbitmap, cx, cy, paint);
    }

    public void moveLogo() {
        this.post(new Runnable() {
            @Override
            public void run() {
                pageWidth = getMeasuredWidth();
                pageHeight = getMeasuredHeight();

                cx = pageWidth / 2 - logoWidth / 2;
                cy = pageHeight - logoHeight - 50;

                stopTimer();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (!isContinue) {
                            timer.cancel();
                            return;
                        }
                        if (cy + logoHeight < 0)
                            cy = pageHeight;
                        cy = cy - 10;
                        invalidate();
                    }
                }, 0, 30);
            }
        });
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private class InjectDataBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = intent.getStringExtra("status");
            if (status.equals("success")) {
                isContinue = false;
                timer.cancel();
            }
        }
    }
}
