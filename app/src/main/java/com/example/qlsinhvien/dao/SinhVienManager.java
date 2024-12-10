package com.example.qlsinhvien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qlsinhvien.Models.SinhVien;

import java.util.ArrayList;
import java.util.List;

public class SinhVienManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private List<SinhVien> sinhVienList;

    public SinhVienManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addSinhVien(SinhVien sinhVien) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.MSSV, sinhVien.getMssv());
        values.put(DatabaseHelper.HOTEN, sinhVien.getHoTen());
        values.put(DatabaseHelper.CCCD, sinhVien.getCccd());
        values.put(DatabaseHelper.NGAYSINH, sinhVien.getNgaySinh());
        values.put(DatabaseHelper.ID, sinhVien.getId());
        values.put(DatabaseHelper.MA_LOPHANHCHINH, sinhVien.getMaLopHanhChinh());
        values.put(DatabaseHelper.MA_NGANH, sinhVien.getMaNganh());

        long rowInserted = db.insert(DatabaseHelper.TB_SINHVIEN, null, values);
        db.close();

        return rowInserted; // >0 nếu thêm thành công, -1 nếu thất bại
    }

    public List<SinhVien> getAllSinhVien() {
        sinhVienList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TB_SINHVIEN;
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null && c.moveToFirst()) {

            sinhVienList.add(new SinhVien(c.getString(0), c.getString(1), c.getString(2),
                    c.getInt(3), c.getInt(4), c.getString(5), c.getString(6)));
            c.close();
            return sinhVienList;

        }
        if (c != null) {
            c.close();
        }
        return null;
    }

    public SinhVien getSinhVien(String maSinhVien) {
        db = dbHelper.getReadableDatabase();
        SinhVien sinhVien = null;
        String[] selection = new String[]{maSinhVien};
        Cursor c = db.query(DatabaseHelper.TB_SINHVIEN, null,
                DatabaseHelper.MSSV + "= ?",
                selection,
                null,
                null,
                null);

        if (c != null && c.moveToFirst()) {
            sinhVien = new SinhVien(c.getString(0), c.getString(1), c.getString(2),
                    c.getInt(3), c.getInt(4), c.getString(5), c.getString(6));
            c.close();
            return sinhVien;
        }
        return null;

    }

    public int updateSinhVien(SinhVien sinhVien) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.MSSV, sinhVien.getMssv());
        values.put(DatabaseHelper.HOTEN, sinhVien.getHoTen());
        values.put(DatabaseHelper.CCCD, sinhVien.getCccd());
        values.put(DatabaseHelper.NGAYSINH, sinhVien.getNgaySinh());
        values.put(DatabaseHelper.KHOA, sinhVien.getId());
        values.put(DatabaseHelper.ID, sinhVien.getMaLopHanhChinh());
        values.put(DatabaseHelper.KHOA, sinhVien.getMaNganh());

        int rowsUpdated = db.update(
                DatabaseHelper.TB_SINHVIEN,
                values,
                DatabaseHelper.MSSV + " = ?",
                new String[]{sinhVien.getMssv()}
        );
        db.close();//>0 thành công =0 ko thành công
        return rowsUpdated;
    }

    public int deleteSinhVien(String mssv) {
        db = dbHelper.getWritableDatabase();
        String selection = DatabaseHelper.MSSV + " = ?";
        String[] selectionArgs = {mssv};

        int rowsDeleted = db.delete(DatabaseHelper.TB_SINHVIEN, selection, selectionArgs);
        db.close();

        return rowsDeleted; // Trả về số dòng bị xóa, >0 nếu xóa thành công, 0 nếu không có gì bị xóa
    }
}
