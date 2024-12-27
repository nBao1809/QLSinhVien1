package com.example.qlsinhvien.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlsinhvien.Activities.SettingActivity;
import com.example.qlsinhvien.Models.Role;
import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.dao.RoleManager;
import com.example.qlsinhvien.dao.UserManager;
import com.google.android.material.appbar.MaterialToolbar;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    private ActivityResultLauncher<Intent> imagePicker;
    private UserManager userManager;
    private RoleManager roleManager;
    ImageView imageView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CURRENTUSER = "user";
    private User currentUser;

    public AccountFragment() {
    }

    public AccountFragment newInstance(int currentUserID) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CURRENTUSER, currentUserID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userManager = new UserManager(requireContext());
        roleManager = new RoleManager(requireContext());
        if (getArguments() != null) {
            currentUser = userManager.getUserByID(getArguments().getInt(ARG_CURRENTUSER));
        }
    }

    MaterialToolbar toolbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        TextView txtUsername, txtMail, txtRole, txtName;
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        txtName = view.findViewById(R.id.txtNameUser);
        txtRole = view.findViewById(R.id.txtRole);
        txtUsername = view.findViewById(R.id.txtTen);
        txtMail = view.findViewById(R.id.txtmail);

        imageView = view.findViewById(R.id.imageView);

        userManager = new UserManager(requireContext());

        imageView.setImageBitmap(currentUser.getPhoto());
        txtUsername.setText(currentUser.getUsername());
        txtMail.setText(currentUser.getEmail());
        txtName.setText(currentUser.getUsername());
        txtRole.setText(roleManager.getRole(currentUser.getRole()).getTenRole());
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menuaccount);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.notification) {
                    Toast.makeText(requireActivity(), "Chưa có chức năng", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        Menu menu = toolbar.getMenu();
        toolbar.setNavigationOnClickListener(v -> {
            Intent myIntent = new Intent(requireActivity(), SettingActivity.class);
            myIntent.putExtra("ID",currentUser.getID());
            startActivity(myIntent);
        });
        ImageButton btn = view.findViewById(R.id.btnEdit);
        ImageView imageView = view.findViewById(R.id.imageView);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                imagePicker.launch(intent);
            }
        });

        imagePicker = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData(); // Lấy URI ảnh được chọn
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), imageUri);
                            imageView.setImageBitmap(bitmap);
                            userManager.updatePhoto(currentUser.getID(), bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(requireContext(), "Lỗi khi xử lý ảnh!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return view;
    }
    public String dateFormat(double ngaySinhDouble) {
        int nam = (int) ngaySinhDouble;
        int thangNgay = (int) ((ngaySinhDouble - nam) * 10000);
        int thang = thangNgay / 100;
        int ngay = thangNgay % 100;
        String formattedDate = String.format("%02d/%02d/%04d", ngay, thang, nam);
        return formattedDate;
    }
}
