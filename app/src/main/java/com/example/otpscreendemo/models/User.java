package com.example.otpscreendemo.models;
public class User {
    private String name;
    private String mobileNumber;
    private String address;
    private int imageResId;

    public User(String name, String mobileNumber, String address, int imageResId) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public String getMobileNumber() { return mobileNumber; }
    public String getAddress() { return address; }
    public int getImageResId() { return imageResId; }
}

