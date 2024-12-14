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

import com.example.qlsinhvien.Models.HocKy;
import com.example.qlsinhvien.Models.LopHocPhan;
import com.example.qlsinhvien.Models.LopSinhVien;
import com.example.qlsinhvien.Models.SinhVien;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;
import com.example.qlsinhvien.dao.HocKyManager;
import com.example.qlsinhvien.dao.LopHocPhanManager;
import com.example.qlsinhvien.dao.SinhVienManager;

import java.util.ArrayList;
import java.util.List;

public class LopSinhVienAdapter extends RecyclerView.Adapter<LopSinhVienAdapter.ClassViewHolder> implements Filterable {
    //    public LopSinhVien(String maLopSinhVien, String maLop, String mssv, String maHocKy) {
//        this.maLopSinhVien = maLopSinhVien;
//        this.maLop = maLop;
//        this.mssv = mssv;
//        this.maHocKy = maHocKy;
//    }
    private LopHocPhanManager lopHocPhanManager;
    private SinhVienManager sinhVienManager;
    private HocKyManager hocKyManager;
    private Context context;
    private List<LopSinhVien> lopSinhVienList, lopSinhVienListOld;


    public LopSinhVienAdapter(Context context) {
        this.context = context;
        lopHocPhanManager = new LopHocPhanManager(context);
        sinhVienManager = new SinhVienManager(context);
        hocKyManager = new HocKyManager(context);
    }

    public void setData(List<LopSinhVien> lopSinhVienList) {
        if(lopSinhVienList==null)
            return;
        this.lopSinhVienList = lopSinhVienList;
        this.lopSinhVienListOld = lopSinhVienList;
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
        LopSinhVien lopSinhVien = lopSinhVienList.get(position);
        if (lopSinhVien == null)
            return;
//        holder.txtNganh.setText(lopHocPhan.getMaMonHoc());
//        holder.txtMonHoc.setText(lopHocPhan.getTenLop());
//        holder.txtGVPhuTrach.setText(lopHocPhan.getMaGiangVienPhuTrach());
//        holder.txtLop.setText(lopHocPhan.getTenLop());
    }

    @Override
    public int getItemCount() {
        if (lopSinhVienList != null)
            return lopSinhVienList.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString = StringUtility.removeMark(constraint.toString().toLowerCase());
                if (searchString.isEmpty()) {
                    lopSinhVienList = lopSinhVienListOld;
                } else {
                    List<LopSinhVien> list = new ArrayList<>();
                    for (LopSinhVien lopSinhVien : lopSinhVienListOld) {
                        String tenMonHoc =
                                StringUtility.removeMark(lopSinhVien.getMaLopSinhVien().toLowerCase());
                        String maLopHoc =
                                StringUtility.removeMark(lopSinhVien.getMaLop().toLowerCase());
                        if (tenMonHoc.contains(searchString)) {
                            list.add(lopSinhVien);
                        }
                        if (maLopHoc.contains(searchString)) {
                            list.add(lopSinhVien);
                        }
                    }
                    lopSinhVienList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = lopSinhVienList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                lopSinhVienList = (List<LopSinhVien>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView txtNganh, txtMonHoc, txtGVPhuTrach, txtLop;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
//            txtNganh = itemView.findViewById(R.id.txtNganh);
//            txtMonHoc = itemView.findViewById(R.id.txtMonHoc);
//            txtGVPhuTrach = itemView.findViewById(R.id.txtGVPhuTrach);
//            txtLop = itemView.findViewById(R.id.txtLop);
        }
    }

}
