package com.example.capstonrestorego.Model;

import java.io.Serializable;

public class User implements Serializable{
    private String address;
    private String PVkey;

    public User()  {
    }

    public User(String address, String PVkey) {
        this.address = address;
        this.PVkey = PVkey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPVkey() {
        return PVkey;
    }

    public void setPVkey(String PVkey) {
        this.PVkey = PVkey;
    }
}
