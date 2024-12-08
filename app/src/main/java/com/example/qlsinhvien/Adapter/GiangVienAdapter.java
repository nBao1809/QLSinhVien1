package com.example.qlsinhvien.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.qlsinhvien.Models.GiangVien;

import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;
import com.example.qlsinhvien.dao.UserManager;


import java.util.ArrayList;
import java.util.List;

public class GiangVienAdapter extends RecyclerView.Adapter<GiangVienAdapter.ClassViewHolder> implements Filterable {
    private UserManager userManager;
    private Context context;
    private List<GiangVien> giangVienList, giangVienListOld;
    private User user, userOld;

    public GiangVienAdapter(Context context) {
        this.context = context;
        userManager = new UserManager(context);
    }

    public void setData(List<GiangVien> giangVienList) {
        this.giangVienList = giangVienList;
        this.giangVienListOld = giangVienList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlophocphan, parent, false);
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        GiangVien giangVien = giangVienList.get(position);
        if (giangVien == null)
            return;
        //holder.txtNganh.setText(diem.getMaMonHoc())
    }

    @Override
    public int getItemCount() {
        if (giangVienList != null)
            return giangVienList.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString = StringUtility.removeMark(constraint.toString().toLowerCase());
                if (searchString.isEmpty()) {
                    giangVienList = giangVienListOld;
                } else {
                    List<GiangVien> list = new ArrayList<>();
                    for (GiangVien giangVien : giangVienListOld) {
                        String tenGiangVien = StringUtility.removeMark(giangVien.getHoTen().toLowerCase());
                        String maGiangVien = StringUtility.removeMark(giangVien.getMaGiangVien().toLowerCase());
                        if (tenGiangVien.contains(searchString)) {
                            list.add(giangVien);
                        }
                        if (maGiangVien.contains(searchString)) {
                            list.add(giangVien);
                        }
                    }
                    giangVienListOld = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = giangVienList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                giangVienList = (List<GiangVien>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView txtNganh, txtMonHoc, txtGVPhuTrach, txtLop;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            //txtNganh = itemView.findViewById(R.id.txtNganh);
        }
    }

}
