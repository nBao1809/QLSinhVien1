package com.example.qlsinhvien.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlsinhvien.Models.GiangVien;
import com.example.qlsinhvien.Models.MonHoc;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;



import java.util.ArrayList;
import java.util.List;

public class MonHocAdapter extends ArrayAdapter<MonHoc> {


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.itemgiangvienselected,parent,false);
        TextView txtSelected = convertView.findViewById(R.id.txtSelected);

        MonHoc monHoc= this.getItem(position);
        {
            if(monHoc!=null){
                txtSelected.setText(monHoc.getTenMonHoc());
            }
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.itemchongiangvien,parent,false);
        TextView txtChonMonHoc = convertView.findViewById(R.id.txtChonGiangVien);

        MonHoc monHoc= this.getItem(position);
        {
            if(monHoc!=null){
                txtChonMonHoc.setText(monHoc.getTenMonHoc());
            }
        }
        return convertView;
    }

    public MonHocAdapter(@NonNull Context context, int resource, @NonNull List<MonHoc> objects) {
        super(context, resource, objects);
    }
}
