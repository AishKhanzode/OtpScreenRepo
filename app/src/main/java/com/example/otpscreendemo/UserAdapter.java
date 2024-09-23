package com.example.otpscreendemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.otpscreendemo.models.User;

import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private List<User> userList;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.nameTextView.setText(user.getName());
        holder.mobileTextView.setText(user.getMobileNumber());
        holder.addressTextView.setText(user.getAddress());
        holder.profileImageView.setImageResource(user.getImageResId());

        // Apply animation to each item
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, mobileTextView, addressTextView;
        CircleImageView profileImageView;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            mobileTextView = itemView.findViewById(R.id.mobileTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            profileImageView = itemView.findViewById(R.id.profileImageView);
        }
    }
}
