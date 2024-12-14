package com.example.qlsinhvien.Adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlsinhvien.Activities.DanhSachLopSinhVienActivity;
import com.example.qlsinhvien.Activities.InteractActivity;
import com.example.qlsinhvien.Activities.QuanLyLopHocActivity;
import com.example.qlsinhvien.Activities.QuanLySinhVienActivity;
import com.example.qlsinhvien.Fragments.HomeFragment;
import com.example.qlsinhvien.Models.GiangVien;
import com.example.qlsinhvien.Models.LopHocPhan;
import com.example.qlsinhvien.Models.LopSinhVien;
import com.example.qlsinhvien.Models.MonHoc;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;
import com.example.qlsinhvien.dao.GiangVienManager;
import com.example.qlsinhvien.dao.LopHocPhanManager;
import com.example.qlsinhvien.dao.MonHocManager;
import com.example.qlsinhvien.dao.NganhManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LopHocPhanAdapter extends RecyclerView.Adapter<LopHocPhanAdapter.ClassViewHolder> implements Filterable {

    private LopHocPhanManager lopHocPhanManager;
    private MonHocManager monHocManager;
    private GiangVienManager giangVienManager;
    private NganhManager nganhManager;
    private Context context;
    private List<LopHocPhan> listHocPhan, listHocPhanOld;
    private HomeFragment homeFragment;
    QuanLyLopHocActivity quanLyLopHocActivity;
    Double ngayBatDau = 0.0;
    Double ngayKetthuc = 0.0;
    String maMonHoc, maGiangVien, maLop, tenLop;
    Boolean bool;

    public LopHocPhanAdapter(Context context, HomeFragment homeFragment) {
        this.context = context;
        this.homeFragment = homeFragment;
        monHocManager = new MonHocManager(context);
        giangVienManager = new GiangVienManager(context);
        lopHocPhanManager = new LopHocPhanManager(context);
        nganhManager = new NganhManager(context);
    }

    public LopHocPhanAdapter(Context context, QuanLyLopHocActivity quanLyLopHocActivity) {
        this.context = context;
        this.quanLyLopHocActivity = quanLyLopHocActivity;
        lopHocPhanManager = new LopHocPhanManager(context);
        monHocManager = new MonHocManager(context);
        giangVienManager = new GiangVienManager(context);
        nganhManager = new NganhManager(context);
    }


    public void setData(List<LopHocPhan> listHocPhan, Boolean bool) {
        this.listHocPhan = listHocPhan;
        this.listHocPhanOld = listHocPhan;
        this.bool = bool;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (!bool) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlophocphan, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemquanlylophoc, parent, false);
        }
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        LopHocPhan lopHocPhan = listHocPhan.get(position);
        if (lopHocPhan == null)
            return;
        if (!bool) {
            holder.txtNganh.setText(nganhManager.getNganh(
                    monHocManager.getMonHoc(lopHocPhan.getMaMonHoc()).getMaNganh()).getTenNganh());
            holder.txtMonHoc.setText(monHocManager.getMonHoc(lopHocPhan.getMaMonHoc()).getTenMonHoc());
            holder.txtGVPhuTrach.setText(giangVienManager.getGiangVien(lopHocPhan.getMaGiangVienPhuTrach()).getHoTen());
            holder.txtLop.setText(lopHocPhan.getTenLop());
            holder.layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickGoToSinhVien(lopHocPhan, bool);
                }
            });
        } else {
            holder.txtNganh.setText(nganhManager.getNganh(
                    monHocManager.getMonHoc(lopHocPhan.getMaMonHoc()).getMaNganh()).getTenNganh());
            holder.txtMonHoc.setText(monHocManager.getMonHoc(lopHocPhan.getMaMonHoc()).getTenMonHoc());
            holder.txtGVPhuTrach.setText(giangVienManager.getGiangVien(lopHocPhan.getMaGiangVienPhuTrach()).getHoTen());
            holder.txtLop.setText(lopHocPhan.getTenLop());
            holder.layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickGoToSinhVien(lopHocPhan, bool);
                }
            });
            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickEdit(lopHocPhan);
                }
            });
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickDelete(lopHocPhan);

                }
            });
        }
    }

    private void onClickEdit(LopHocPhan lopHocPhan) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.itemchinhsualophocphan, null);
        builder.setView(view);
        EditText edtMaLop, edtTenLop, edtNgayBatDau, edtNgayKetThuc;
        ImageButton ibtnStartday, ibtnEndDay;
        Spinner spinnerGiangVien, spinnerMonHoc;
        Button btnHuy, btnLuu;
        edtNgayBatDau = view.findViewById(R.id.edtNgayBatDau);
        edtNgayKetThuc = view.findViewById(R.id.edtNgayKetThuc);
        edtMaLop = view.findViewById(R.id.edtMaLop);
        edtTenLop = view.findViewById(R.id.edtTenLop);
        ibtnStartday = view.findViewById(R.id.ibtnStartday);
        ibtnEndDay = view.findViewById(R.id.ibtnEndDay);
        spinnerGiangVien = view.findViewById(R.id.spinnerGiangVien);
        spinnerMonHoc = view.findViewById(R.id.spinnerMonHoc);
        btnLuu = view.findViewById(R.id.btnLuu);
        btnHuy = view.findViewById(R.id.btnHuy);
        AlertDialog alertDialog = builder.create();
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_themlophocphan);
        }
        alertDialog.show();
        edtMaLop.setText(lopHocPhan.getMaLop());
        edtMaLop.setEnabled(false);
        edtMaLop.setBackgroundColor(Color.TRANSPARENT);
        edtTenLop.setText(lopHocPhan.getTenLop());
        String sNgayBatDau = dateFormat(lopHocPhan.getNgayBatDau());
        edtNgayBatDau.setText(sNgayBatDau);
        ngayBatDau = lopHocPhan.getNgayBatDau();
        ngayKetthuc = lopHocPhan.getNgayKetThuc();
        String sNgayKetThuc = dateFormat(lopHocPhan.getNgayKetThuc());
        edtNgayKetThuc.setText(sNgayKetThuc);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


        ibtnStartday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDatePickerDialog(edtNgayBatDau, true);

            }
        });
        ibtnEndDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(edtNgayKetThuc, false);

            }
        });
        GiangVienAdapter adapter = new GiangVienAdapter(context, R.layout.itemgiangvienselected, giangVienManager.getAllGiangVien());

        spinnerGiangVien.setAdapter(adapter);
        maGiangVien = lopHocPhan.getMaGiangVienPhuTrach();

        spinnerMonHoc.setSelection(adapter.getPosition(giangVienManager.getGiangVien(lopHocPhan.getMaMonHoc())));


        spinnerGiangVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maGiangVien = adapter.getItem(position).getMaGiangVien();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        MonHocAdapter monHocAdapter = new MonHocAdapter(context, R.layout.itemgiangvienselected, monHocManager.getAllMonHoc());
        spinnerMonHoc.setAdapter(monHocAdapter);
        maMonHoc = lopHocPhan.getMaMonHoc();
        spinnerMonHoc.setSelection(monHocAdapter.getPosition(monHocManager.getMonHoc(lopHocPhan.getMaMonHoc())));


        spinnerMonHoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maMonHoc = monHocAdapter.getItem(position).getMaMonHoc();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maLop = lopHocPhan.getMaLop();
                tenLop = edtTenLop.getText().toString().trim();
                Log.d("test4", String.valueOf(maLop));
                Log.d("test4", String.valueOf(tenLop));
                Log.d("test4", sNgayBatDau);
                Log.d("test4", sNgayKetThuc);
                Log.d("test4", String.valueOf(ngayBatDau));
                Log.d("test4", String.valueOf(ngayKetthuc));
                Log.d("test4", String.valueOf(maMonHoc));
                Log.d("test4", String.valueOf(maGiangVien));
                if (maLop.isEmpty() || tenLop.isEmpty() || ngayBatDau == 0.0 || ngayKetthuc == 0.0 || maMonHoc == null || maGiangVien == null) {
                    Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    LopHocPhan lopHocPhan1 = new LopHocPhan(maLop, tenLop, ngayBatDau, ngayKetthuc, maMonHoc, maGiangVien);
                    int ketqua = lopHocPhanManager.updateLopHocPhan(lopHocPhan1);
                    if (ketqua > 0) {
                        for (int i = 0; i < listHocPhan.size(); i++) {
                            if (listHocPhan.get(i).getMaLop().equals(lopHocPhan.getMaLop())) {
                                listHocPhan.set(i, lopHocPhan1);
                                break;
                            }
                        }
                        notifyDataSetChanged();
                        View view = LayoutInflater.from(context).inflate(R.layout.successdialog, null);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setView(view);
                        Button successDone = view.findViewById(R.id.successDone);
                        TextView sucessBelow = view.findViewById(R.id.successBelow);
                        AlertDialog alertDialog = builder1.create();
                        if (alertDialog.getWindow() != null) {
                            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custome_success);
                            alertDialog.getWindow().setGravity(Gravity.CENTER);
                        }
                        alertDialog.show();
                        sucessBelow.setText("Bạn đã chỉnh sửa thành công");
                        successDone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });
                    } else {
                        View view = LayoutInflater.from(context).inflate(R.layout.faildialog, null);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setView(view);
                        Button failDone = view.findViewById(R.id.failDone);
                        TextView failBelow = view.findViewById(R.id.failBelow);
                        AlertDialog alertDialog = builder1.create();
                        if (alertDialog.getWindow() != null) {
                            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custome_success);
                            alertDialog.getWindow().setGravity(Gravity.CENTER);
                        }
                        alertDialog.show();
                        failBelow.setText("Chỉnh sửa thất bại");
                        failDone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });


                    }


                }

            }
        });
    }


    private void onClickDelete(LopHocPhan lopHocPhan) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xóa lớp " + lopHocPhan.getTenLop() + " ?");
        builder.setMessage("Bạn có chắc muốn xóa lớp " + lopHocPhan.getTenLop() + " không");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int ketqua = lopHocPhanManager.deleteLopHocPhan(lopHocPhan.getMaLop());
                listHocPhan.remove(lopHocPhan);
                if (listHocPhan.isEmpty()) {
                    if (homeFragment != null) {
                        homeFragment.setThongBaoVisibility(true);
                    }
                    if (quanLyLopHocActivity != null) {
                        quanLyLopHocActivity.setThongBaoVisibility(true);
                    }
                }
                notifyDataSetChanged();
                if (ketqua > 0) {
                    View view = LayoutInflater.from(context).inflate(R.layout.successdialog, null);
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setView(view);
                    Button successDone = view.findViewById(R.id.successDone);
                    AlertDialog alertDialog = builder1.create();
                    if (alertDialog.getWindow() != null) {
                        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custome_success);
                        alertDialog.getWindow().setGravity(Gravity.CENTER);
                    }
                    alertDialog.show();
                    successDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                } else {
                    View view = LayoutInflater.from(context).inflate(R.layout.faildialog, null);
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setView(view);
                    Button failDone = view.findViewById(R.id.failDone);
                    AlertDialog alertDialog = builder1.create();
                    if (alertDialog.getWindow() != null) {
                        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custome_success);
                        alertDialog.getWindow().setGravity(Gravity.CENTER);
                    }
                    alertDialog.show();
                    failDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                }
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void showDatePickerDialog(final EditText edtNgay, final Boolean isStartDay) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                edtNgay.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, dayOfMonth);

                        // Cập nhật ngày cho EditText
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        edtNgay.setText(dateFormat.format(selectedDate.getTime()));

                        if (isStartDay) {
                            Double ngayTam = Double.parseDouble(String.format(Locale.getDefault(), "%d.%02d%02d", year, month + 1, dayOfMonth));
                            // Nếu là ngày bắt đầu, lưu ngày bắt đầu
                            if (ngayTam > ngayKetthuc) {
                                Toast.makeText(context, "Ngày bắt đầu phải nhỏ hơn ngày kết thúc ", Toast.LENGTH_SHORT).show();
                                edtNgay.setText("");
                                edtNgay.setError("");
                            } else {
                                ngayBatDau = Double.parseDouble(String.format(Locale.getDefault(), "%d.%02d%02d", year, month + 1, dayOfMonth));
                                edtNgay.setError(null);
                            }
                        } else {
                            if (ngayBatDau == 0.0) {
                                Toast.makeText(context, "Bạn phải chọn ngày bắt đầu trước", Toast.LENGTH_SHORT).show();
                                edtNgay.setText("");
                            } else {
                                Double ngayTam = Double.parseDouble(String.format(Locale.getDefault(), "%d.%02d%02d", year, month + 1, dayOfMonth));
                                if (ngayTam < ngayBatDau) {
                                    Toast.makeText(context, "Ngày kết thúc phải lớn hơn ngày bắt đầu", Toast.LENGTH_SHORT).show();
                                    edtNgay.setText("");
                                    edtNgay.setError("");
                                } else {
                                    edtNgay.setError(null); // Xóa lỗi khi ngày hợp lệ
                                    ngayKetthuc = Double.parseDouble(String.format(Locale.getDefault(), "%d.%02d%02d", year, month + 1, dayOfMonth));
                                }
                            }
                        }
                    }
                },
                year, month, day
        );

        // Thiết lập ngày tối thiểu cho DatePicker (ngày hiện tại)
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        // Nếu đã có ngày trong EditText, cập nhật DatePicker với ngày hiện tại trong EditText
        String currentDate = edtNgay.getText().toString();
        if (!currentDate.isEmpty()) {
            try {
                String[] parts = currentDate.split("/");
                int existingDay = Integer.parseInt(parts[0]);
                int existingMonth = Integer.parseInt(parts[1]) - 1;  // Chuyển đổi tháng về chỉ số từ 0
                int existingYear = Integer.parseInt(parts[2]);
                datePickerDialog.updateDate(existingYear, existingMonth, existingDay);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        datePickerDialog.show();
    }


    private void onClickGoToSinhVien(LopHocPhan lopHocPhan, Boolean bool) {
        if (bool) {
            Intent intent = new Intent(context, QuanLySinhVienActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("lopHocPhan", lopHocPhan);
            intent.putExtras(bundle);
            context.startActivity(intent);
        } else {
            Intent intent = new Intent(context, DanhSachLopSinhVienActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("lopHocPhan", lopHocPhan);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }

    }

    @Override
    public int getItemCount() {
        if (listHocPhan != null)
            return listHocPhan.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString = StringUtility.removeMark(constraint.toString().toLowerCase());
                if (searchString.isEmpty()) {
                    listHocPhan = listHocPhanOld;
                } else {
                    List<LopHocPhan> list = new ArrayList<>();
                    for (LopHocPhan lopHocPhan : listHocPhanOld) {
                        String tenLop = StringUtility.removeMark(lopHocPhan.getTenLop().toLowerCase());
                        String tenMonHoc =
                                StringUtility.removeMark(monHocManager.getMonHoc(lopHocPhan.getMaMonHoc()).getTenMonHoc().toLowerCase());
                        if (tenMonHoc.startsWith(searchString) || tenMonHoc.contains(searchString) || tenLop.startsWith(searchString) || tenLop.contains(searchString)) {
                            list.add(lopHocPhan);
                        }

                    }
                    listHocPhan = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listHocPhan;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listHocPhan = (List<LopHocPhan>) results.values;

                if (homeFragment != null) {
                    if (listHocPhan == null || listHocPhan.isEmpty()) {
                        homeFragment.setThongBaoVisibility(true);
                    } else {
                        homeFragment.setThongBaoVisibility(false);
                    }
                }

                if (quanLyLopHocActivity != null) {
                    if (listHocPhan == null || listHocPhan.isEmpty()) {
                        quanLyLopHocActivity.setThongBaoVisibility(true);
                    } else {
                        quanLyLopHocActivity.setThongBaoVisibility(false);
                    }
                }
                notifyDataSetChanged();
            }

        };
    }


    public class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView txtNganh, txtMonHoc, txtGVPhuTrach, txtLop;
        CardView layoutItem;
        ImageButton btnEdit, btnDelete;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            layoutItem = itemView.findViewById(R.id.layoutItem);
            txtNganh = itemView.findViewById(R.id.txtNganh);
            txtMonHoc = itemView.findViewById(R.id.txtMonHoc);
            txtGVPhuTrach = itemView.findViewById(R.id.txtGVPhuTrach);
            txtLop = itemView.findViewById(R.id.txtLop);
        }
    }

    public String dateFormat(double ngaySinhDouble) {
        int nam = (int) ngaySinhDouble;
        int thangNgay = (int) ((ngaySinhDouble - nam) * 10000);
        int thang = thangNgay / 100;
        int ngay = thangNgay % 100;
        String formattedDate = String.format("%02d/%02d/%04d", ngay, thang, nam);
        return formattedDate;
    }

    public void release() {
        context = null;

    }
}
