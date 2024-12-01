package com.example.qlsinhvien.fragment;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qlsinhvien.Adapter.LopHocPhanAdapter;
import com.example.qlsinhvien.Models.LopHocPhan;
import com.example.qlsinhvien.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
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

    RecyclerView recycleHocPhan;
    LopHocPhanAdapter lopHocPhanAdapter;
    SearchView searchView;
    MaterialToolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recycleHocPhan = view.findViewById(R.id.recycleHocPhan);
        lopHocPhanAdapter = new LopHocPhanAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recycleHocPhan.setLayoutManager(linearLayoutManager);
        lopHocPhanAdapter.setData(getListHocPhan());
        recycleHocPhan.setAdapter(lopHocPhanAdapter);
        toolbar = view.findViewById(R.id.toolbar);
        OnBackPressedDispatcher onBackPressedDispatcher = requireActivity().getOnBackPressedDispatcher();
        onBackPressedDispatcher.addCallback(requireActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                    return;
                }
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

                    assert searchView != null;
                    searchView.setQueryHint("Tìm theo tên môn học");

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

        return view;
    }


    private List<LopHocPhan> getListHocPhan() {
        List<LopHocPhan> listHocPhan = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            listHocPhan.add(new LopHocPhan("IT1", "OOP", 1,1 ,null,"Dương Hữu Thành"));
        }
        for (int i = 0; i <= 3; i++) {
            listHocPhan.add(new LopHocPhan("CS1", "Công nghệ phần mềm", 1,1 ,null,"Dương Hữu " +
                    "Thành"));
        }
        for (int i = 0; i <= 3; i++) {
            listHocPhan.add(new LopHocPhan("IM1", "Lập trình trên thiết bị di động",1,1 ,null,
                    "Dương Hữu Thành"));
        }
        for (int i = 0; i <= 3; i++) {
            listHocPhan.add(new LopHocPhan("CS2", "Các công nghệ lập trình hiện đại",1,1 ,null
                    ,"Dương Hữu Thành"));
        }
        for (int i = 0; i <= 3; i++) {
            listHocPhan.add(new LopHocPhan("IT2", "Kĩ thuật lập trình", 1,1 ,null,"Dương " +
                    "Hữu Thành"));
        }
        return listHocPhan;
    }

}


