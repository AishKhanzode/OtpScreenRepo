package com.example.otpscreendemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.otpscreendemo.databinding.ActivityOtpVerificationBinding;
import com.example.otpscreendemo.models.VerifyOtpResponse;
import com.example.otpscreendemo.network.ApiClient;
import com.example.otpscreendemo.network.ApiService;
import com.example.otpscreendemo.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerificationActivity extends AppCompatActivity {

    // ViewBinding variable
    private ActivityOtpVerificationBinding binding;
    private String phoneNumber;
    private ProgressBar progressBar;  // Added ProgressBar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using ViewBinding
        binding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ProgressBar
        progressBar = findViewById(R.id.progressBar);

        // Log to check whether the app is detecting no internet
        Log.d("OtpVerificationActivity", "Checking internet connection");

        // Check for internet connection
        if (!NetworkUtil.isInternetAvailable(this)) {
            Log.d("OtpVerificationActivity", "No internet connection detected");
            showNoInternetDialog();
            return; // Stop further execution if no internet
        }

        Log.d("OtpVerificationActivity", "Internet connection available");

        // Get phone number from Intent
        phoneNumber = getIntent().getStringExtra("phoneNumber");

        // Set up OTP input fields to move to next/previous field automatically
        setupOtpInputs();

        // Set the click listener using ViewBinding
        binding.btnVerify.setOnClickListener(v -> {
            // Show loading when verification starts
            showLoading();
            verifyOtp();
        });

        binding.tvResend.setOnClickListener(v -> {
            // Code to resend OTP
        });
    }

    // Method to show ProgressBar
    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);  // Show ProgressBar
    }

    // Method to hide ProgressBar
    private void hideLoading() {
        progressBar.setVisibility(View.GONE);  // Hide ProgressBar
    }

    // Method to set up TextWatcher for each OTP field
    private void setupOtpInputs() {
        EditText[] otpFields = {
                binding.etOtp1, binding.etOtp2, binding.etOtp3,
                binding.etOtp4, binding.etOtp5, binding.etOtp6
        };

        for (int i = 0; i < otpFields.length; i++) {
            final int currentIndex = i;

            otpFields[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // No action needed here
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // If text is entered, move to next field
                    if (s.length() == 1 && currentIndex < otpFields.length - 1) {
                        otpFields[currentIndex + 1].requestFocus();
                    }
                    // If text is deleted and backspace, move to previous field
                    else if (s.length() == 0 && currentIndex > 0) {
                        otpFields[currentIndex - 1].requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // No action needed here
                }
            });
        }
    }

    private void verifyOtp() {
        // Check internet connection before verifying OTP
        if (!NetworkUtil.isInternetAvailable(this)) {
            showNoInternetDialog();
            return;
        }

        // Concatenate OTP fields into a single string using ViewBinding
        String otp = binding.etOtp1.getText().toString() +
                binding.etOtp2.getText().toString() +
                binding.etOtp3.getText().toString() +
                binding.etOtp4.getText().toString() +
                binding.etOtp5.getText().toString() +
                binding.etOtp6.getText().toString();

        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<VerifyOtpResponse> call = apiService.verifyOtp("123456", phoneNumber, otp);

        call.enqueue(new Callback<VerifyOtpResponse>() {
            @Override
            public void onResponse(Call<VerifyOtpResponse> call, Response<VerifyOtpResponse> response) {
                // Hide ProgressBar when response is received
                hideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    VerifyOtpResponse verifyOtpResponse = response.body();

                    if (verifyOtpResponse.getResultCode() == 200) {
                        // Log and show message for successful verification
                        Log.d("OtpVerificationActivity", "Response: " + verifyOtpResponse.getMessage());
                        Toast.makeText(OtpVerificationActivity.this, "OTP Verified Successfully", Toast.LENGTH_SHORT).show();

                        // Check the response message to determine if the user is registered
                        if (verifyOtpResponse.getMessage().equals("User Registered with QD")) {
                            navigateToBottomNavigationPage();
                        } else {
                            Toast.makeText(OtpVerificationActivity.this, "User not Registered with QD", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Show error message if OTP verification failed
                        Toast.makeText(OtpVerificationActivity.this, verifyOtpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Show a general error message for unsuccessful response
                    Toast.makeText(OtpVerificationActivity.this, "OTP Verification Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VerifyOtpResponse> call, Throwable t) {
                // Hide ProgressBar if there's a failure
                hideLoading();
                // Show a message for network failure
                Toast.makeText(OtpVerificationActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToBottomNavigationPage() {
        Intent intent = new Intent(OtpVerificationActivity.this, BottomNavigationActivity.class);
        startActivity(intent);
    }

    // Method to show No Internet Dialog
    private void showNoInternetDialog() {
        Log.d("OtpVerificationActivity", "Showing no internet dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Internet");
        builder.setMessage("Please check your internet connection and try again.");
        builder.setCancelable(false); // Prevent dismissing the dialog by clicking outside it.
        builder.setIcon(R.drawable.ic_no_internet); // Assuming you have an icon for "no internet"

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Close the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
