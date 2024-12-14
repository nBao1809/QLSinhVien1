package com.example.qlsinhvien.Fragments;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlsinhvien.Adapter.LopHocPhanAdapter;

import com.example.qlsinhvien.dao.GiangVienManager;
import com.example.qlsinhvien.dao.MonHocManager;
import com.example.qlsinhvien.dao.NganhManager;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.dao.DatabaseHelper;
import com.example.qlsinhvien.dao.LopHocPhanManager;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    TextView txtThongBao;
    RecyclerView recycleHocPhan;
    LopHocPhanAdapter lopHocPhanAdapter;
    SearchView searchView;
    MaterialToolbar toolbar;
    LopHocPhanManager lopHocPhanManager;
    MonHocManager monHocManager;
    GiangVienManager giangVienManager;
    NganhManager nganhManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        txtThongBao = view.findViewById(R.id.txtThongBao);
        recycleHocPhan = view.findViewById(R.id.recycleHocPhan);
        lopHocPhanManager = new LopHocPhanManager(getContext());
        giangVienManager = new GiangVienManager(getContext());
        monHocManager = new MonHocManager(getContext());
        nganhManager = new NganhManager(getContext());
        toolbar = view.findViewById(R.id.toolbar);
        OnBackPressedDispatcher onBackPressedDispatcher = requireActivity().getOnBackPressedDispatcher();
        onBackPressedDispatcher.addCallback(requireActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(searchView!=null){
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                    return;
                }}
                new AlertDialog.Builder(requireActivity())
                        .setTitle("Xác nhận").setIcon(R.drawable.checkicon)
                        .setMessage("Bạn có muốn đăng xuất không?")
                        .setPositiveButton("Có", (dialog, which) -> requireActivity().finish())
                        .setNegativeButton("Không", null)
                        .show();

            }
        });
        toolbar.inflateMenu(R.menu.menutoolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.search) {
                    searchView = (SearchView) item.getActionView();
                    SearchManager searchManager = (SearchManager) requireActivity().getSystemService(Context.SEARCH_SERVICE);
                    searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));


                    if (searchView != null) {
                        searchView.setQueryHint("Tìm theo tên môn học");
                    }


                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            lopHocPhanAdapter.getFilter().filter(query);
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            lopHocPhanAdapter.getFilter().filter(newText);
                            return false;
                        }
                    });
                    return true;
                }
                if (id == R.id.notification) {
                    Toast.makeText(requireActivity(), "Chưa có chức năng", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }

        });
        Menu menu = toolbar.getMenu();

//        nganhManager.addNganh(new Nganh("N2", "Khoa Hoc May Tinh"));
//        giangVienManager.addGiangVien(new GiangVien("GV2", "nguyen van a", "123456", 31, "CNTT", 8));
//        monHocManager.addMonHoc(new MonHoc("ITEC101", "Data Structure", 2, "N2"));
//        lopHocPhanManager.addLopHocPhan(new LopHocPhan("LOP2","DH22CS01",1,2,"ITEC101","GV2"));
        lopHocPhanAdapter = new LopHocPhanAdapter(getContext(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recycleHocPhan.setLayoutManager(linearLayoutManager);
        List<LopHocPhan> lopHocPhan = lopHocPhanManager.getAllLopHocPhan();
        if (lopHocPhan == null || lopHocPhan.isEmpty()) {


        }
        Boolean bool = Boolean.FALSE;
        lopHocPhanAdapter.setData(lopHocPhan,bool);
        recycleHocPhan.setAdapter(lopHocPhanAdapter);
        return view;
    }

    public void setThongBaoVisibility(boolean isVisible) {
        if (isVisible) {
            txtThongBao.setText("Không tìm thấy lớp học phần");
            txtThongBao.setVisibility(View.VISIBLE);
        } else {
            txtThongBao.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (lopHocPhanAdapter != null) {
            lopHocPhanAdapter.release();
        }
    }

//        nganhManager.addNganh(new Nganh("N1", "Khoa Hoc May Tinh"));
//        giangVienManager.addGiangVien(new GiangVien("GV1", "nguyen van a", "123456", 31, "CNTT",
//                1));
//        monHocManager.addMonHoc(new MonHoc("ITEC123", "OOP", 2, "N1"));
//        lopHocPhanManager.addLopHocPhan(new LopHocPhan("LOP1","LOP1",1,2,"ITEC123","GV1"));
//
//        nganhManager.addNganh(new Nganh("N2", "Luat"));
//        giangVienManager.addGiangVien(new GiangVien("GV2", "nguyen van B", "123456", 31, "Luat",
//                2));
//        monHocManager.addMonHoc(new MonHoc("LUAT1", "Mon LUAT", 2, "N2"));
//        lopHocPhanManager.addLopHocPhan(new LopHocPhan("LOP2","LOP2",1,2,"LUAT1","GV2"));
//
//        nganhManager.addNganh(new Nganh("N3", "Kien Truc"));
//        giangVienManager.addGiangVien(new GiangVien("GV3", "Khang", "123456", 31, "CNTT",
//                3));
//        monHocManager.addMonHoc(new MonHoc("KT1", "KT", 2, "N3"));
//        lopHocPhanManager.addLopHocPhan(new LopHocPhan("LOP3","LOP3",1,2,"KT1","GV3"));

}


