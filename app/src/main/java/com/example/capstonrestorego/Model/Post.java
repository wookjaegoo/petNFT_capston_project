package com.example.capstonrestorego.Model;

import android.graphics.Bitmap;


public class Post {

    private String tokenid;
    private Bitmap photo;
    private String price;
    private String information1;
    private String information2;
    private String username;


    public Post() {
    }

    public Post(String tokenid, Bitmap photo, String price, String information1, String information2, String username) {
        this.tokenid = tokenid;
        this.photo = photo;
        this.price = price;
        this.information1 = information1;
        this.information2 = information2;
        this.username = username;
    }

    public String getTokenid() {
        return tokenid;
    }

    public void setTokenid(String tokenid) {
        this.tokenid = tokenid;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInformation1() {
        return information1;
    }

    public void setInformation1(String information1) {
        this.information1 = information1;
    }

    public String getInformation2() {
        return information2;
    }

    public void setInformation2(String information2) {
        this.information2 = information2;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
