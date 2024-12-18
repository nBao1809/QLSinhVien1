package com.example.qlsinhvien.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlsinhvien.Models.Diem;
import com.example.qlsinhvien.Models.HocKy;
import com.example.qlsinhvien.Models.LoaiDiem;
import com.example.qlsinhvien.Models.LopHocPhan;
import com.example.qlsinhvien.Models.LopSinhVien;
import com.example.qlsinhvien.Models.SinhVien;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;
import com.example.qlsinhvien.dao.DiemManager;
import com.example.qlsinhvien.dao.HocKyManager;
import com.example.qlsinhvien.dao.LoaiDiemManager;
import com.example.qlsinhvien.dao.LopHocPhanManager;
import com.example.qlsinhvien.dao.LopSinhVienManager;
import com.example.qlsinhvien.dao.SinhVienManager;

import java.util.ArrayList;
import java.util.List;

public class LopSinhVienAdapter extends RecyclerView.Adapter<LopSinhVienAdapter.ClassViewHolder> {
    //    public LopSinhVien(String maLopSinhVien, String maLop, String mssv, String maHocKy) {
//        this.maLopSinhVien = maLopSinhVien;
//        this.maLop = maLop;
//        this.mssv = mssv;
//        this.maHocKy = maHocKy;
//    }
    private LopHocPhanManager lopHocPhanManager;
    private LopSinhVienManager lopSinhVienManager;
    private SinhVienManager sinhVienManager;
    private DiemManager diemManager;
    private LoaiDiemManager loaiDiemManager;
    private HocKyManager hocKyManager;
    private Context context;

    private List<Diem> listDiem;


    public LopSinhVienAdapter(Context context) {
        this.context = context;
        lopHocPhanManager = new LopHocPhanManager(context);
        sinhVienManager = new SinhVienManager(context);
        diemManager = new DiemManager(context);
        loaiDiemManager = new LoaiDiemManager(context);
        hocKyManager = new HocKyManager(context);
        lopSinhVienManager = new LopSinhVienManager(context);
    }


    public void setData(List<Diem> listDiem) {
        this.listDiem = listDiem;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdiem_lopsinhvien, parent, false);
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        Diem diem = listDiem.get(position);

        if (diem == null)
            return;
        LoaiDiem loaiDiem = loaiDiemManager.getLoaiDiem(diem.getMaLoaiDiem());
        holder.txtLoaiDiem.setText(loaiDiem.getTenLoaiDiem());
        holder.txtDiem.setText(String.valueOf(diem.getDiemSo()));
        holder.txtTrongSo.setText(String.valueOf(loaiDiem.getTrongSo()));

    }

    @Override
    public int getItemCount() {
        if (listDiem != null) {
            return listDiem.size();
        }
        return 0;
    }


    public class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView txtLoaiDiem, txtDiem, txtTrongSo;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDiem = itemView.findViewById(R.id.txtDiem);
            txtLoaiDiem = itemView.findViewById(R.id.txtLoaiDiem);
            txtTrongSo = itemView.findViewById(R.id.txtTrongSo);
        }
    }

}
