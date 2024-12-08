package com.example.qlsinhvien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qlsinhvien.Models.LopSinhVien;

import java.util.ArrayList;
import java.util.List;

public class LopSinhVienManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private List<LopSinhVien> lopSinhVienList;
    public LopSinhVienManager(Context context){
        dbHelper=new DatabaseHelper(context);
    }

    public long addLopSinhVien(LopSinhVien lopSinhVien) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.MA_LOPSINHVIEN, lopSinhVien.getMaLopSinhVien());
        values.put(DatabaseHelper.MA_LOP, lopSinhVien.getMaLop());
        values.put(DatabaseHelper.MSSV, lopSinhVien.getMssv());
        values.put(DatabaseHelper.MA_HOCKY, lopSinhVien.getMaHocKy());

        long rowInserted = db.insert(DatabaseHelper.TB_LOPSINHVIEN, null, values);
        db.close();

        return rowInserted; // >0 nếu thêm thành công, -1 nếu thất bại
    }

    public List<LopSinhVien> getAllLopSinhVien() {
        lopSinhVienList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TB_LOPSINHVIEN;
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c != null && c.moveToFirst()) {
            do {
                lopSinhVienList.add(new LopSinhVien(c.getString(0), c.getString(1), c.getString(2), c.getString(3)));
            } while (c.moveToNext());
            c.close();
            return lopSinhVienList;
        }
        if (c != null) {
            c.close();
        }
        return null;
    }

    public LopSinhVien getLopSinhVien(String maLopSinhVien) {
        db = dbHelper.getReadableDatabase();
        LopSinhVien lopSinhVien = null;
        String[] selection = new String[]{maLopSinhVien};
        Cursor c = db.query(DatabaseHelper.TB_LOPSINHVIEN, null,
                DatabaseHelper.MA_LOP + "= ?",
                selection,
                null,
                null,
                null);

        if (c != null && c.moveToFirst()) {
            lopSinhVien = new LopSinhVien(c.getString(0), c.getString(1), c.getString(2), c.getString(3));
            c.close();
            return lopSinhVien;
        }
        if (c != null) {
            c.close();
        }
        return null;
    }

    public int updateLopSinhVien(LopSinhVien lopSinhVien) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.MA_LOPSINHVIEN, lopSinhVien.getMaLopSinhVien());
        values.put(DatabaseHelper.MA_LOP, lopSinhVien.getMaLop());
        values.put(DatabaseHelper.MSSV, lopSinhVien.getMssv());
        values.put(DatabaseHelper.MA_HOCKY, lopSinhVien.getMaHocKy());

        int rowsUpdated = db.update(
                DatabaseHelper.TB_LOPSINHVIEN,
                values,
                DatabaseHelper.MA_LOP + " = ?",
                new String[]{lopSinhVien.getMaLop()}
        );
        db.close();//>0 thành công =0 ko thành công
        return rowsUpdated;
    }

    public int deleteLopSinhVien(String maLopSinhVien) {
        db = dbHelper.getWritableDatabase();
        String selection = DatabaseHelper.MA_LOP + " = ?";
        String[] selectionArgs = {maLopSinhVien};

        int rowsDeleted = db.delete(DatabaseHelper.TB_LOPSINHVIEN, selection, selectionArgs);
        db.close();

        return rowsDeleted; // Trả về số dòng bị xóa, >0 nếu xóa thành công, 0 nếu không có gì bị xóa
    }
}
