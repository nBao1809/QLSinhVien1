package com.example.qlsinhvien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qlsinhvien.Models.HocKy;

import java.util.ArrayList;
import java.util.List;

public class HocKyManager {

    private final DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private List<HocKy> HocKyList;

    public HocKyManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addHocKy(HocKy HocKy) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.MA_HOCKY, HocKy.getMaHocKy());
        values.put(DatabaseHelper.TENHOCKY, HocKy.getTenHocKy());
        values.put(DatabaseHelper.NAMHOC, HocKy.getNamHoc());

        long rowInserted = db.insert(DatabaseHelper.TB_HOCKY, null, values);
        db.close();

        return rowInserted; // >0 nếu thêm thành công, -1 nếu thất bại
    }

    public List<HocKy> getAllHocKy() {
        HocKyList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TB_HOCKY;
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c != null && c.moveToFirst()) {
            do {
                HocKyList.add(new HocKy(c.getString(0), c.getString(1), c.getString(2)));
            } while (c.moveToNext());
            c.close();
            return HocKyList;
        }
        if (c != null) {
            c.close();
        }
        return null;
    }
    public HocKy getHocKy(String maHocKy) {
        db = dbHelper.getReadableDatabase();
        HocKy hocKy = null;
        String[] selection = new String[]{maHocKy};
        Cursor c = db.query(DatabaseHelper.TB_HOCKY, null,
                DatabaseHelper.MA_HOCKY + "= ?",
                selection,
                null,
                null,
                null);

        if (c != null && c.moveToFirst()) {
            hocKy = new HocKy(c.getString(0), c.getString(1), c.getString(2));
            c.close();
            return hocKy;
        }
        if (c != null) {
            c.close();
        }
        return null;
    }

    public int updateHocKy(HocKy HocKy) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.MA_HOCKY, HocKy.getMaHocKy());
        values.put(DatabaseHelper.TENHOCKY, HocKy.getTenHocKy());
        values.put(DatabaseHelper.NAMHOC, HocKy.getNamHoc());

        int rowsUpdated = db.update(
                DatabaseHelper.TB_HOCKY,
                values,
                DatabaseHelper.MA_HOCKY + " = ?",
                new String[]{String.valueOf(HocKy.getMaHocKy())}
        );
        db.close();//>0 thành công =0 ko thành công
        return rowsUpdated;
    }

    public int deleteHocKy(String maHocKy) {
        db = dbHelper.getWritableDatabase();
        String selection = DatabaseHelper.MA_HOCKY + " = ?";
        String[] selectionArgs = {maHocKy};

        int rowsDeleted = db.delete(DatabaseHelper.TB_HOCKY, selection, selectionArgs);
        db.close();

        return rowsDeleted; // Trả về số dòng bị xóa, >0 nếu xóa thành công, 0 nếu không có gì bị xóa
    }
}
