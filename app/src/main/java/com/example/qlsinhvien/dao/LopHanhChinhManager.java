package com.example.qlsinhvien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.qlsinhvien.Models.LopHanhChinh;

import java.util.ArrayList;
import java.util.List;

public class LopHanhChinhManager {
    private final DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private List<LopHanhChinh> lopHanhChinhList;


    public LopHanhChinhManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addLopHanhChinh(LopHanhChinh lopHanhChinh) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.MA_LOPHANHCHINH, lopHanhChinh.getMaLopHanhChinh());
        values.put(DatabaseHelper.TEN_LOPHANHCHINH, lopHanhChinh.getTenLopHanhChinh());

        long rowInserted = db.insert(DatabaseHelper.TB_LOPHANHCHINH, null, values);
        db.close();

        return rowInserted; // >0 nếu thêm thành công, -1 nếu thất bại
    }

    public List<LopHanhChinh> getAllLopHanhChinh() {
        lopHanhChinhList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TB_LOPHANHCHINH;
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            lopHanhChinhList.add(new LopHanhChinh(c.getString(0), c.getString(1)));
            c.close();
            return lopHanhChinhList;
        }
        return null;
    }
    public LopHanhChinh getLopHanhChinh(String maLopHanhChinh) {
        db = dbHelper.getReadableDatabase();
        LopHanhChinh lopHanhChinh = null;
        String[] selection = new String[]{maLopHanhChinh};
        Cursor c = db.query(DatabaseHelper.TB_LOPHANHCHINH, null,
                DatabaseHelper.MA_LOPHANHCHINH +
                        "= ?",
                selection,
                null,
                null
                , null);
        if (c != null) {
            c.moveToFirst();
            lopHanhChinh = new LopHanhChinh(c.getString(0), c.getString(1));
            c.close();
            return lopHanhChinh;
        }
        return null;
    }
    public int updateLopHanhChinh(LopHanhChinh lopHanhChinh) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.MA_LOPHANHCHINH, lopHanhChinh.getMaLopHanhChinh());
        values.put(DatabaseHelper.TEN_LOPHANHCHINH, lopHanhChinh.getMaLopHanhChinh());

        int rowsUpdated = db.update(
                DatabaseHelper.TB_LOPHANHCHINH,
                values,
                DatabaseHelper.MA_LOPHANHCHINH + " = ?",
                new String[]{lopHanhChinh.getMaLopHanhChinh()}
        );
        db.close();//>0 thành công =0 ko thành công
        return rowsUpdated;
    }

    public int deleteLopHanhChinh(String maLopHanhChinh) {
        db = dbHelper.getWritableDatabase();
        String selection = DatabaseHelper.MA_LOPHANHCHINH + " = ?";
        String[] selectionArgs = {maLopHanhChinh};

        int rowsDeleted = db.delete(DatabaseHelper.TB_LOPHANHCHINH, selection, selectionArgs);
        db.close();

        return rowsDeleted; // Trả về số dòng bị xóa, >0 nếu xóa thành công, 0 nếu không có gì bị xóa
    }
}
