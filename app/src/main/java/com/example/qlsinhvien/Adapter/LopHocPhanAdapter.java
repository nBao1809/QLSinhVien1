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
import com.example.qlsinhvien.Models.LopHocPhan;
import com.example.qlsinhvien.Models.MonHoc;
import com.example.qlsinhvien.Models.Nganh;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;
import com.example.qlsinhvien.dao.GiangVienManager;
import com.example.qlsinhvien.dao.MonHocManager;
import com.example.qlsinhvien.dao.NganhManager;

import java.util.ArrayList;
import java.util.List;

public class LopHocPhanAdapter extends RecyclerView.Adapter<LopHocPhanAdapter.ClassViewHolder> implements Filterable {

    private MonHocManager monHocManager;
    private GiangVienManager giangVienManager;
    private NganhManager nganhManager;
    private Context context;
    private List<LopHocPhan> listHocPhan, listHocPhanOld;


    public LopHocPhanAdapter(Context context) {
        this.context = context;
        monHocManager = new MonHocManager(context);
        giangVienManager = new GiangVienManager(context);
        nganhManager=new NganhManager(context);
    }

    public void setData(List<LopHocPhan> listHocPhan) {
        this.listHocPhan = listHocPhan;
        this.listHocPhanOld = listHocPhan;
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
        LopHocPhan lopHocPhan = listHocPhan.get(position);
        if (lopHocPhan == null)
            return;
        MonHoc monHoc =monHocManager.getMonHoc(lopHocPhan.getMaMonHoc());
        Nganh nganh =nganhManager.getNganh(monHoc.getMaNganh());
        GiangVien giangVien=giangVienManager.getGiangVien(lopHocPhan.getMaGiangVienPhuTrach());

        holder.txtNganh.setText(nganh.getTenNganh());
        holder.txtMonHoc.setText(monHoc.getTenMonHoc());
        holder.txtGVPhuTrach.setText(giangVien.getHoTen());
        holder.txtLop.setText(lopHocPhan.getTenLop());
    }

    @Override
    public int getItemCount() {
        if (listHocPhan != null)
            return listHocPhan.size();
        return 0;
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
                        String tenMonHoc = StringUtility.removeMark(lopHocPhan.getTenLop().toLowerCase());
                        String maLopHoc =
                                StringUtility.removeMark(lopHocPhan.getMaLop().toLowerCase());
                        if (tenMonHoc.contains(searchString)) {
                            list.add(lopHocPhan);
                        }
                        if (maLopHoc.contains(searchString)) {
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


    public class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView txtNganh, txtMonHoc, txtGVPhuTrach, txtLop;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNganh = itemView.findViewById(R.id.txtNganh);
            txtMonHoc = itemView.findViewById(R.id.txtMonHoc);
            txtGVPhuTrach = itemView.findViewById(R.id.txtGVPhuTrach);
            txtLop = itemView.findViewById(R.id.txtLop);
        }
    }

}
