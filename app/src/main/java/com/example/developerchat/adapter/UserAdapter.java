package com.example.developerchat.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.developerchat.R;
import com.example.developerchat.activity.ChatUserActivity;
import com.example.developerchat.models.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    Context dashboardMainActivity;
    ArrayList<User> userArrayList;
    public UserAdapter(com.example.developerchat.activity.dashboardMainActivity dashboardMainActivity, ArrayList<User> userArrayList) {

        this.dashboardMainActivity = dashboardMainActivity;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View wiew = LayoutInflater.from(dashboardMainActivity).inflate(R.layout.item_user_row,parent,false);
        return new ViewHolder(wiew);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user  = userArrayList.get(position);

        if(FirebaseAuth.getInstance().getCurrentUser().equals(user.getUid())){

            holder.itemView.setVisibility(View.GONE);
        }
        holder.user_name.setText(user.getName()+" "+user.getLastName());
        holder.user_status.setText(user.getStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(dashboardMainActivity, ChatUserActivity.class);
               intent.putExtra("name",user.getName());
               intent.putExtra("lasName",user.getLastName());
               intent.putExtra("uid",user.getUid());
               intent.putExtra("status",user.getStatus());
               dashboardMainActivity.startActivity(intent);
           }
        });

    }


    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView user_name;
        TextView user_status;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            user_name = itemView.findViewById(R.id.user_name);
            user_status = itemView.findViewById(R.id.user_status);
        }

    }
}
