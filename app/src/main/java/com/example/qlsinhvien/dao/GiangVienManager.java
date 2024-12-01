package com.example.qlsinhvien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.qlsinhvien.Models.GiangVien;

import java.util.ArrayList;
import java.util.List;

public class GiangVienManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private List<GiangVien> giangVienList;
    public GiangVienManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addGiangVien(GiangVien giangVien) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.MA_GIANGVIEN,giangVien.getMaGiangVien());
        values.put(DatabaseHelper.HOTEN, giangVien.getHoTen());
        values.put(DatabaseHelper.CCCD, giangVien.getCccd());
        values.put(DatabaseHelper.NGAYSINH, giangVien.getNgaySinh());
        values.put(DatabaseHelper.KHOA, giangVien.getKhoa());
        values.put(DatabaseHelper.ID, giangVien.getId());

        long rowInserted = db.insert(DatabaseHelper.TB_GIANGVIEN, null, values);
        db.close();

        return rowInserted; // >0 nếu thêm thành công, -1 nếu thất bại
    }

    public List<GiangVien> getAllGiangVien() {
        giangVienList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TB_GIANGVIEN;
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            giangVienList.add(new GiangVien(c.getString(0), c.getString(1), c.getString(2),
                    c.getInt(3), c.getString(4), c.getInt(5)));
            c.close();
            return giangVienList;
        }
        return null;
    }
    public GiangVien getGiangVien(int maGiangVien) {
        db = dbHelper.getReadableDatabase();
        GiangVien giangVien = null;
        String[] selection = new String[]{String.valueOf(maGiangVien)};
        Cursor c = db.query(DatabaseHelper.TB_GIANGVIEN, null,
                DatabaseHelper.MA_GIANGVIEN +
                        "= ?",
                selection,
                null,
                null
                , null);
        if (c != null) {
            c.moveToFirst();
            giangVien = new GiangVien(c.getString(0), c.getString(1), c.getString(2),
                    c.getInt(3), c.getString(4), c.getInt(5));
            c.close();
            return giangVien;
        }
        return null;
    }
    public int updateGiangVien(GiangVien giangVien) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.MA_GIANGVIEN,giangVien.getMaGiangVien());
        values.put(DatabaseHelper.HOTEN, giangVien.getHoTen());
        values.put(DatabaseHelper.CCCD, giangVien.getCccd());
        values.put(DatabaseHelper.NGAYSINH, giangVien.getNgaySinh());
        values.put(DatabaseHelper.KHOA, giangVien.getKhoa());
        values.put(DatabaseHelper.ID, giangVien.getId());

        int rowsUpdated = db.update(
                DatabaseHelper.TB_GIANGVIEN,
                values,
                DatabaseHelper.MA_GIANGVIEN + " = ?",
                new String[]{giangVien.getMaGiangVien()}
        );
        db.close();//>0 thành công =0 ko thành công
        return rowsUpdated;
    }
    public int deleteGiangVien(String maGiangVien) {
        db = dbHelper.getWritableDatabase();
        String selection = DatabaseHelper.MA_GIANGVIEN + " = ?";
        String[] selectionArgs = { maGiangVien};

        int rowsDeleted = db.delete(DatabaseHelper.TB_GIANGVIEN, selection, selectionArgs);
        db.close();

        return rowsDeleted; // Trả về số dòng bị xóa, >0 nếu xóa thành công, 0 nếu không có gì bị xóa
    }

}
