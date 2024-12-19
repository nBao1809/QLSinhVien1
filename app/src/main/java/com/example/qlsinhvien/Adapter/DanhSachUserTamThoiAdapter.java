package com.example.qlsinhvien.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;
import com.example.qlsinhvien.dao.RoleManager;
import com.example.qlsinhvien.dao.UserManager;

import java.util.ArrayList;
import java.util.List;

public class DanhSachUserTamThoiAdapter extends RecyclerView.Adapter<DanhSachUserTamThoiAdapter.DanhSachUserViewHolder> implements Filterable {
    private UserManager userManager;
    private RoleManager roleManager;
    private Context context;
    private List<User> userList, userListOld;


    public DanhSachUserTamThoiAdapter(Context context) {
        this.context = context;
        userManager = new UserManager(context);
        roleManager = new RoleManager(context);
    }


    public void setData(List<User> userList) {
        this.userListOld = userList;
        this.userList = userList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public DanhSachUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdanhsachsinhvientemp, parent, false);


        return new DanhSachUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhSachUserViewHolder holder, int position) {
        User user = userList.get(position);
        if (user == null)
            return;

        holder.txtUsename.setText(user.getUsername());
        holder.txtmail.setText(user.getEmail());
        holder.txtRole.setText(roleManager.getRole(user.getRole()).getTenRole());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positionTam = holder.getAdapterPosition();
                if (positionTam != RecyclerView.NO_POSITION) {
                    int idBiXoa = userList.get(positionTam).getID();
                    userList.remove(positionTam);
                    updatUserList(idBiXoa);
                    notifyItemRemoved(positionTam);
                }
            }
        });


    }

    public void updatUserList(int idBiXoa) {
        for (int i = 0; i < userList.size(); i++) {
            if (idBiXoa + 1 == userList.get(i).getID()) {
                User user = userList.get(i);
                user.setID(idBiXoa);
                idBiXoa = userList.get(i).getID();
            }
            Log.d("test", String.valueOf(userList.get(i).getID()));
        }
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
                        String username =
                                StringUtility.removeMark(user.getUsername().toLowerCase());
                        if (username.startsWith(searchString) || username.contains(searchString)) {
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

    public class DanhSachUserViewHolder extends RecyclerView.ViewHolder {
        TextView txtUsename, txtmail, txtRole;
        CardView itemLayout;
        ImageButton btnDelete;

        public DanhSachUserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUsename = itemView.findViewById(R.id.txtUsername);
            txtmail = itemView.findViewById(R.id.txtmail);
            txtRole = itemView.findViewById(R.id.txtRole);
            itemLayout = itemView.findViewById(R.id.layoutItem);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public void release() {
        context = null;

    }
}
