package com.example.qlsinhvien.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qlsinhvien.Models.Role;
import com.example.qlsinhvien.R;

import java.util.List;

public class RoleAdapter extends ArrayAdapter<Role> {

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.itemchongiangvien,parent,false);
        TextView txtChonRole = convertView.findViewById(R.id.txtChonGiangVien);

        Role role= this.getItem(position);
        {
            if(role!=null){
                txtChonRole.setText(role.getTenRole());
            }
        }
        return convertView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.itemgiangvienselected,parent,false);
        TextView txtSelected = convertView.findViewById(R.id.txtSelected);

        Role Role= this.getItem(position);
        {
            if(Role!=null){
                txtSelected.setText(Role.getTenRole());
            }
        }
        return convertView;
    }


    public RoleAdapter(@NonNull Context context, int resource, @NonNull List<Role> objects) {
        super(context, resource, objects);
    }
}
