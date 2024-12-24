package com.example.qlsinhvien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.qlsinhvien.Models.HocKy;
import com.example.qlsinhvien.Models.LopSinhVien;

import java.util.ArrayList;
import java.util.List;

public class LopSinhVienManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private List<LopSinhVien> lopSinhVienList;

    public LopSinhVienManager(Context context) {
        dbHelper = new DatabaseHelper(context);
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

    public String getNextLopSinhVienID() {
        String query = "SELECT MAX(CAST(SUBSTR(" + DatabaseHelper.MA_LOPSINHVIEN + ", 4) AS INTEGER)) FROM " + DatabaseHelper.TB_LOPSINHVIEN;
        Cursor cursor = null;
        int nextID = 1;

        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                int maxID = cursor.getInt(0);
                nextID = maxID + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return String.format("LSV%03d", nextID);
    }

    public List<String> getMaLopFromMSSV(String mssv) {
        db = dbHelper.getReadableDatabase();
        List<String> maLopList = new ArrayList<>();
        String query = "SELECT " + DatabaseHelper.MA_LOP + " FROM " + DatabaseHelper.TB_LOPSINHVIEN + " WHERE " + DatabaseHelper.MSSV + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{mssv});

        if (cursor != null && cursor.moveToFirst()) {
            do {

                maLopList.add(cursor.getString(0));
            } while (cursor.moveToNext());
            cursor.close();
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return maLopList;
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

    public List<String> getMSSVfromMalop(String maLop) {
        List<String> mssvList = new ArrayList<>();
        String query = "SELECT " + DatabaseHelper.MSSV + " FROM " + DatabaseHelper.TB_LOPSINHVIEN + " WHERE " + DatabaseHelper.MA_LOP + " =?";
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query, new String[]{maLop});

        if (c != null && c.moveToFirst()) {
            do {
                mssvList.add(c.getString(0));
            } while (c.moveToNext());
            c.close();
            return mssvList;
        }
        if (c != null) {
            c.close();
        }
        return null;
    }

    public String getMaHocKyfromMalop(String maLop) {
        db = dbHelper.getReadableDatabase();
        String maHocKy;
        String query = "SELECT " + DatabaseHelper.MA_HOCKY + " FROM " + DatabaseHelper.TB_LOPSINHVIEN + " WHERE " + DatabaseHelper.MA_LOP + " =?";
        Cursor c = db.rawQuery(query, new String[]{maLop});

        if (c != null && c.moveToFirst()) {
            maHocKy = c.getString(0);
            c.close();
            return maHocKy;
        }
        if (c != null) {
            c.close();
        }
        return null;
    }

    public String getMaLopSinhVienfromMalopMSSV(String maLop, String MSSV) {
        db = dbHelper.getReadableDatabase();
        String maLopSinhVien;
        String query = "SELECT " + DatabaseHelper.MA_LOPSINHVIEN + " FROM " + DatabaseHelper.TB_LOPSINHVIEN + " WHERE " + DatabaseHelper.MA_LOP + " =? " + " AND " + DatabaseHelper.MSSV + " =? ";
        Cursor c = db.rawQuery(query, new String[]{maLop, MSSV});

        if (c != null && c.moveToFirst()) {
            maLopSinhVien = c.getString(0);
            c.close();
            return maLopSinhVien;
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
        String selection = DatabaseHelper.MA_LOPSINHVIEN + " = ?";
        String[] selectionArgs = {maLopSinhVien};

        int rowsDeleted = db.delete(DatabaseHelper.TB_LOPSINHVIEN, selection, selectionArgs);
        db.close();

        return rowsDeleted; // Trả về số dòng bị xóa, >0 nếu xóa thành công, 0 nếu không có gì bị xóa
    }
}
