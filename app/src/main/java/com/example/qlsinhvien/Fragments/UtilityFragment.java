package com.example.qlsinhvien.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.qlsinhvien.Activities.DanhSachLopSinhVienActivity;
import com.example.qlsinhvien.Activities.QuanLyDanhSachSinhVienActivity;
import com.example.qlsinhvien.Activities.QuanLyGiangVienActivity;
import com.example.qlsinhvien.Activities.QuanLyLopHocActivity;

import com.example.qlsinhvien.Activities.UserActivity;
import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.dao.UserManager;
import com.google.android.material.appbar.MaterialToolbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UtilityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UtilityFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CURRENTUSER = "user";
    private User currentUser;
    Button btnUser;
    private UserManager userManager;

    public UtilityFragment() {
    }

    public UtilityFragment newInstance(int currentUserID) {
        UtilityFragment fragment = new UtilityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CURRENTUSER, currentUserID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userManager = new UserManager(requireContext());
        if (getArguments() != null) {
            currentUser = userManager.getUserByID(getArguments().getInt(ARG_CURRENTUSER));
        }
    }

    MaterialToolbar toolbar;

    Button btnQuanLyLopHoc, btnQuanLyDanhSachSinhVien, btnQuanLyGiangVien;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_utility, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menuutility);
        btnUser=view.findViewById(R.id.btnQuanLiUser);
        btnUser.setOnClickListener(v -> {
            Intent intent=new Intent(requireContext(), UserActivity.class);
            startActivity(intent);
        });
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
        btnQuanLyLopHoc = view.findViewById(R.id.btnQuanLyLopHoc);
        btnQuanLyLopHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), QuanLyLopHocActivity.class);
                startActivity(intent);
            }
        });
        btnQuanLyDanhSachSinhVien = view.findViewById(R.id.btnQuanLyDanhSachSinhVien);
        btnQuanLyDanhSachSinhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), QuanLyDanhSachSinhVienActivity.class);
                startActivity(intent);
            }
        });
        btnQuanLyGiangVien = view.findViewById(R.id.btnQuanLyGiangVien);
        btnQuanLyGiangVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), QuanLyGiangVienActivity.class);
                startActivity(intent);
            }
        });

        return view;

    }
}