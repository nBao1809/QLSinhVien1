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

import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;
import com.example.qlsinhvien.dao.UserManager;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class GiangVienAdapter extends ArrayAdapter<GiangVien> {
 
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.itemchongiangvien,parent,false);
        TextView txtChonGiangVien = convertView.findViewById(R.id.txtChonGiangVien);

        GiangVien giangVien= this.getItem(position);
        {
            if(giangVien!=null){
                txtChonGiangVien.setText(giangVien.getHoTen());
            }
        }
        return convertView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.itemgiangvienselected,parent,false);
        TextView txtSelected = convertView.findViewById(R.id.txtSelected);

        GiangVien giangVien= this.getItem(position);
        {
            if(giangVien!=null){
                txtSelected.setText(giangVien.getHoTen());
            }
        }
        return convertView;
    }

    public GiangVienAdapter(@NonNull Context context, int resource, @NonNull List<GiangVien> objects) {
        super(context, resource, objects);
    }
}
