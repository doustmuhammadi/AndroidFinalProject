package com.cobacobms.finalproject.webservice;

import com.cobacobms.finalproject.entity.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface productApi  {
    String BASE_URL = "http://api.cobacobms.com/";

    // @FormUrlEncoded
    @GET("api/product/getallproducts")
    Call<List<Product>> getallproduct();


    //@FormUrlEncoded
    @GET("api/product/getproductByid/{id}")
    Call<Product> getproductbyid(@Path("id") Long id);
}
