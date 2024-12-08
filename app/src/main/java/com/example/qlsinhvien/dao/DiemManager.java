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

        values.put(DatabaseHelper.MA_DIEM, diem.getMaDiem());
        values.put(DatabaseHelper.DIEMSO, diem.getDiemSo());
        values.put(DatabaseHelper.TRONGSO, diem.getMaLoaiDiem());
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
