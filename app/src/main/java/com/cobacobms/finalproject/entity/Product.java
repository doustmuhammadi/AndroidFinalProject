package com.cobacobms.finalproject.entity;

public class Product {
    private int ID;
    private int ProductId;
    private String ProductName;
    private String ProductName2;
    private String ProductDesc;
    private String Category;
    private int PicId;
    private String PicName;
    private int Rank;


    public Product(int productId, String productName, String productName2, String productDesc, String category, int picId, String picName, int rank) {
        ProductId = productId;
        ProductName = productName;
        ProductName2 = productName2;
        ProductDesc = productDesc;
        Category = category;
        PicId = picId;
        PicName = picName;
        Rank = rank;
    }

    public Product(int ID, int productId, String productName, String productName2, String productDesc, String category, int picId, String picName, int rank) {
        this.ID = ID;
        ProductId = productId;
        ProductName = productName;
        ProductName2 = productName2;
        ProductDesc = productDesc;
        Category = category;
        PicId = picId;
        PicName = picName;
        Rank = rank;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductName2() {
        return ProductName2;
    }

    public void setProductName2(String productName2) {
        ProductName2 = productName2;
    }

    public String getProductDesc() {
        return ProductDesc;
    }

    public void setProductDesc(String productDesc) {
        ProductDesc = productDesc;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getPicId() {
        return PicId;
    }

    public void setPicId(int picId) {
        PicId = picId;
    }

    public String getPicName() {
        return PicName;
    }

    public void setPicName(String picName) {
        PicName = picName;
    }

    public int getRank() {
        return Rank;
    }

    public void setRank(int rank) {
        Rank = rank;
    }
}
