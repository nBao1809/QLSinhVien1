package com.example.qlsinhvien.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.qlsinhvien.Activities.DanhSachLopSinhVienActivity;
import com.example.qlsinhvien.Activities.DiemSinhVien;
import com.example.qlsinhvien.Activities.QuanLyLopHocActivity;
import com.example.qlsinhvien.Activities.QuanLySinhVienActivity;
import com.example.qlsinhvien.Fragments.HomeFragment;
import com.example.qlsinhvien.Models.Diem;
import com.example.qlsinhvien.Models.GiangVien;
import com.example.qlsinhvien.Models.LoaiDiem;
import com.example.qlsinhvien.Models.LopHocPhan;
import com.example.qlsinhvien.Models.LopSinhVien;
import com.example.qlsinhvien.Models.SinhVien;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;
import com.example.qlsinhvien.dao.GiangVienManager;
import com.example.qlsinhvien.dao.LopHocPhanManager;
import com.example.qlsinhvien.dao.LopSinhVienManager;
import com.example.qlsinhvien.dao.MonHocManager;
import com.example.qlsinhvien.dao.NganhManager;
import com.example.qlsinhvien.dao.SinhVienManager;

import java.util.ArrayList;
import java.util.List;

public class SinhVienMonHocAdapter extends RecyclerView.Adapter<SinhVienMonHocAdapter.ClassViewHolder> implements Filterable {
    private LopHocPhanManager lopHocPhanManager;
    private MonHocManager monHocManager;
    private GiangVienManager giangVienManager;
    private NganhManager nganhManager;
    private Context context;
    private List<LopHocPhan> listHocPhan, listHocPhanOld;
    private Boolean bool;

    private SinhVien sinhVien;

    public SinhVienMonHocAdapter(Context context) {
        this.context = context;
        monHocManager = new MonHocManager(context);
        giangVienManager = new GiangVienManager(context);
        lopHocPhanManager = new LopHocPhanManager(context);
        nganhManager = new NganhManager(context);
    }

    public void setData(List<LopHocPhan> listHocPhan, SinhVien sinhVien,Boolean bool) {
        this.listHocPhan = listHocPhan;
        this.listHocPhanOld = listHocPhan;
        this.sinhVien = sinhVien;
        this.bool=bool;
        notifyDataSetChanged();
    }

    public void setDataGV(List<LopHocPhan> listHocPhan, Boolean bool) {
        this.listHocPhan = listHocPhan;
        this.listHocPhanOld = listHocPhan;
        this.bool = bool;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (listHocPhan != null) {
            return listHocPhan.size();
        }
        return 0;
    }

    @NonNull
    @Override
    public SinhVienMonHocAdapter.ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlophocphan, parent, false);
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SinhVienMonHocAdapter.ClassViewHolder holder, int position) {
        LopHocPhan lopHocPhan = listHocPhan.get(position);
        if (lopHocPhan == null)
            return;
        if (!bool) {
            holder.txtNganh.setText(nganhManager.getNganh(
                    monHocManager.getMonHoc(lopHocPhan.getMaMonHoc()).getMaNganh()).getTenNganh());
            holder.txtMonHoc.setText(monHocManager.getMonHoc(lopHocPhan.getMaMonHoc()).getTenMonHoc());
            GiangVien giangVien = giangVienManager.getGiangVien(lopHocPhan.getMaGiangVienPhuTrach());
            if (giangVien != null) {
                holder.txtGVPhuTrach.setText(giangVien.getHoTen());
            } else {
                holder.txtGVPhuTrach.setText("Giảng viên không xác định");
            }
            holder.txtLop.setText(lopHocPhan.getTenLop());
            holder.layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DiemSinhVien.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("lopHocPhan", lopHocPhan);
                    bundle.putSerializable("sinhVien", sinhVien);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        } else {
            holder.txtNganh.setText(nganhManager.getNganh(
                    monHocManager.getMonHoc(lopHocPhan.getMaMonHoc()).getMaNganh()).getTenNganh());
            holder.txtMonHoc.setText(monHocManager.getMonHoc(lopHocPhan.getMaMonHoc()).getTenMonHoc());
            GiangVien giangVien = giangVienManager.getGiangVien(lopHocPhan.getMaGiangVienPhuTrach());
            if (giangVien != null) {
                holder.txtGVPhuTrach.setText(giangVien.getHoTen());
            } else {
                holder.txtGVPhuTrach.setText("Giảng viên không xác định");
            }
            holder.txtLop.setText(lopHocPhan.getTenLop());
            holder.layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickGoToSinhVien(lopHocPhan);
                }
            });
        }
    }

    private void onClickGoToSinhVien(LopHocPhan lopHocPhan) {
        Intent intent = new Intent(context, DanhSachLopSinhVienActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("lopHocPhan", lopHocPhan);
        bundle.putSerializable("3",3);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }

    public class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView txtNganh, txtMonHoc, txtGVPhuTrach, txtLop;
        CardView layoutItem;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layoutItem);
            txtNganh = itemView.findViewById(R.id.txtNganh);
            txtMonHoc = itemView.findViewById(R.id.txtMonHoc);
            txtGVPhuTrach = itemView.findViewById(R.id.txtGVPhuTrach);
            txtLop = itemView.findViewById(R.id.txtLop);
        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString = StringUtility.removeMark(constraint.toString().toLowerCase());
                if (searchString.isEmpty()) {
                    listHocPhan = listHocPhanOld;
                } else {
                    List<LopHocPhan> list = new ArrayList<>();
                    for (LopHocPhan lopHocPhan : listHocPhanOld) {
                        String tenLop = StringUtility.removeMark(lopHocPhan.getTenLop().toLowerCase());
                        String tenMonHoc =
                                StringUtility.removeMark(monHocManager.getMonHoc(lopHocPhan.getMaMonHoc()).getTenMonHoc().toLowerCase());
                        if (tenMonHoc.startsWith(searchString) || tenMonHoc.contains(searchString) || tenLop.startsWith(searchString) || tenLop.contains(searchString)) {
                            list.add(lopHocPhan);
                        }

                    }
                    listHocPhan = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listHocPhan;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listHocPhan = (List<LopHocPhan>) results.values;
                notifyDataSetChanged();
            }

        };
    }

}
