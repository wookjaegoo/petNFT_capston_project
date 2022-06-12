package com.example.capstonrestorego.Model;

public class UserInfo {

    private String useradd;

    private static final UserInfo ourInstance = new UserInfo();

    public static UserInfo getInstance() {
        return ourInstance;
    }

    private UserInfo() {
    }

    public String getUseradd() {
        return useradd;
    }

    public void setUseradd(String useradd) {
        this.useradd = useradd;
    }
}
