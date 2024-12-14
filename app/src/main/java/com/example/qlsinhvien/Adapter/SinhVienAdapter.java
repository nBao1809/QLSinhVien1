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


import com.example.qlsinhvien.Models.LopHanhChinh;
import com.example.qlsinhvien.Models.SinhVien;

import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;
import com.example.qlsinhvien.dao.LopHanhChinhManager;
import com.example.qlsinhvien.dao.UserManager;


import java.util.ArrayList;
import java.util.List;

public class SinhVienAdapter extends RecyclerView.Adapter<SinhVienAdapter.ClassViewHolder> implements Filterable {
    private UserManager userManager;
    private LopHanhChinhManager lopHanhChinhManager;
    private Context context;
    private List<SinhVien> sinhVienList, sinhVienListOld;


    public SinhVienAdapter(Context context) {
        this.context = context;
        userManager = new UserManager(context);
        lopHanhChinhManager=new LopHanhChinhManager(context);
    }

    public void setData(List<SinhVien> sinhVienList) {
        if(sinhVienList==null)
            return;
        this.sinhVienList = sinhVienList;
        this.sinhVienListOld = sinhVienList;
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
        SinhVien giangVien = sinhVienList.get(position);
        if (giangVien == null)
            return;
        //holder.txtNganh.setText(diem.getMaMonHoc())
    }

    @Override
    public int getItemCount() {
        if (sinhVienList != null)
            return sinhVienList.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString = StringUtility.removeMark(constraint.toString().toLowerCase());
                if (searchString.isEmpty()) {
                    sinhVienList = sinhVienListOld;
                } else {
                    List<SinhVien> list = new ArrayList<>();
                    for (SinhVien sinhVien : sinhVienListOld) {
                        String mssv =
                                StringUtility.removeMark(sinhVien.getHoTen().toLowerCase());
                        String tenSinhVien =
                                StringUtility.removeMark(sinhVien.getHoTen().toLowerCase());
                        if (mssv.contains(searchString)) {
                            list.add(sinhVien);
                        }
                        if (tenSinhVien.contains(searchString)) {
                            list.add(sinhVien);
                        }
                    }
                    sinhVienList= list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = sinhVienList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                sinhVienList = (List<SinhVien>) results.values;
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
