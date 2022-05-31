package com.example.capstonrestorego.Model;

import android.graphics.Bitmap;


public class Post {

    private String tokenid;
    private Bitmap photo;
    private String title;
    private String location;
    private String description;
    private String username;


    public Post()
    {
    }

    public Post(String tokenid, Bitmap photo, String title, String location, String description) {
        this.tokenid = tokenid;
        this.photo = photo;
        this.title = title;
        this.location = location;
        this.description = description;
        this.username= username;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername(){ return username; }

    public void setUsername(String username) {this.username=username;}
}
