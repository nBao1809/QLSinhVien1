package com.example.qlsinhvien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qlsinhvien.Models.Nganh;

import java.util.ArrayList;
import java.util.List;

public class NganhManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private List<Nganh> nganhList;

    public NganhManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addNganh(Nganh Nganh) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.MA_NGANH, Nganh.getMaNganh());
        values.put(DatabaseHelper.TEN_NGANH, Nganh.getTenNganh());

        long rowInserted = db.insert(DatabaseHelper.TB_NGANH, null, values);
        db.close();

        return rowInserted; // >0 nếu thêm thành công, -1 nếu thất bại
    }

    public List<Nganh> getAllNganh() {
        nganhList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TB_NGANH;
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            nganhList.add(new Nganh(c.getString(0), c.getString(1)));
            c.close();
            return nganhList;
        }
        return null;
    }
    public Nganh getNganh(String maNganh) {
        db = dbHelper.getReadableDatabase();
        Nganh nganh = null;
        String[] selection = new String[]{maNganh};
        Cursor c = db.query(DatabaseHelper.TB_NGANH, null,
                DatabaseHelper.MA_NGANH +
                        "= ?",
                selection,
                null,
                null
                , null);
        if (c != null) {
            c.moveToFirst();
            nganh = new Nganh(c.getString(0), c.getString(1));
            c.close();
            return nganh;
        }
        return null;
    }
    public int updateNganh(Nganh nganh) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.MA_NGANH, nganh.getMaNganh());
        values.put(DatabaseHelper.TEN_NGANH, nganh.getMaNganh());

        int rowsUpdated = db.update(
                DatabaseHelper.TB_NGANH,
                values,
                DatabaseHelper.MA_NGANH + " = ?",
                new String[]{nganh.getMaNganh()}
        );
        db.close();//>0 thành công =0 ko thành công
        return rowsUpdated;
    }

    public int deleteNganh(String maNganh) {
        db = dbHelper.getWritableDatabase();
        String selection = DatabaseHelper.MA_NGANH + " = ?";
        String[] selectionArgs = {maNganh};

        int rowsDeleted = db.delete(DatabaseHelper.TB_NGANH, selection, selectionArgs);
        db.close();

        return rowsDeleted; // Trả về số dòng bị xóa, >0 nếu xóa thành công, 0 nếu không có gì bị xóa
    }
}
