package com.example.qlsinhvien.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlsinhvien.Activities.QuanLyDiemvaInforSinhVien;
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
    QuanLyDiemvaInforSinhVien quanLyDiemvaInforSinhVien;


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
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test69", "buttonclick");
                onClickDelete(diem);
            }
        });

    }

    public void onClickDelete(Diem diem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xóa điểm?");
        builder.setMessage("Bạn có chắc muốn xóa điểm hay không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int ketqua = diemManager.deleteDiem(diem.getMaDiem());

                if (ketqua > 0) {
                    listDiem.remove(diem);
                    if (listDiem.isEmpty()) {
                        if (quanLyDiemvaInforSinhVien != null) {
                            quanLyDiemvaInforSinhVien.setThongBaoVisibility(true);
                        }
                    }
                    notifyDataSetChanged();
                    View view = LayoutInflater.from(context).inflate(R.layout.successdialog, null);
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setView(view);
                    Button successDone = view.findViewById(R.id.successDone);
                    AlertDialog alertDialog = builder1.create();
                    if (alertDialog.getWindow() != null) {
                        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custome_success);
                        alertDialog.getWindow().setGravity(Gravity.CENTER);
                    }
                    alertDialog.show();
                    successDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                } else {
                    View view = LayoutInflater.from(context).inflate(R.layout.faildialog, null);
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setView(view);
                    Button failDone = view.findViewById(R.id.failDone);
                    AlertDialog alertDialog = builder1.create();
                    if (alertDialog.getWindow() != null) {
                        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custome_success);
                        alertDialog.getWindow().setGravity(Gravity.CENTER);
                    }
                    alertDialog.show();
                    failDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                }
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

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
        ImageButton btnDelete;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDiem = itemView.findViewById(R.id.txtDiem);
            txtLoaiDiem = itemView.findViewById(R.id.txtLoaiDiem);
            txtTrongSo = itemView.findViewById(R.id.txtTrongSo);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

}
