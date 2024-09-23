package com.example.otpscreendemo.models;

import com.google.gson.annotations.SerializedName;

public class VerifyOtpResponse {

    @SerializedName("resultCode")
    private int resultCode;

    @SerializedName("result")
    private String result;

    @SerializedName("message")
    private String message;

    // Getter and Setter for resultCode
    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    // Getter and Setter for result
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    // Getter and Setter for message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
