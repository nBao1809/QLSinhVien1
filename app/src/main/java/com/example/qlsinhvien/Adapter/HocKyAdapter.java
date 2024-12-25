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

import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;


import java.util.ArrayList;
import java.util.List;

public class HocKyAdapter extends RecyclerView.Adapter<HocKyAdapter.ClassViewHolder> implements Filterable {


    private List<HocKy> hocKyList, hocKyListOld;


    public HocKyAdapter() {


    }

    public void setData(List<HocKy> hocKyList) {
        if (hocKyList == null)
            return;
        this.hocKyList = hocKyList;
        this.hocKyListOld = hocKyList;
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
        HocKy hocKy = hocKyList.get(position);
        if (hocKyList == null)
            return;
        //holder.txtNganh.setText(diem.getMaMonHoc())
    }

    @Override
    public int getItemCount() {
        if (hocKyList != null)
            return hocKyList.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString = StringUtility.removeMark(constraint.toString().toLowerCase());
                if (searchString.isEmpty()) {
                    hocKyList = hocKyListOld;
                } else {
                    List<HocKy> list = new ArrayList<>();
                    for (HocKy hocKy : hocKyListOld) {
                        String tenHocKy =
                                StringUtility.removeMark(hocKy.getTenHocKy().toLowerCase());

                        if (tenHocKy.contains(searchString)) {
                            list.add(hocKy);
                        }

                    }
                    hocKyList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = hocKyList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                hocKyListOld = (List<HocKy>) results.values;
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
