package com.example.qlsinhvien.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;


import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.Activities.SingleUserActivity;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;


import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ClassViewHolder> implements Filterable {


    private List<User> userList, userListOld;


    public UserAdapter() {
    }

    public void setData(List<User> userList) {
        if (userList == null)
            return;
        this.userList = userList;
        this.userListOld = userList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (viewType == 0) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemuser, parent,
                false);
        return new ClassViewHolder(view);
//        } else if (viewType == 1) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemuser, parent, false);
//            return new AdminViewHolder(view);
//        } else if (viewType == 2) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemuser, parent,
//                    false);
//            return new AdminViewHolder(view);
//        } else {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemuser, parent, false);
//            return new AdminViewHolder(view);
//        }
    }

    @Override
    public void onBindViewHolder(ClassViewHolder holder, int position) {
        User user = userList.get(position);
        if (user == null)
            return;
        holder.txtID.setText(String.valueOf(user.getID()));
        holder.txtUsername.setText(user.getUsername());
        holder.txtEmail.setText(user.getEmail());
        holder.txtRole.setText(user.getRole());
        holder.photo.setImageBitmap(user.getPhoto());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), SingleUserActivity.class);
            intent.putExtra("userID",user.getID());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (userList != null)
            return userList.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString = StringUtility.removeMark(constraint.toString().toLowerCase());
                if (searchString.isEmpty()) {
                    userList = userListOld;
                } else {
                    List<User> list = new ArrayList<>();
                    for (User user : userListOld) {
                        String username = StringUtility.removeMark(user.getUsername().toLowerCase());
                        if (username.contains(searchString)) {
                            list.add(user);
                        }

                    }
                    userList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = userList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                userList = (List<User>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView txtID, txtUsername, txtEmail, txtRole;
        ImageView photo;


        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            txtID = itemView.findViewById(R.id.txtID);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtRole = itemView.findViewById(R.id.txtRole);
            photo = itemView.findViewById(R.id.photo);

        }
    }

}
