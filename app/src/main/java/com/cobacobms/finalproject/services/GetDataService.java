package com.cobacobms.finalproject.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.cobacobms.finalproject.SplashActivity;
import com.cobacobms.finalproject.database.ProductDBHelper;
import com.cobacobms.finalproject.entity.Product;
import com.cobacobms.finalproject.ui.ProductListActivity;
import com.cobacobms.finalproject.webservice.productApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetDataService extends IntentService {

    public GetDataService() {
        super("GetDataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(productApi.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        productApi api = retrofit.create(productApi.class);

        final Call<List<Product>> request = api.getallproduct();
        request.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                injectDataToDB(response.body());
                Intent intent1 = new Intent("InjectData");
                intent1.putExtra("status", "success");
                sendBroadcast(intent1);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Intent intent1 = new Intent("InjectData");
                intent1.putExtra("status", "failure");
                sendBroadcast(intent1);
            }
        });
    }

    private void injectDataToDB(List<Product> productList) {
        for (Product item : productList) {
            int productId = item.getProductId();
            String productName = item.getProductName();
            if (productName != null && productName.length() > 150)
                productName = productName.substring(0, 150);
            String productName2 = item.getProductName2();
            if (productName2 != null && productName2.length() > 150)
                productName2 = productName2.substring(0, 150);
            String productDesc = item.getProductDesc();
            if (productDesc != null && productDesc.length() > 2000)
                productDesc = productDesc.substring(0, 2000);
            String category = item.getCategory();
            if (category != null && category.length() > 100)
                category = category.substring(0, 100);
            int picId = item.getPicId();
            String picName = item.getPicName();
            if (picName != null && picName.length() > 1000)
                picName = picName.substring(0, 1000);
            int rank = 0;

            Product localProduct = new ProductDBHelper(this).selectByProductId(item.getProductId());
            if (localProduct != null) {
                Product product = new Product(localProduct.getID(), productId, productName, productName2, productDesc, category, picId, picName, localProduct.getRank());
                new ProductDBHelper(this).update(product);
            } else {
                new ProductDBHelper(this).insert(new Product(productId, productName, productName2, productDesc, category, picId, picName, rank));
            }
        }
    }
}
