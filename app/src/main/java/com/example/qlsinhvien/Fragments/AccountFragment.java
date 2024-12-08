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
import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.R;
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
    ImageView imageView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CURRENTUSER = "userID";
    private int currentUserID;

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
        if (getArguments() != null) {
            currentUserID = getArguments().getInt(ARG_CURRENTUSER);
        }
    }

    MaterialToolbar toolbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        TextView txtTen, txtMail;
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        txtTen = view.findViewById(R.id.txtTen);
        txtMail = view.findViewById(R.id.txtmail);

        imageView = view.findViewById(R.id.imageView);

        userManager = new UserManager(requireContext());
        User currentUser = userManager.getUserByID(currentUserID);

        imageView.setImageBitmap(currentUser.getPhoto());
        txtTen.setText(userManager.getUserByID(currentUser.getID()).getUsername());
        txtMail.setText(userManager.getUserByID(currentUser.getID()).getEmail());
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
            startActivity(myIntent);
        });
        Button btn = view.findViewById(R.id.button2);
        ImageView imageView = view.findViewById(R.id.imageView);
        ImageButton imageButton = view.findViewById(R.id.edtImage);
        btn.setOnClickListener(new View.OnClickListener() {
            boolean isEditing = false;

            @Override
            public void onClick(View v) {
                if (!isEditing) {
                    // Bắt đầu chỉnh sửa
                    imageButton.setVisibility(View.VISIBLE);
                    btn.setText("Lưu");
                    isEditing = true;
                } else {
                    // Lưu nội dung và hiển thị lại
                    imageView.setVisibility(View.VISIBLE);
                    imageButton.setVisibility(View.GONE);
                    btn.setText("Chỉnh sửa");
                    isEditing = false;
                }
            }
        });
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePicker.launch(intent);
        });
userManager.sendEmail("tonhatbao@gmail.com");
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
}