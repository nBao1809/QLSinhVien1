package com.example.qlsinhvien.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.qlsinhvien.Activities.DanhSachLopSinhVienActivity;
import com.example.qlsinhvien.Activities.DiemSinhVien;
import com.example.qlsinhvien.Activities.QuanLyDiemvaInforSinhVien;
import com.example.qlsinhvien.Activities.QuanLySinhVienActivity;
import com.example.qlsinhvien.Models.LopHanhChinh;
import com.example.qlsinhvien.Models.LopHocPhan;
import com.example.qlsinhvien.Models.SinhVien;

import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;
import com.example.qlsinhvien.dao.LopHanhChinhManager;
import com.example.qlsinhvien.dao.LopSinhVienManager;
import com.example.qlsinhvien.dao.SinhVienManager;
import com.example.qlsinhvien.dao.UserManager;


import java.util.ArrayList;
import java.util.List;

public class SinhVienAdapter extends RecyclerView.Adapter<SinhVienAdapter.SinhVienViewHolder> implements Filterable {
    private UserManager userManager;
    private LopHanhChinhManager lopHanhChinhManager;
    private SinhVienManager sinhVienManager;
    private LopSinhVienManager lopSinhVienManager;
    private Context context;
    private List<SinhVien> sinhVienList, sinhVienListOld;
    private LopHocPhan lopHocPhan;
    Boolean bool;


    public SinhVienAdapter(Context context, LopHocPhan lopHocPhan) {
        this.context = context;
        this.lopHocPhan = lopHocPhan;
        userManager = new UserManager(context);
        sinhVienManager = new SinhVienManager(context);
        lopSinhVienManager = new LopSinhVienManager(context);
        lopHanhChinhManager = new LopHanhChinhManager(context);
    }



    public void setData(List<SinhVien> sinhVienList, Boolean bool) {
        this.sinhVienList = sinhVienList;
        this.sinhVienListOld = sinhVienList;
        this.bool = bool;
        notifyDataSetChanged();
    }

    public String dateFormat(double ngaySinhDouble) {
        int nam = (int) ngaySinhDouble;
        int thangNgay = (int) ((ngaySinhDouble - nam) * 10000);
        int thang = thangNgay / 100;
        int ngay = thangNgay % 100;
        String formattedDate = String.format("%02d/%02d/%04d", ngay, thang, nam);
        return formattedDate;
    }

    @NonNull
    @Override
    public SinhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsinhvien, parent, false);
        return new SinhVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SinhVienViewHolder holder, int position) {
        SinhVien sinhVien = sinhVienList.get(position);
        if (sinhVien == null)
            return;
        holder.txtTen.setText(sinhVien.getHoTen());
        holder.txtMSSV.setText(sinhVien.getMssv());
        String formattedDate = dateFormat(sinhVien.getNgaySinh());
        holder.txtNgaySinh.setText(formattedDate);
        holder.txtLopHanhChinh.setText(lopHanhChinhManager.getLopHanhChinh(sinhVien.getMaLopHanhChinh()).getTenLopHanhChinh());
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToLopSinhVien(sinhVien, bool);
            }
        });
    }

    private void onClickGoToLopSinhVien(SinhVien sinhVien, Boolean bool) {
        if (bool) {
            Intent intent = new Intent(context, QuanLyDiemvaInforSinhVien.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("sinhVien", sinhVien);
            bundle.putSerializable("lopHocPhan", lopHocPhan);
            intent.putExtras(bundle);
            context.startActivity(intent);
        } else {
            Intent intent = new Intent(context, DiemSinhVien.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("sinhVien", sinhVien);
            bundle.putSerializable("lopHocPhan", lopHocPhan);
            intent.putExtras(bundle);
            context.startActivity(intent);

        }
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
                                StringUtility.removeMark(sinhVien.getMssv().toLowerCase());
                        String tenSinhVien =
                                StringUtility.removeMark(sinhVien.getHoTen().toLowerCase());
                        if (mssv.startsWith(searchString) || tenSinhVien.contains(searchString) || mssv.contains(searchString) || tenSinhVien.startsWith(searchString)) {
                            list.add(sinhVien);
                        }

                    }
                    sinhVienList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = sinhVienList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                sinhVienList = (List<SinhVien>) results.values;
                if (sinhVienList == null || sinhVienList.isEmpty()) {
                    // Hiển thị thông báo không tìm thấy sinh viên
                    if (context instanceof DanhSachLopSinhVienActivity) {
                        ((DanhSachLopSinhVienActivity) context).setThongBaoVisibility(true);
                    }
                } else {
                    // Ẩn thông báo
                    if (context instanceof DanhSachLopSinhVienActivity) {
                        ((DanhSachLopSinhVienActivity) context).setThongBaoVisibility(false);
                    }
                }
                if (sinhVienList == null || sinhVienList.isEmpty()) {
                    // Hiển thị thông báo không tìm thấy sinh viên
                    if (context instanceof QuanLySinhVienActivity) {
                        ((QuanLySinhVienActivity) context).setThongBaoVisibility(true);
                    }
                } else {
                    // Ẩn thông báo
                    if (context instanceof QuanLySinhVienActivity) {
                        ((QuanLySinhVienActivity) context).setThongBaoVisibility(false);
                    }
                }
                notifyDataSetChanged();
            }
        };
    }

    public class SinhVienViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtMSSV, txtNgaySinh, txtLopHanhChinh;
        CardView itemLayout;

        public SinhVienViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTen);
            txtMSSV = itemView.findViewById(R.id.txtMSSV);
            txtNgaySinh = itemView.findViewById(R.id.txtNgaySinh);
            txtLopHanhChinh = itemView.findViewById(R.id.txtLopHanhChinh);
            itemLayout = itemView.findViewById(R.id.layoutItem);

        }
    }

    public void release() {
        context = null;

    }
}
