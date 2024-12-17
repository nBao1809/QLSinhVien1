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
import androidx.recyclerview.widget.RecyclerView;


import com.example.qlsinhvien.Models.GiangVien;

import com.example.qlsinhvien.Models.LopHanhChinh;
import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;
import com.example.qlsinhvien.dao.UserManager;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LopHanhChinhAdapter extends ArrayAdapter<LopHanhChinh> {

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.itemchongiangvien,parent,false);
        TextView txtChonGiangVien = convertView.findViewById(R.id.txtChonGiangVien);

        LopHanhChinh lopHanhChinh= this.getItem(position);
        {
            if(lopHanhChinh!=null){
                txtChonGiangVien.setText(lopHanhChinh.getTenLopHanhChinh());
            }
        }
        return convertView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.itemgiangvienselected,parent,false);
        TextView txtSelected = convertView.findViewById(R.id.txtSelected);

        LopHanhChinh lopHanhChinh= this.getItem(position);
        {
            if(lopHanhChinh!=null){
                txtSelected.setText(lopHanhChinh.getTenLopHanhChinh());
            }
        }
        return convertView;
    }

    public LopHanhChinhAdapter(@NonNull Context context, int resource, @NonNull List<LopHanhChinh> objects) {
        super(context, resource, objects);
    }
}
