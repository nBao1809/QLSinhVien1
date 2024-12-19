package com.example.qlsinhvien.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;


import com.example.qlsinhvien.Models.GiangVien;
import com.example.qlsinhvien.Models.SinhVien;
import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.Activities.ThongTinChiTietUserActivity;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;
import com.example.qlsinhvien.dao.GiangVienManager;
import com.example.qlsinhvien.dao.SinhVienManager;


import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {


    private List<User> userList, userListOld;
    private User currentUser;
    private GiangVienManager giangVienManager;
    private SinhVienManager sinhVienManager;
    public static final int VIEW_TYPE_ADMIN = 0;
    public static final int VIEW_TYPE_MOD = 1;
    public static final int VIEW_TYPE_GIANGVIEN = 2;
    public static final int VIEW_TYPE_SINHVIEN = 3;

    public UserAdapter(Context context, User user) {
        this.currentUser = user;
        giangVienManager = new GiangVienManager(context);
        sinhVienManager = new SinhVienManager(context);
    }

    public void setData(List<User> userList) {
        if (userList == null) return;
        this.userList = userList;
        this.userListOld = userList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        User user = userList.get(position);
        if (user.getRole().equals("admin")) {
            return VIEW_TYPE_ADMIN;
        } else if (user.getRole().equals("mod")) {
            return VIEW_TYPE_MOD;
        } else if (user.getRole().equals("gv")) {
            return VIEW_TYPE_GIANGVIEN;
        } else if (user.getRole().equals("sv")) {
            return VIEW_TYPE_SINHVIEN;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ADMIN) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemuser_minmod, parent, false);
            return new AdminViewHolder(view);
        } else if (viewType == VIEW_TYPE_MOD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemuser_minmod, parent, false);
            return new GiangVienViewHolder(view);
        } else if (viewType == VIEW_TYPE_GIANGVIEN) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemuser_gv, parent, false);
            return new GiangVienViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemuser_sv, parent, false);
            return new SinhVienViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User user = userList.get(position);
        if (user == null) return;
        if (holder instanceof AdminViewHolder) {
            AdminViewHolder viewHolder = (AdminViewHolder) holder;
            viewHolder.txtID.setText(String.valueOf(user.getID()));
            viewHolder.txtUsername.setText(user.getUsername());
            viewHolder.txtEmail.setText(user.getEmail());
            viewHolder.txtRole.setText(user.getRole());
            viewHolder.photo.setImageBitmap(user.getPhoto());
            if (currentUser.getRole().equals("admin") || currentUser.getRole().equals("mod")) {
                viewHolder.btnEdit.setVisibility(View.GONE);
                viewHolder.btnDelete.setVisibility(View.GONE);
            }
            viewHolder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(holder.itemView.getContext(), ThongTinChiTietUserActivity.class);
                intent.putExtra("userID", user.getID());
                holder.itemView.getContext().startActivity(intent);
            });
        }
        if (holder instanceof ModViewHolder) {
            ModViewHolder viewHolder = (ModViewHolder) holder;
            viewHolder.txtID.setText(String.valueOf(user.getID()));
            viewHolder.txtUsername.setText(user.getUsername());
            viewHolder.txtEmail.setText(user.getEmail());
            viewHolder.txtRole.setText(user.getRole());
            viewHolder.photo.setImageBitmap(user.getPhoto());
            if (currentUser.getRole().equals("mod")) {
                viewHolder.btnEdit.setVisibility(View.GONE);
                viewHolder.btnDelete.setVisibility(View.GONE);
            }
            viewHolder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(holder.itemView.getContext(), ThongTinChiTietUserActivity.class);
                intent.putExtra("userID", user.getID());
                holder.itemView.getContext().startActivity(intent);
            });
        }
        if (holder instanceof GiangVienViewHolder) {
            GiangVien giangVien = giangVienManager.getGiangVienFromUser(user.getID());
            GiangVienViewHolder viewHolder = (GiangVienViewHolder) holder;
            viewHolder.txtID.setText(String.valueOf(user.getID()));
            viewHolder.txtUsername.setText(user.getUsername());
            viewHolder.txtEmail.setText(user.getEmail());
            viewHolder.txtRole.setText(user.getRole());
            viewHolder.photo.setImageBitmap(user.getPhoto());
            viewHolder.txtMSGV.setText(giangVien.getMaGiangVien());
            viewHolder.txtTen.setText(giangVien.getHoTen());
            viewHolder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(holder.itemView.getContext(), ThongTinChiTietUserActivity.class);
                intent.putExtra("userID", user.getID());
                holder.itemView.getContext().startActivity(intent);
            });
        }
        if (holder instanceof SinhVienViewHolder) {
            SinhVien sinhVien = sinhVienManager.getSinhVienFromUser(user.getID());
            SinhVienViewHolder viewHolder = (SinhVienViewHolder) holder;
            viewHolder.txtID.setText(String.valueOf(user.getID()));
            viewHolder.txtUsername.setText(user.getUsername());
            viewHolder.txtEmail.setText(user.getEmail());
            viewHolder.txtRole.setText(user.getRole());
            viewHolder.photo.setImageBitmap(user.getPhoto());
            viewHolder.txtMSSV.setText(sinhVien.getMssv());
            viewHolder.txtTen.setText(sinhVien.getHoTen());
            viewHolder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(holder.itemView.getContext(), ThongTinChiTietUserActivity.class);
                intent.putExtra("userID", user.getID());
                holder.itemView.getContext().startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        if (userList != null) return userList.size();
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

    public class AdminViewHolder extends RecyclerView.ViewHolder {
        TextView txtID, txtUsername, txtEmail, txtRole;
        ImageView photo;
        ImageButton btnEdit, btnDelete;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            txtID = itemView.findViewById(R.id.txtID);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtRole = itemView.findViewById(R.id.txtRole);
            photo = itemView.findViewById(R.id.photo);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public class ModViewHolder extends RecyclerView.ViewHolder {
        TextView txtID, txtUsername, txtEmail, txtRole;
        ImageView photo;
        ImageButton btnEdit, btnDelete;

        public ModViewHolder(@NonNull View itemView) {
            super(itemView);
            txtID = itemView.findViewById(R.id.txtID);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtRole = itemView.findViewById(R.id.txtRole);
            photo = itemView.findViewById(R.id.photo);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public class GiangVienViewHolder extends RecyclerView.ViewHolder {
        TextView txtID, txtUsername, txtEmail, txtRole, txtMSGV, txtTen;
        ImageView photo;
        ImageButton btnEdit;



        public GiangVienViewHolder(@NonNull View itemView) {
            super(itemView);
            txtID = itemView.findViewById(R.id.txtID);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtRole = itemView.findViewById(R.id.txtRole);
            photo = itemView.findViewById(R.id.photo);
            txtMSGV = itemView.findViewById(R.id.txtMSGV);
            txtTen = itemView.findViewById(R.id.txtTen);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }

    public class SinhVienViewHolder extends RecyclerView.ViewHolder {
        TextView txtID, txtUsername, txtEmail, txtRole, txtMSSV, txtTen;
        ImageView photo;
        ImageButton btnEdit;


        public SinhVienViewHolder(@NonNull View itemView) {
            super(itemView);
            txtID = itemView.findViewById(R.id.txtID);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtRole = itemView.findViewById(R.id.txtRole);
            photo = itemView.findViewById(R.id.photo);
            txtMSSV = itemView.findViewById(R.id.txtMSSV);
            txtTen = itemView.findViewById(R.id.txtTen);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }


}
