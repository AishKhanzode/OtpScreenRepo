package com.example.otpscreendemo.network;

import com.example.otpscreendemo.models.OtpResponse;
import com.example.otpscreendemo.models.VerifyOtpResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("generate_otp")
    Call<OtpResponse> generateOtp(
            @Header("Authorization") String authToken,
            @Field("mobile_number") String mobileNumber
    );

    @FormUrlEncoded
    @POST("verify_otp")
    Call<VerifyOtpResponse> verifyOtp(
            @Header("Authorization") String authorization,
            @Field("mobile_number") String mobileNumber,
            @Field("otp") String otp
    );
}

