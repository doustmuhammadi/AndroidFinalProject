package com.cobacobms.finalproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.cobacobms.finalproject.entity.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductDBHelper extends DBHelper {

    private final String TABLE_NAME = Product.class.getSimpleName();
    private final String FIELD_ID = "ID";
    private final String FIELD_ProductId = "ProductId";
    private final String FIELD_ProductName = "ProductName";
    private final String FIELD_ProductName2 = "ProductName2";
    private final String FIELD_ProductDesc = "ProductDesc";
    private final String FIELD_Category = "Category";
    private final String FIELD_PicId = "PicId";
    private final String FIELD_PicName = "PicName";
    private final String FIELD_Rank = "Rank";

    public ProductDBHelper(@Nullable Context context) {
        super(context);
    }

    public long insert(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(FIELD_ProductId, product.getProductId());
        cv.put(FIELD_ProductName, product.getProductName());
        cv.put(FIELD_ProductName2, product.getProductName2());
        cv.put(FIELD_ProductDesc, product.getProductDesc());
        cv.put(FIELD_Category, product.getCategory());
        cv.put(FIELD_PicId, product.getPicId());
        cv.put(FIELD_PicName, product.getPicName());
        cv.put(FIELD_Rank, product.getRank());

        return db.insert(TABLE_NAME, null, cv);
    }

    public List<Product> select() {
        List<Product> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] col = {FIELD_ID, FIELD_ProductId, FIELD_ProductName, FIELD_ProductName2, FIELD_ProductDesc, FIELD_Category, FIELD_PicId, FIELD_PicName, FIELD_Rank};
        Cursor cursor = db.query(TABLE_NAME, col, null, null, null, null, null);
        if (cursor.getCount() == 0) {
            cursor.close();
            return result;
        }

        while (cursor.moveToNext()) {

            int ID = cursor.getInt(cursor.getColumnIndex(FIELD_ID));
            int productId = cursor.getInt(cursor.getColumnIndex(FIELD_ProductId));
            String productName = cursor.getString(cursor.getColumnIndex(FIELD_ProductName));
            String productName2 = cursor.getString(cursor.getColumnIndex(FIELD_ProductName2));
            String productDesc = cursor.getString(cursor.getColumnIndex(FIELD_ProductDesc));
            String category = cursor.getString(cursor.getColumnIndex(FIELD_Category));
            int picId = cursor.getInt(cursor.getColumnIndex(FIELD_PicId));
            String picName = cursor.getString(cursor.getColumnIndex(FIELD_PicName));
            int rank = cursor.getInt(cursor.getColumnIndex(FIELD_Rank));

            Product product = new Product(ID, productId, productName, productName2, productDesc, category, picId, picName, rank);            result.add(product);
        }

        cursor.close();
        return result;
    }

    public List<Product> select(String param) {
        List<Product> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] col = {FIELD_ID, FIELD_ProductId, FIELD_ProductName, FIELD_ProductName2, FIELD_ProductDesc, FIELD_Category, FIELD_PicId, FIELD_PicName, FIELD_Rank};
        Cursor cursor = db.query(TABLE_NAME, col, "ProductName=?", new String[]{param}, null, null, null);
        if (cursor.getCount() == 0)
            return result;

        while (cursor.moveToNext()) {

            int ID = cursor.getInt(cursor.getColumnIndex(FIELD_ID));
            int productId = cursor.getInt(cursor.getColumnIndex(FIELD_ProductId));
            String productName = cursor.getString(cursor.getColumnIndex(FIELD_ProductName));
            String productName2 = cursor.getString(cursor.getColumnIndex(FIELD_ProductName2));
            String productDesc = cursor.getString(cursor.getColumnIndex(FIELD_ProductDesc));
            String category = cursor.getString(cursor.getColumnIndex(FIELD_Category));
            int picId = cursor.getInt(cursor.getColumnIndex(FIELD_PicId));
            String picName = cursor.getString(cursor.getColumnIndex(FIELD_PicName));
            int rank = cursor.getInt(cursor.getColumnIndex(FIELD_Rank));

            Product product = new Product(ID, productId, productName, productName2, productDesc, category, picId, picName, rank);
            result.add(product);
        }
        cursor.close();
        return result;
    }

    public Product select(int paramId) {

        SQLiteDatabase db = this.getReadableDatabase();
        String[] col = {FIELD_ID, FIELD_ProductId, FIELD_ProductName, FIELD_ProductName2, FIELD_ProductDesc, FIELD_Category, FIELD_PicId, FIELD_PicName, FIELD_Rank};
        Cursor cursor = db.query(TABLE_NAME, col, "ID=?", new String[]{String.valueOf(paramId)}, null, null, null);
        if (cursor.getCount() == 0)
            return null;

        while (cursor.moveToNext()) {
            int ID = cursor.getInt(cursor.getColumnIndex(FIELD_ID));
            int productId = cursor.getInt(cursor.getColumnIndex(FIELD_ProductId));
            String productName = cursor.getString(cursor.getColumnIndex(FIELD_ProductName));
            String productName2 = cursor.getString(cursor.getColumnIndex(FIELD_ProductName2));
            String productDesc = cursor.getString(cursor.getColumnIndex(FIELD_ProductDesc));
            String category = cursor.getString(cursor.getColumnIndex(FIELD_Category));
            int picId = cursor.getInt(cursor.getColumnIndex(FIELD_PicId));
            String picName = cursor.getString(cursor.getColumnIndex(FIELD_PicName));
            int rank = cursor.getInt(cursor.getColumnIndex(FIELD_Rank));

            Product product = new Product(ID, productId, productName, productName2, productDesc, category, picId, picName, rank);

            cursor.close();
            return product;
        }
        return null;
    }

    public Product selectByProductId(int pId) {

        SQLiteDatabase db = this.getReadableDatabase();
        String[] col = {FIELD_ID, FIELD_ProductId, FIELD_ProductName, FIELD_ProductName2, FIELD_ProductDesc, FIELD_Category, FIELD_PicId, FIELD_PicName, FIELD_Rank};
        Cursor cursor = db.query(TABLE_NAME, col, "ProductId=?", new String[]{String.valueOf(pId)}, null, null, null);
        if (cursor.getCount() == 0)
            return null;

        while (cursor.moveToNext()) {
            int ID = cursor.getInt(cursor.getColumnIndex(FIELD_ID));
            int productId = cursor.getInt(cursor.getColumnIndex(FIELD_ProductId));
            String productName = cursor.getString(cursor.getColumnIndex(FIELD_ProductName));
            String productName2 = cursor.getString(cursor.getColumnIndex(FIELD_ProductName2));
            String productDesc = cursor.getString(cursor.getColumnIndex(FIELD_ProductDesc));
            String category = cursor.getString(cursor.getColumnIndex(FIELD_Category));
            int picId = cursor.getInt(cursor.getColumnIndex(FIELD_PicId));
            String picName = cursor.getString(cursor.getColumnIndex(FIELD_PicName));
            int rank = cursor.getInt(cursor.getColumnIndex(FIELD_Rank));

            Product product = new Product(ID, productId, productName, productName2, productDesc, category, picId, picName, rank);

            cursor.close();
            return product;
        }
        return null;
    }

    public int update(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(FIELD_ProductId, product.getProductId());
        cv.put(FIELD_ProductName, product.getProductName());
        cv.put(FIELD_ProductName2, product.getProductName2());
        cv.put(FIELD_ProductDesc, product.getProductDesc());
        cv.put(FIELD_Category, product.getCategory());
        cv.put(FIELD_PicId, product.getPicId());
        cv.put(FIELD_PicName, product.getPicName());
        cv.put(FIELD_Rank, product.getRank());

        return db.update(TABLE_NAME, cv, "ID=?", new String[]{String.valueOf(product.getID())});
    }

    public int delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID=?", new String[]{String.valueOf(id)});
    }
}
