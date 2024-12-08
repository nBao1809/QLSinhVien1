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

import com.example.qlsinhvien.Models.Nganh;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;



import java.util.ArrayList;
import java.util.List;

public class NganhAdapter extends RecyclerView.Adapter<NganhAdapter.ClassViewHolder> implements Filterable {

    private Context context;
    private List<Nganh> nganhList, nganhListOld;


    public NganhAdapter(Context context) {
        this.context = context;

    }

    public void setData(List<Nganh> nganhList) {
        this.nganhList = nganhList;
        this.nganhListOld = nganhList;
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
        Nganh nganh = nganhList.get(position);
        if (nganh == null)
            return;
        //holder.txtNganh.setText(diem.getMaMonHoc())
    }

    @Override
    public int getItemCount() {
        if (nganhList != null)
            return nganhList.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString = StringUtility.removeMark(constraint.toString().toLowerCase());
                if (searchString.isEmpty()) {
                    nganhList = nganhListOld;
                } else {
                    List<Nganh> list = new ArrayList<>();
                    for (Nganh nganh : nganhListOld) {
                        String tenNganh =
                                StringUtility.removeMark(nganh.getTenNganh().toLowerCase());
                        String maNganh =
                                StringUtility.removeMark(nganh.getMaNganh().toLowerCase());
                        if (tenNganh.contains(searchString)) {
                            list.add(nganh);
                        }
                        if (maNganh.contains(searchString)) {
                            list.add(nganh);
                        }
                    }
                    nganhListOld = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = nganhList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                nganhListOld = (List<Nganh>) results.values;
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
