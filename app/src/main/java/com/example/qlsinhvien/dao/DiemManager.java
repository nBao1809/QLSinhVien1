package com.example.qlsinhvien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qlsinhvien.Models.Diem;

import java.util.ArrayList;
import java.util.List;

public class DiemManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private List<Diem> diemList;

    public DiemManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addDiem(Diem diem) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.DIEMSO, diem.getDiemSo());
        values.put(DatabaseHelper.MA_LOAIDIEM, diem.getMaLoaiDiem());
        values.put(DatabaseHelper.MA_LOPSINHVIEN, diem.getMaLopSinhVien());

        long rowInserted = db.insert(DatabaseHelper.TB_DIEM, null, values);
        db.close();

        return rowInserted; // >0 nếu thêm thành công, -1 nếu thất bại
    }

    public List<Diem> getAllDiem() {
        diemList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TB_DIEM;
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c != null && c.moveToFirst()) {
            do {
                diemList.add(new Diem(c.getInt(0), c.getDouble(1), c.getString(2), c.getString(3)));
            } while (c.moveToNext());
            c.close();
            return diemList;
        }
        if (c != null) {
            c.close();
        }
        return null;

    }

    public int getDiemID() {
        String query = "SELECT " + DatabaseHelper.MA_DIEM +
                " FROM " + DatabaseHelper.TB_DIEM +
                " ORDER BY " + DatabaseHelper.MA_DIEM + " DESC LIMIT 1";
        Cursor cursor = null;
        int ID = 1; // ID mặc định ban đầu là 1

        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                String lastID = cursor.getString(0); // Lấy ID cuối cùng
                try {
                    // Lấy số từ ID và tăng lên 1
                    int number = Integer.parseInt(lastID.replace("DIEM", ""));
                    ID = number;
                } catch (NumberFormatException e) {
                    // Nếu không thể parse được số, đặt ID mặc định là 1
                    ID = 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return ID; // Trả về ID tiếp theo dưới dạng số
    }


    public Diem getDiem(int maDiem) {
        db = dbHelper.getReadableDatabase();
        Diem diem = null;
        String[] selection = new String[]{String.valueOf(maDiem)};
        Cursor c = db.query(DatabaseHelper.TB_DIEM, null,
                DatabaseHelper.MA_DIEM + "= ?",
                selection,
                null,
                null,
                null);

        if (c != null && c.moveToFirst()) {
            diem = new Diem(c.getInt(0), c.getDouble(1), c.getString(2), c.getString(3));
            c.close();
            return diem;
        }
        if (c != null) {
            c.close();
        }
        return null;
    }

    public Diem getDiemfromLopSinhVienID(String maLopSinhVien) {
        db = dbHelper.getReadableDatabase();
        Diem diem = null;
        String[] selection = new String[]{String.valueOf(maLopSinhVien)};
        Cursor c = db.query(DatabaseHelper.TB_DIEM, null,
                DatabaseHelper.MA_LOPSINHVIEN + "= ?",
                selection,
                null,
                null,
                null);

        if (c != null && c.moveToFirst()) {
            diem = new Diem(c.getInt(0), c.getDouble(1), c.getString(2), c.getString(3));
            c.close();
            return diem;
        }
        if (c != null) {
            c.close();
        }
        return null;
    }

    public List<Diem> getDiemfromMalopsinhvien(String maLopSinhVien) {
        diemList = new ArrayList<>();
        String query = "SELECT * " + " FROM " + DatabaseHelper.TB_DIEM + " WHERE " + DatabaseHelper.MA_LOPSINHVIEN + " =?";
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query, new String[]{maLopSinhVien});

        if (c != null && c.moveToFirst()) {
            do {
                diemList.add(new Diem(c.getInt(0), c.getDouble(1), c.getString(2), c.getString(3)));
            } while (c.moveToNext());
            c.close();
            return diemList;
        }
        if (c != null) {
            c.close();
        }
        return null;
    }


    public int updateDiem(Diem Diem) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.MA_DIEM, Diem.getMaDiem());
        values.put(DatabaseHelper.DIEMSO, Diem.getDiemSo());
        values.put(DatabaseHelper.MA_LOAIDIEM, Diem.getMaLoaiDiem());
        values.put(DatabaseHelper.MA_LOPSINHVIEN, Diem.getMaLopSinhVien());

        int rowsUpdated = db.update(
                DatabaseHelper.TB_DIEM,
                values,
                DatabaseHelper.MA_DIEM + " = ?",
                new String[]{String.valueOf(Diem.getMaDiem())}
        );
        db.close();//>0 thành công =0 ko thành công
        return rowsUpdated;
    }

    public int deleteDiem(long maDiem) {
        db = dbHelper.getWritableDatabase();
        String selection = DatabaseHelper.MA_DIEM + " = ?";
        String[] selectionArgs = {String.valueOf(maDiem)};

        int rowsDeleted = db.delete(DatabaseHelper.TB_DIEM, selection, selectionArgs);
        db.close();

        return rowsDeleted; // Trả về số dòng bị xóa, >0 nếu xóa thành công, 0 nếu không có gì bị xóa
    }
}
