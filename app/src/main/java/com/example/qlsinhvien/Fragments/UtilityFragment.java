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


import com.example.qlsinhvien.Activities.QuanLyLopHocActivity;

import com.example.qlsinhvien.R;
import com.google.android.material.appbar.MaterialToolbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UtilityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UtilityFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UtilityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UtilityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UtilityFragment newInstance(String param1, String param2) {
        UtilityFragment fragment = new UtilityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    MaterialToolbar toolbar;
    Button btnQuanLyLopHoc;



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
        btnQuanLyLopHoc=view.findViewById(R.id.btnQuanLyLopHoc);
        btnQuanLyLopHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(requireActivity(), QuanLyLopHocActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }
}