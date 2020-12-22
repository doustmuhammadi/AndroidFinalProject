package com.cobacobms.finalproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.cobacobms.finalproject.R;
import com.cobacobms.finalproject.SplashActivity;
import com.cobacobms.finalproject.adapter.ProductListAdapter;
import com.cobacobms.finalproject.database.ProductDBHelper;
import com.cobacobms.finalproject.entity.Product;
import com.cobacobms.finalproject.utilities.CobacoApp;
import com.cobacobms.finalproject.utilities.Security;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.cobacobms.finalproject.utilities.Settings.getListPosition;
import static com.cobacobms.finalproject.utilities.Settings.setListPosition;

public class ProductListActivity extends AppCompatActivity {

    private List<Product> productList = new ArrayList<>();
    private ProductListAdapter productListAdapter;
    RecyclerView rvProductList;
    BottomNavigationView bottomNav;
    private String ListTye = String.valueOf(R.string.TwoColumn);
    private int defaultColor;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setColor();
        productList = new ProductDBHelper(this).select();
        position = getListPosition(this);
        if (productList != null && productList.size() > 0) {
            bindProductList();
            productListAdapter.notifyDataSetChanged();
        } else {
            Intent intent = new Intent(ProductListActivity.this, SplashActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    private void setColor() {
        defaultColor = ((CobacoApp) this.getApplication()).getDefaultColor(this);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            window.setStatusBarColor(defaultColor);

        bottomNav.setBackgroundColor(defaultColor);
    }

    private void init() {
        Security.checkPermission(this);
        setListPosition(this, 0);

        getSupportActionBar().setTitle("");

        searchActionBar();

        rvProductList = findViewById(R.id.rvProductList);

        bottomNav = findViewById(R.id.bottomNav);

        final Menu menu = bottomNav.getMenu();
        MenuItem item = bottomNav.getMenu().findItem(R.id.btnShowType);
        item.setChecked(true);
        menu.findItem(R.id.btnShowType).setIcon(R.drawable.ic_baseline_view_list_24);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btnShowType:
                        setListType(menu);
                        break;
                    case R.id.btnInfo:
                        showAboutUsActivity();
                        break;
                    case R.id.btnSetting:
                        showSettingActivity();
                        break;
                    case R.id.btnDownLoad:
                        showDownloadActivity();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void searchActionBar() {
        View searchView = LayoutInflater.from(this).inflate(R.layout.search_view, null);
        getSupportActionBar().setCustomView(searchView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        int color = ContextCompat.getColor(this, R.color.color_transparent);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));

        final EditText etSearch = searchView.findViewById(R.id.etSearch);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0 && !charSequence.equals(null) && !charSequence.equals(""))
                    productListAdapter.getFilter().filter(charSequence);
                else
                    productListAdapter.getFilter().filter("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    private void bindProductList() {
        productListAdapter = new ProductListAdapter(this, productList, ListTye);
        if (ListTye.equals(String.valueOf(R.string.TwoColumn)))
            rvProductList.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
        else
            rvProductList.setLayoutManager(new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false));

        rvProductList.smoothScrollToPosition(position);
        rvProductList.setItemAnimator(new DefaultItemAnimator());
        rvProductList.setAdapter(productListAdapter);
    }

    private void setListType(Menu menu) {
        if (ListTye.equals(String.valueOf(R.string.TwoColumn))) {
            ListTye = String.valueOf(R.string.OneColumn);
            menu.findItem(R.id.btnShowType).setIcon(R.drawable.ic_baseline_apps_24);
        } else {
            ListTye = String.valueOf(R.string.TwoColumn);
            menu.findItem(R.id.btnShowType).setIcon(R.drawable.ic_baseline_view_list_24);
        }
        bindProductList();
    }

    private void showSettingActivity() {
        Intent intent = new Intent(ProductListActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    private void showAboutUsActivity() {
        Intent intent = new Intent(ProductListActivity.this, InfoActivity.class);
        startActivity(intent);
    }

    private void showDownloadActivity() {
        Intent intent = new Intent(ProductListActivity.this, DownloadActivity.class);
        startActivity(intent);
    }
}
