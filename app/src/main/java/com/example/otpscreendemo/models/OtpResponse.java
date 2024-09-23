package com.example.otpscreendemo.models;

import com.google.gson.annotations.SerializedName;

public class OtpResponse {

    @SerializedName("resultCode")
    private int resultCode;

    @SerializedName("result")
    private String result;

    @SerializedName("message")
    private String message;

    @SerializedName("OTP")
    private int otp;

    public int getResultCode() {
        return resultCode;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public int getOtp() {
        return otp;
    }
}
