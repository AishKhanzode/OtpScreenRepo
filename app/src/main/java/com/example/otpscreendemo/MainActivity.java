package com.example.otpscreendemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.otpscreendemo.databinding.ActivityMainBinding;
import com.example.otpscreendemo.models.OtpResponse;
import com.example.otpscreendemo.models.User;
import com.example.otpscreendemo.network.ApiClient;
import com.example.otpscreendemo.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

// Correct import statement
import com.example.otpscreendemo.R;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

//    private EditText etPhoneNumber;
//    private Button btnContinue;
//    private static final String TAG = "OTPResponse";

    // ViewBinding variable
    private ActivityMainBinding binding;
    private UserAdapter userAdapter;
    private List<User> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);

//        etPhoneNumber = findViewById(R.id.etPhoneNumber);
//        btnContinue = findViewById(R.id.btnContinue);

        // Inflate the layout using ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



       binding.btnContinue.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               String phoneNumber = binding.etPhoneNumber.getText().toString();
                                               if (!phoneNumber.isEmpty()) {
                                                   binding.progressBar.setVisibility(View.VISIBLE);  // Show progress bar
                                                   generateOtp(phoneNumber);
                                               } else {
                                                   binding.etPhoneNumber.setError("Please enter your phone number");
                                               }
                                           }
                                       }
        );
    }


    private void navigateToOtpScreen(String phoneNumber) {
        Intent intent = new Intent(MainActivity.this, OtpVerificationActivity.class);
        intent.putExtra("phoneNumber", phoneNumber);
        startActivity(intent);
    }



    private void generateOtp(String phoneNumber) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<OtpResponse> call = apiService.generateOtp("123456", phoneNumber);

        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                binding.progressBar.setVisibility(View.GONE);  // Hide progress bar on success
                if (response.isSuccessful() && response.body() != null) {
                    OtpResponse otpResponse = response.body();
                    if (otpResponse.getResultCode() == 200) {
                        int validOtp = otpResponse.getOtp();

                        // Logging the OTP for debugging
                        Log.d("MainActivity", "OTP: " + validOtp);

                        // Intent to navigate to OTP Verification Activity
                        Intent intent = new Intent(MainActivity.this, OtpVerificationActivity.class);
                        intent.putExtra("phoneNumber", phoneNumber);
                        intent.putExtra("otp", validOtp);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, otpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to generate OTP", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);  // Hide progress bar on failure
                Toast.makeText(MainActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }}