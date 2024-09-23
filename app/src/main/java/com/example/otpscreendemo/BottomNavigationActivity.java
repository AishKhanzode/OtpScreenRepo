package com.example.otpscreendemo;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.otpscreendemo.databinding.ActivityBottomNavigationBinding;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNavigationActivity extends AppCompatActivity {

    private ActivityBottomNavigationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using ViewBinding
        binding = ActivityBottomNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up Bottom Navigation
        setupBottomNavigation();

        // Show the UserListFragment by default when the activity starts
        loadFragment(new UserListFragment());
    }

    private void setupBottomNavigation() {
        // Set up BottomNavigationView with a listener for item selection
        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.nav_home) {
                    selectedFragment = new UserListFragment();  // Navigate to user list
                } else if (item.getItemId() == R.id.nav_messages) {
                    Toast.makeText(BottomNavigationActivity.this, "Messages clicked", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.nav_search) {
                    Toast.makeText(BottomNavigationActivity.this, "Search clicked", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.nav_history) {
                    Toast.makeText(BottomNavigationActivity.this, "History clicked", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.nav_profile) {
                    Toast.makeText(BottomNavigationActivity.this, "Profile clicked", Toast.LENGTH_SHORT).show();
                }

                if (selectedFragment != null) {
                    loadFragment(selectedFragment);  // Load the selected fragment
                    return true;
                }
                return false;
            }
        });
    }

    // Helper method to load fragments
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}

