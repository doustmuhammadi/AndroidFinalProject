package com.cobacobms.finalproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.text.LineBreaker;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cobacobms.finalproject.R;
import com.cobacobms.finalproject.database.ProductDBHelper;
import com.cobacobms.finalproject.entity.Product;
import com.cobacobms.finalproject.utilities.CobacoApp;
import com.cobacobms.finalproject.utilities.Security;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import static com.cobacobms.finalproject.utilities.Settings.getHelpStatus;
import static com.cobacobms.finalproject.utilities.Settings.setHelpStatus;
import static com.cobacobms.finalproject.utilities.Settings.setListPosition;

public class ProductActivity extends AppCompatActivity {
    private int defaultColor;

    ImageView imgProductPic;
    TextView tvProductName, tvProductName2, tvProductId, tvCategory, tvProductDesc;
    Button btnCall, btnSMS;
    Product product;
    CheckBox s1, s2, s3, s4, s5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        init();
    }

    private void init() {
        setColor();

        Security.checkPermission(this);

        showHelp();

        getSupportActionBar().setTitle(R.string.ReturnButtonCaption);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgProductPic = findViewById(R.id.imgProductPic);

        tvProductName = findViewById(R.id.tvProductName);
        tvProductName2 = findViewById(R.id.tvProductName2);
        tvProductId = findViewById(R.id.tvProductId);
        tvCategory = findViewById(R.id.tvCategory);
        tvProductDesc = findViewById(R.id.tvProductDesc);

        btnCall = findViewById(R.id.btnCall);
        btnSMS = findViewById(R.id.btnSMS);

        s1 = findViewById(R.id.chbStar1);
        s2 = findViewById(R.id.chbStar2);
        s3 = findViewById(R.id.chbStar3);
        s4 = findViewById(R.id.chbStar4);
        s5 = findViewById(R.id.chbStar5);

        tvProductName.setTextColor(defaultColor);

        bindProduct();
    }

    private void setColor() {
        defaultColor = ((CobacoApp) this.getApplication()).getDefaultColor(this);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            window.setStatusBarColor(defaultColor);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(defaultColor));
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.ic_baseline_star_24);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, defaultColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wrappedDrawable.setTint(defaultColor);
            unwrappedDrawable.setTint(defaultColor);
            wrappedDrawable.setColorFilter(defaultColor, android.graphics.PorterDuff.Mode.SRC_IN);
        }

        Drawable ic_call = AppCompatResources.getDrawable(this, R.drawable.ic_baseline_call_24);
        Drawable ic_sms = AppCompatResources.getDrawable(this, R.drawable.ic_baseline_sms_24);
        Drawable ic_star = AppCompatResources.getDrawable(this, R.drawable.ic_baseline_star_24);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DrawableCompat.setTint(DrawableCompat.wrap(ic_call), defaultColor);
            DrawableCompat.setTint(DrawableCompat.wrap(ic_sms), defaultColor);
            DrawableCompat.setTint(DrawableCompat.wrap(ic_star), defaultColor);
        }
    }

    private void bindProduct() {
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }
        int ID = intent.getIntExtra("ID", 0);
        product = new ProductDBHelper(this).select(ID);

        setListPosition(this, product.getID());

        String imgAddress = "http://www.cobacobms.com/img/amh500/prd/" + product.getPicId() + "/" + product.getPicName();
        Picasso.get().load(imgAddress).into(imgProductPic);
        tvProductName.setText(product.getProductName());
        if (product.getProductName2() != null)
            tvProductName2.setText(product.getProductName2());
        tvProductId.setText(String.valueOf(product.getProductId()));
        tvCategory.setText(product.getCategory());
        tvProductDesc.setText(Html.fromHtml(product.getProductDesc()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tvProductDesc.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }
        switch (product.getRank()) {
            case 1: {
                s1.setChecked(true);
            }
            break;
            case 2: {
                s1.setChecked(true);
                s2.setChecked(true);
            }
            break;
            case 3: {
                s1.setChecked(true);
                s2.setChecked(true);
                s3.setChecked(true);
            }
            break;
            case 4: {
                s1.setChecked(true);
                s2.setChecked(true);
                s3.setChecked(true);
                s4.setChecked(true);
            }
            break;
            case 5: {
                s1.setChecked(true);
                s2.setChecked(true);
                s3.setChecked(true);
                s4.setChecked(true);
                s5.setChecked(true);
            }
            break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void btnCallClick(View view) {
        String tele = "tel:" + getString(R.string.CompanyTele);
        Intent callIntent = new Intent();
        callIntent.setAction(Intent.ACTION_VIEW);
        callIntent.setData(Uri.parse(tele));
        startActivity(callIntent);
    }

    public void btnSMSClick(View view) {
        String mobile = "sms:" + getString(R.string.MyMobile);
        Intent textIntent = new Intent();
        textIntent.setAction(Intent.ACTION_VIEW);
        textIntent.setData(Uri.parse(mobile));
        startActivity(textIntent);
    }

    public void chbStarClick(View view) {

        int rank = 0;
        CheckBox chbStar = (CheckBox) view;
        switch (chbStar.getId()) {
            case R.id.chbStar1: {
                if (chbStar.isChecked()) {
                    rank = 1;
                } else {
                    rank = 0;
                }
                s2.setChecked(false);
                s3.setChecked(false);
                s4.setChecked(false);
                s5.setChecked(false);
            }
            break;
            case R.id.chbStar2: {
                if (chbStar.isChecked()) {
                    s1.setChecked(true);
                } else {
                    s2.setChecked(true);
                }
                s3.setChecked(false);
                s4.setChecked(false);
                s5.setChecked(false);
                rank = 2;
            }
            break;
            case R.id.chbStar3: {
                if (chbStar.isChecked()) {
                    s1.setChecked(true);
                    s2.setChecked(true);
                } else {
                    s3.setChecked(true);
                }
                s4.setChecked(false);
                s5.setChecked(false);
                rank = 3;
            }
            break;
            case R.id.chbStar4: {
                if (chbStar.isChecked()) {
                    s1.setChecked(true);
                    s2.setChecked(true);
                    s3.setChecked(true);
                } else {
                    s4.setChecked(true);
                }
                s5.setChecked(false);
                rank = 4;
            }
            break;
            case R.id.chbStar5: {
                if (chbStar.isChecked()) {
                    s1.setChecked(true);
                    s2.setChecked(true);
                    s3.setChecked(true);
                    s4.setChecked(true);
                } else {
                    s5.setChecked(true);
                }
                rank = 5;
            }
            break;
            default:
                break;
        }
        product.setRank(rank);
        new ProductDBHelper(this).update(product);
        Toast.makeText(this, "امتیاز شما به محصول ثبت شد.", Toast.LENGTH_SHORT).show();
    }

    private void showHelp() {
        if (getHelpStatus(this).equals("true")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.help_startup, Snackbar.LENGTH_LONG);
            ViewCompat.setLayoutDirection(mySnackbar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
            mySnackbar.show();
            mySnackbar.setAction(R.string.help_cancel, new hidHelp());
        }
    }

    public class hidHelp implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            setHelpStatus(ProductActivity.this, "false");
        }
    }
}