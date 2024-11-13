package com.example.qlsinhvien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlsinhvien.fragment.HomeFragment;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {
    private Context context;
    private List<LopHocPhan> listHocPhan;

    public ClassAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<LopHocPhan> listHocPhan) {
        this.listHocPhan = listHocPhan;
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
     LopHocPhan lopHocPhan = listHocPhan.get(position);
     if(lopHocPhan == null)
         return;
     holder.txtNganh.setText(lopHocPhan.getTenNganh());
     holder.txtMonHoc.setText(lopHocPhan.getTenMonHoc());
     holder.txtGVPhuTrach.setText(lopHocPhan.getTenGiaoVienPhuTrach());
     holder.txtLop.setText(lopHocPhan.getTenLop());
    }

    @Override
    public int getItemCount() {
        if (listHocPhan != null)
            return listHocPhan.size();
        return 0;
    }

    public class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView txtNganh, txtMonHoc, txtGVPhuTrach,txtLop;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNganh = itemView.findViewById(R.id.txtNganh);
            txtMonHoc = itemView.findViewById(R.id.txtMonHoc);
            txtGVPhuTrach = itemView.findViewById(R.id.txtGVPhuTrach);
            txtLop=itemView.findViewById(R.id.txtLop);
        }
    }
}
