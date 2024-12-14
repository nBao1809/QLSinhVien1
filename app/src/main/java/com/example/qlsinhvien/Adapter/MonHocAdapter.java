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

import com.example.qlsinhvien.Models.MonHoc;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;



import java.util.ArrayList;
import java.util.List;

public class MonHocAdapter extends RecyclerView.Adapter<MonHocAdapter.ClassViewHolder> implements Filterable {

    private Context context;
    private List<MonHoc> monHocList, monHocListOld;


    public MonHocAdapter() {

    }

    public void setData(List<MonHoc> monHocList) {
        if(monHocList==null)
            return;
        this.monHocList = monHocList;
        this.monHocListOld = monHocList;
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
        MonHoc monHoc = monHocList.get(position);
        if (monHoc == null)
            return;
        //holder.txtNganh.setText(diem.getMaMonHoc())
    }

    @Override
    public int getItemCount() {
        if (monHocList != null)
            return monHocList.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString = StringUtility.removeMark(constraint.toString().toLowerCase());
                if (searchString.isEmpty()) {
                    monHocList = monHocListOld;
                } else {
                    List<MonHoc> list = new ArrayList<>();
                    for (MonHoc monHoc : monHocListOld) {
                        String maMonHoc =
                                StringUtility.removeMark(monHoc.getMaMonHoc().toLowerCase());
                        String tenMonHoc =
                                StringUtility.removeMark(monHoc.getTenMonHoc().toLowerCase());
                        if (tenMonHoc.contains(searchString)) {
                            list.add(monHoc);
                        }
                        if (maMonHoc.contains(searchString)) {
                            list.add(monHoc);
                        }
                    }
                    monHocList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = monHocList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                monHocListOld = (List<MonHoc>) results.values;
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
