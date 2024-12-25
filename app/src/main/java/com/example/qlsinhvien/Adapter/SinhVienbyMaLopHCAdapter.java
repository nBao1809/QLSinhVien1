package com.example.qlsinhvien.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qlsinhvien.Models.LopHanhChinh;
import com.example.qlsinhvien.Models.SinhVien;
import com.example.qlsinhvien.R;

import java.util.ArrayList;
import java.util.List;

public class SinhVienbyMaLopHCAdapter extends ArrayAdapter<SinhVien>
{

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsinhvienselected,parent,false);
        TextView txtMSSVselected = convertView.findViewById(R.id.txtMSSVselected);
        TextView txtTenSinhVienselected =convertView.findViewById(R.id.txtTenSinhVienselected);

        SinhVien sinhVien= this.getItem(position);
        {
            if(sinhVien!=null){
                txtMSSVselected.setText(sinhVien.getMssv());
                txtTenSinhVienselected.setText(sinhVien.getHoTen());
            }
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemchonsinhvien,parent,false);
        TextView txtMSSV = convertView.findViewById(R.id.txtMSSV);
        TextView txtTenSinhVien=convertView.findViewById(R.id.txtTenSinhVien);

        SinhVien sinhVien= this.getItem(position);
        {
            if(sinhVien!=null){
                txtMSSV.setText(sinhVien.getMssv());
                txtTenSinhVien.setText(sinhVien.getHoTen());
            }
        }
        return convertView;
    }

    public SinhVienbyMaLopHCAdapter(@NonNull Context context, int resource, @NonNull List<SinhVien> objects) {
        super(context, resource, objects);
    }
}
