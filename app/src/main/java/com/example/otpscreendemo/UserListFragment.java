package com.example.otpscreendemo;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.otpscreendemo.databinding.FragmentUserListBinding;
import com.example.otpscreendemo.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends Fragment {

    private FragmentUserListBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout using ViewBinding
        binding = FragmentUserListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up the RecyclerView with user data
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<User> users = new ArrayList<>();
        users.add(new User("Aarusi Sharma", "9876543210", "MG Road, Bengaluru", R.drawable.profile1));
        users.add(new User("Ishita Verma", "9123456789", "Sector 18, Noida", R.drawable.profile2));
        users.add(new User("Rajshree Patel", "9988776655", "Connaught Place, Delhi", R.drawable.profile3));
        users.add(new User("Sarvesh Singh", "9001234567", "Marine Drive, Mumbai", R.drawable.profile4));
        users.add(new User("Karan Mehta", "8765432109", "Salt Lake, Kolkata", R.drawable.profile5));
        users.add(new User("Rohit Kumar", "9012345678", "Anna Nagar, Chennai", R.drawable.profile6));
        users.add(new User("Ananya Iyer", "9878901234", "Banjara Hills, Hyderabad", R.drawable.profile7));

        UserAdapter adapter = new UserAdapter(getContext(), users);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;  // Avoid memory leaks
    }
}
