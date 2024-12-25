package com.example.qlsinhvien.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qlsinhvien.Models.LoaiDiem;
import com.example.qlsinhvien.R;

import java.util.List;

public class LoaiDiemAdapter extends ArrayAdapter<LoaiDiem> {


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsinhvienselected,parent,false);
        TextView txtLoaiDiemSelected = convertView.findViewById(R.id.txtMSSVselected);
        TextView txtTrongSoSelected =convertView.findViewById(R.id.txtTenSinhVienselected);

        LoaiDiem loaiDiem= this.getItem(position);
        {
            if(loaiDiem!=null){
                txtLoaiDiemSelected.setText(loaiDiem.getTenLoaiDiem());
                txtTrongSoSelected.setText((String.valueOf(loaiDiem.getTrongSo())));
            }
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemchonsinhvien,parent,false);
        TextView txtLoaiDiem = convertView.findViewById(R.id.txtMSSV);
        TextView txtTrongSo =convertView.findViewById(R.id.txtTenSinhVien);

        LoaiDiem loaiDiem= this.getItem(position);
        {
            if(loaiDiem!=null){
                txtLoaiDiem.setText(loaiDiem.getTenLoaiDiem());
                txtTrongSo.setText(String.valueOf(loaiDiem.getTrongSo()));
            }
        }
        return convertView;
    }

    public LoaiDiemAdapter(@NonNull Context context, int resource, @NonNull List<LoaiDiem> objects) {
        super(context, resource, objects);
    }
}