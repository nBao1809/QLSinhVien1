package com.example.qlsinhvien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qlsinhvien.Models.MonHoc;

import java.util.ArrayList;
import java.util.List;

public class MonHocManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private List<MonHoc> monHocList;

    public MonHocManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addMonHoc(MonHoc monHoc) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.MA_MONHOC, monHoc.getMaMonHoc());
        values.put(DatabaseHelper.TENMONHOC, monHoc.getTenMonHoc());
        values.put(DatabaseHelper.TINCHI, monHoc.getTinChi());
        values.put(DatabaseHelper.MA_NGANH, monHoc.getMaNganh());

        long rowInserted = db.insert(DatabaseHelper.TB_MONHOC, null, values);
        db.close();

        return rowInserted; // >0 nếu thêm thành công, -1 nếu thất bại
    }

    public List<MonHoc> getAllMonHoc() {
        monHocList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TB_MONHOC;
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            monHocList.add(new MonHoc(c.getString(0), c.getString(1), c.getDouble(2),
                    c.getString(3)));
            c.close();
            return monHocList;
        }
        return null;
    }

    public MonHoc getMonHoc(String maMonHoc) {
        db = dbHelper.getReadableDatabase();
        MonHoc monHoc= null;
        String[] selection = new String[]{maMonHoc};
        Cursor c = db.query(DatabaseHelper.TB_MONHOC, null,
                DatabaseHelper.MA_NGANH +
                        "= ?",
                selection,
                null,
                null
                , null);
        if (c != null) {
            c.moveToFirst();
            monHoc = new MonHoc(c.getString(0), c.getString(1), c.getDouble(2),
                    c.getString(3));
            c.close();
            return monHoc;
        }
        return null;
    }

    public int updateMonHoc(MonHoc monHoc) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.MA_MONHOC, monHoc.getMaMonHoc());
        values.put(DatabaseHelper.TENMONHOC, monHoc.getTenMonHoc());
        values.put(DatabaseHelper.TINCHI, monHoc.getTinChi());
        values.put(DatabaseHelper.MA_NGANH, monHoc.getMaNganh());

        int rowsUpdated = db.update(
                DatabaseHelper.TB_MONHOC,
                values,
                DatabaseHelper.MA_NGANH + " = ?",
                new String[]{monHoc.getMaMonHoc()}
        );
        db.close();//>0 thành công =0 ko thành công
        return rowsUpdated;
    }

    public int deleteMonHoc(String maMonHoc) {
        db = dbHelper.getWritableDatabase();
        String selection = DatabaseHelper.MA_MONHOC + " = ?";
        String[] selectionArgs = {maMonHoc};

        int rowsDeleted = db.delete(DatabaseHelper.TB_MONHOC, selection, selectionArgs);
        db.close();

        return rowsDeleted; // Trả về số dòng bị xóa, >0 nếu xóa thành công, 0 nếu không có gì bị xóa
    }
}
