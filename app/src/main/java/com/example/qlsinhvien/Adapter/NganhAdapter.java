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
import com.example.qlsinhvien.Models.HocKy;

import com.example.qlsinhvien.Models.Nganh;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;



import java.util.ArrayList;
import java.util.List;
public class NganhAdapter extends ArrayAdapter<Nganh> {


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.itemgiangvienselected,parent,false);
        TextView txtSelected = convertView.findViewById(R.id.txtSelected);

        Nganh nganh= this.getItem(position);
        {
            if(nganh!=null){
                txtSelected.setText(nganh.getTenNganh());
            }
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.itemchongiangvien,parent,false);
        TextView txtChonGiangVien = convertView.findViewById(R.id.txtChonGiangVien);

        Nganh nganh= this.getItem(position);
        {
            if(nganh!=null){
                txtChonGiangVien.setText(nganh.getTenNganh());

            }
        }
        return convertView;
    }

    public NganhAdapter(@NonNull Context context, int resource, @NonNull List<Nganh> objects) {
        super(context, resource, objects);
    }
}