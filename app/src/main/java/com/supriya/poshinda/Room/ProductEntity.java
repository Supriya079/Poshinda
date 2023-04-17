package com.supriya.poshinda.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ProductEntity {

    @PrimaryKey(autoGenerate = true)
    int productId;

    String productName;
    String productPrice;
    String productImg;

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }
}
