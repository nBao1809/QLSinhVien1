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

import com.example.qlsinhvien.Models.LopHanhChinh;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;



import java.util.ArrayList;
import java.util.List;

public class LopHanhChinhAdapter extends RecyclerView.Adapter<LopHanhChinhAdapter.ClassViewHolder> implements Filterable {

    private Context context;
    private List<LopHanhChinh> lopHanhChinhList, lopHanhChinhListOld;


    public LopHanhChinhAdapter(Context context) {
        this.context = context;

    }

    public void setData(List<LopHanhChinh> lopHanhChinhList) {
        this.lopHanhChinhList = lopHanhChinhList;
        this.lopHanhChinhListOld = lopHanhChinhList;
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
        LopHanhChinh lopHanhChinh = lopHanhChinhList.get(position);
        if (lopHanhChinh == null)
            return;
        //holder.txtNganh.setText(diem.getMaMonHoc())
    }

    @Override
    public int getItemCount() {
        if (lopHanhChinhList != null)
            return lopHanhChinhList.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString = StringUtility.removeMark(constraint.toString().toLowerCase());
                if (searchString.isEmpty()) {
                    lopHanhChinhList = lopHanhChinhListOld;
                } else {
                    List<LopHanhChinh> list = new ArrayList<>();
                    for (LopHanhChinh lopHanhChinh : lopHanhChinhListOld) {
                        String maLopHanhChinh =
                                StringUtility.removeMark(lopHanhChinh.getMaLopHanhChinh().toLowerCase());
                        String tenLopHanhChinh =
                                StringUtility.removeMark(lopHanhChinh.getMaLopHanhChinh().toLowerCase());
                        if (maLopHanhChinh.contains(searchString)) {
                            list.add(lopHanhChinh);
                        }
                        if (tenLopHanhChinh.contains(searchString)) {
                            list.add(lopHanhChinh);
                        }
                    }
                    lopHanhChinhListOld = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = lopHanhChinhList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                lopHanhChinhListOld = (List<LopHanhChinh>) results.values;
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
