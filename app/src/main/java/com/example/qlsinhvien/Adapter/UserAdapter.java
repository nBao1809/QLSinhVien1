package com.example.qlsinhvien.Adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;


import com.example.qlsinhvien.Models.GiangVien;
import com.example.qlsinhvien.Models.Role;
import com.example.qlsinhvien.Models.SinhVien;
import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.Activities.ThongTinChiTietUserActivity;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;
import com.example.qlsinhvien.dao.GiangVienManager;
import com.example.qlsinhvien.dao.RoleManager;
import com.example.qlsinhvien.dao.SinhVienManager;
import com.example.qlsinhvien.dao.UserManager;


import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {


    private Context context;
    private List<User> userList, userListOld;
    private User currentUser;
    private GiangVienManager giangVienManager;
    private SinhVienManager sinhVienManager;
    private UserManager userManager;
    private RoleManager roleManager;
    public static final int VIEW_TYPE_ADMIN = 0;
    public static final int VIEW_TYPE_MOD = 1;
    public static final int VIEW_TYPE_GIANGVIEN = 2;
    public static final int VIEW_TYPE_SINHVIEN = 3;
    String role;

    public UserAdapter(Context context, User user) {
        this.currentUser = user;
        this.context = context;
        giangVienManager = new GiangVienManager(context);
        sinhVienManager = new SinhVienManager(context);
        userManager = new UserManager(context);
        roleManager = new RoleManager(context);
    }

    public void setData(List<User> userList) {
        if (userList == null) return;
        this.userList = userList;
        this.userListOld = userList;
        notifyDataSetChanged();
    }

    public void onClickEdit(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.itemchinhsuauser, null);
        builder.setView(view);

        EditText edtUsername, edtPassword, edtConfirm, edtEmail;
        Button btnHuy, btnLuu;
        Spinner spinnerRole;
        TextView vaiTro;
        edtUsername = view.findViewById(R.id.edtUsername);
        edtPassword = view.findViewById(R.id.edtPassword);
        edtConfirm = view.findViewById(R.id.edtConfirmPassword);
        edtEmail = view.findViewById(R.id.edtEmail);
        btnHuy = view.findViewById(R.id.btnHuy);
        btnLuu = view.findViewById(R.id.btnLuu);
        spinnerRole = view.findViewById(R.id.spinnerRole);
        vaiTro=view.findViewById(R.id.txtVaitro);
        edtUsername.setText(user.getUsername());
        edtEmail.setText(user.getEmail());

        AlertDialog alertDialog = builder.create();
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_themlophocphan);
        }
        alertDialog.show();
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        List<Role> roleList = roleManager.getAllRole();

        roleList.removeIf(role -> role.getMaRole().equals("sv") || role.getMaRole().equals("gv") || role.getMaRole().equals("superadmin"));

        if (user.getRole().equals("sv") || user.getRole().equals("gv")) {
            vaiTro.setVisibility(View.GONE);
            spinnerRole.setVisibility(View.GONE);
        }

        RoleAdapter roleAdapter = new RoleAdapter(context, R.layout.itemgiangvienselected, roleList
        );
        spinnerRole.setAdapter(roleAdapter);

        spinnerRole.setAdapter(roleAdapter);
        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                role = roleAdapter.getItem(position).getMaRole();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = user.getID();
                String userString = user.getUsername().trim();
                String password = edtPassword.getText().toString().trim();
                String cfPassword = edtConfirm.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                if (!user.getRole().equals(role)) {
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getID() == ID) {
                            userList.remove(i);
                            break;
                        }
                    }
                }
                if (ID == 0 || userString.isEmpty() || password.isEmpty() || email.isEmpty() || role == null) {
                    Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (password.equals(cfPassword)) {
                    User userUpdate = new User(ID, userString, password,
                            null, email, role);
                    int ketqua = userManager.updateUser(userUpdate);
                    if (ketqua > 0) {
                        for (int i = 0; i < userList.size(); i++) {
                            if (userList.get(i).getID() == ID) {
                                userList.set(i, userManager.getUserByID(ID));
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

    public void onClickDelete(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xóa user " + user.getUsername() + " ?");
        builder.setMessage("Bạn có chắc muốn xóa user " + user.getUsername() + " không");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int ketquaUser = userManager.deleteUser(user.getID());
                userList.remove(user);
                notifyDataSetChanged();
                if (ketquaUser > 0) {
                    View view = LayoutInflater.from(context).inflate(R.layout.successdialog, null);
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setView(view);
                    Button successDone = view.findViewById(R.id.successDone);
                    TextView successBelow = view.findViewById(R.id.successBelow);
                    AlertDialog alertDialog = builder1.create();
                    if (alertDialog.getWindow() != null) {
                        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custome_success);
                        alertDialog.getWindow().setGravity(Gravity.CENTER);
                    }
                    alertDialog.show();
                    successBelow.setText("Xóa user thành công!!!");
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
                    failBelow.setText("Xóa user thất bại");
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

    @Override
    public int getItemViewType(int position) {
        User user = userList.get(position);
        if (user.getRole().equals("admin")) {
            return VIEW_TYPE_ADMIN;
        } else if (user.getRole().equals("mod")) {
            return VIEW_TYPE_MOD;
        } else if (user.getRole().equals("gv")) {
            return VIEW_TYPE_GIANGVIEN;
        } else if (user.getRole().equals("sv")) {
            return VIEW_TYPE_SINHVIEN;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_MOD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemuser_minmod, parent, false);
            return new ModViewHolder(view);
        } else if (viewType == VIEW_TYPE_GIANGVIEN) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemuser_gv, parent, false);
            return new GiangVienViewHolder(view);
        } else if (viewType == VIEW_TYPE_SINHVIEN) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemuser_sv, parent, false);
            return new SinhVienViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemuser_minmod, parent, false);
            return new AdminViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User user = userList.get(position);
        if (user == null) return;
        if (holder instanceof AdminViewHolder) {
            AdminViewHolder viewHolder = (AdminViewHolder) holder;
            viewHolder.txtID.setText(String.valueOf(user.getID()));
            viewHolder.txtUsername.setText(user.getUsername());
            viewHolder.txtEmail.setText(user.getEmail());
            viewHolder.txtRole.setText(user.getRole());
            viewHolder.photo.setImageBitmap(user.getPhoto());
            if (currentUser.getRole().equals("mod")||currentUser.getRole().equals("admin")) {
                viewHolder.btnEdit.setVisibility(View.GONE);
                viewHolder.btnDelete.setVisibility(View.GONE);
            }
            viewHolder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(holder.itemView.getContext(), ThongTinChiTietUserActivity.class);
                intent.putExtra("userID", user.getID());
                holder.itemView.getContext().startActivity(intent);
            });

            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickDelete(user);
                }
            });
            viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickEdit(user);
                }
            });
        }
        if (holder instanceof ModViewHolder) {
            ModViewHolder viewHolder = (ModViewHolder) holder;
            viewHolder.txtID.setText(String.valueOf(user.getID()));
            viewHolder.txtUsername.setText(user.getUsername());
            viewHolder.txtEmail.setText(user.getEmail());
            viewHolder.txtRole.setText(user.getRole());
            viewHolder.photo.setImageBitmap(user.getPhoto());
            if (currentUser.getRole().equals("mod")) {
                viewHolder.btnEdit.setVisibility(View.GONE);
                viewHolder.btnDelete.setVisibility(View.GONE);
            }
            viewHolder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(holder.itemView.getContext(), ThongTinChiTietUserActivity.class);
                intent.putExtra("userID", user.getID());
                holder.itemView.getContext().startActivity(intent);
            });
            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickDelete(user);
                }
            });
            viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickEdit(user);
                }
            });
        }
        if (holder instanceof GiangVienViewHolder) {
            GiangVien giangVien = giangVienManager.getGiangVienFromUser(user.getID());
            Log.d("test", giangVien.getMaGiangVien());
            GiangVienViewHolder viewHolder = (GiangVienViewHolder) holder;
            viewHolder.txtID.setText(String.valueOf(user.getID()));
            viewHolder.txtUsername.setText(user.getUsername());
            viewHolder.txtEmail.setText(user.getEmail());
            viewHolder.txtRole.setText(user.getRole());
            viewHolder.photo.setImageBitmap(user.getPhoto());
            if (giangVien != null) {
                viewHolder.txtMSGV.setText(giangVien.getMaGiangVien());
                viewHolder.txtTen.setText(giangVien.getHoTen());
            }
            viewHolder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(holder.itemView.getContext(), ThongTinChiTietUserActivity.class);
                intent.putExtra("userID", user.getID());
                holder.itemView.getContext().startActivity(intent);
            });
            viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickEdit(user);
                }
            });
        }
        if (holder instanceof SinhVienViewHolder) {
            SinhVien sinhVien = sinhVienManager.getSinhVienFromUser(user.getID());
            SinhVienViewHolder viewHolder = (SinhVienViewHolder) holder;
            viewHolder.txtID.setText(String.valueOf(user.getID()));
            viewHolder.txtUsername.setText(user.getUsername());
            viewHolder.txtEmail.setText(user.getEmail());
            viewHolder.txtRole.setText(user.getRole());
            viewHolder.photo.setImageBitmap(user.getPhoto());
            if (sinhVien != null) {
                viewHolder.txtMSSV.setText(sinhVien.getMssv());
                viewHolder.txtTen.setText(sinhVien.getHoTen());
            }
            viewHolder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(holder.itemView.getContext(), ThongTinChiTietUserActivity.class);
                intent.putExtra("userID", user.getID());
                holder.itemView.getContext().startActivity(intent);
            });
            viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickEdit(user);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (userList != null) return userList.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString = StringUtility.removeMark(constraint.toString().toLowerCase());
                if (searchString.isEmpty()) {
                    userList = userListOld;
                } else {
                    List<User> list = new ArrayList<>();
                    for (User user : userListOld) {
                        String username = StringUtility.removeMark(user.getUsername().toLowerCase());
                        if (username.contains(searchString)) {
                            list.add(user);
                        }

                    }
                    userList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = userList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                userList = (List<User>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class AdminViewHolder extends RecyclerView.ViewHolder {
        TextView txtID, txtUsername, txtEmail, txtRole;
        ImageView photo;
        ImageButton btnEdit, btnDelete;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            txtID = itemView.findViewById(R.id.txtID);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtRole = itemView.findViewById(R.id.txtRole);
            photo = itemView.findViewById(R.id.photo);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public class ModViewHolder extends RecyclerView.ViewHolder {
        TextView txtID, txtUsername, txtEmail, txtRole;
        ImageView photo;
        ImageButton btnEdit, btnDelete;

        public ModViewHolder(@NonNull View itemView) {
            super(itemView);
            txtID = itemView.findViewById(R.id.txtID);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtRole = itemView.findViewById(R.id.txtRole);
            photo = itemView.findViewById(R.id.photo);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public class GiangVienViewHolder extends RecyclerView.ViewHolder {
        TextView txtID, txtUsername, txtEmail, txtRole, txtMSGV, txtTen;
        ImageView photo;
        ImageButton btnEdit;


        public GiangVienViewHolder(@NonNull View itemView) {
            super(itemView);
            txtID = itemView.findViewById(R.id.txtID);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtRole = itemView.findViewById(R.id.txtRole);
            photo = itemView.findViewById(R.id.photo);
            txtMSGV = itemView.findViewById(R.id.txtMSGV);
            txtTen = itemView.findViewById(R.id.txtTen);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }

    public class SinhVienViewHolder extends RecyclerView.ViewHolder {
        TextView txtID, txtUsername, txtEmail, txtRole, txtMSSV, txtTen;
        ImageView photo;
        ImageButton btnEdit;


        public SinhVienViewHolder(@NonNull View itemView) {
            super(itemView);
            txtID = itemView.findViewById(R.id.txtID);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtRole = itemView.findViewById(R.id.txtRole);
            photo = itemView.findViewById(R.id.photo);
            txtMSSV = itemView.findViewById(R.id.txtMSSV);
            txtTen = itemView.findViewById(R.id.txtTen);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }


}
