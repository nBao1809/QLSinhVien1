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

import com.example.qlsinhvien.Models.LoaiDiem;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;



import java.util.ArrayList;
import java.util.List;

public class LoaiDiemAdapter extends RecyclerView.Adapter<LoaiDiemAdapter.ClassViewHolder> implements Filterable {


    private List<LoaiDiem> loaiDiemList, loaiDiemListOld;


    public LoaiDiemAdapter() {

    }

    public void setData(List<LoaiDiem> loaiDiemList) {
        if(loaiDiemList==null)
            return;
        this.loaiDiemList = loaiDiemList;
        this.loaiDiemListOld = loaiDiemList;
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
        LoaiDiem loaiDiem = loaiDiemList.get(position);
        if (loaiDiem == null)
            return;
        //holder.txtNganh.setText(diem.getMaMonHoc())
    }

    @Override
    public int getItemCount() {
        if (loaiDiemList != null)
            return loaiDiemList.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString = StringUtility.removeMark(constraint.toString().toLowerCase());
                if (searchString.isEmpty()) {
                    loaiDiemList = loaiDiemListOld;
                } else {
                    List<LoaiDiem> list = new ArrayList<>();
                    for (LoaiDiem loaiDiem : loaiDiemListOld) {
                        String tenLoaiDiem =
                                StringUtility.removeMark(loaiDiem.getTenLoaiDiem().toLowerCase());

                        if (tenLoaiDiem.contains(searchString)) {
                            list.add(loaiDiem);
                        }
                    }
                    loaiDiemList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = loaiDiemList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                loaiDiemListOld = (List<LoaiDiem>) results.values;
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
