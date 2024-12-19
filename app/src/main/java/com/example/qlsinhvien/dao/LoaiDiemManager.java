package com.example.qlsinhvien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qlsinhvien.Models.LoaiDiem;

import java.util.ArrayList;
import java.util.List;

public class LoaiDiemManager {
    private final DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private List<LoaiDiem> loaiDiemList;

    public LoaiDiemManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addLoaiDiem(LoaiDiem loaiDiem) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.MA_LOAIDIEM, loaiDiem.getMaLoaiDiem());
        values.put(DatabaseHelper.TEN_LOAIDIEM, loaiDiem.getTenLoaiDiem());
        values.put(DatabaseHelper.TRONGSO, loaiDiem.getTrongSo());

        long rowInserted = db.insert(DatabaseHelper.TB_LOAIDIEM, null, values);
        db.close();

        return rowInserted; // >0 nếu thêm thành công, -1 nếu thất bại
    }

    public List<LoaiDiem> getAllLoaiDiem() {
        loaiDiemList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TB_LOAIDIEM;
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c != null && c.moveToFirst()) {
            do {
                loaiDiemList.add(new LoaiDiem(c.getString(0), c.getString(1), c.getDouble(2)));
            } while (c.moveToNext());
            c.close();
            return loaiDiemList;
        }

        if (c != null) {
            c.close();
        }
        return null;
    }

    public LoaiDiem getLoaiDiem(String maLoaiDiem) {
        db = dbHelper.getReadableDatabase();
        LoaiDiem loaiDiem = null;
        String[] selection = new String[]{maLoaiDiem};
        Cursor c = db.query(DatabaseHelper.TB_LOAIDIEM, null,
                DatabaseHelper.MA_LOAIDIEM + "= ?",
                selection,
                null,
                null,
                null);

        if (c != null && c.moveToFirst()) {
            loaiDiem = new LoaiDiem(c.getString(0), c.getString(1), c.getDouble(2));
            c.close();
            return loaiDiem;
        }
        if (c != null) {
            c.close();
        }
        return null;
    }

    public String getNextLoaiDiemID() {
        String query = "SELECT " + DatabaseHelper.MA_LOAIDIEM +
                " FROM " + DatabaseHelper.TB_LOAIDIEM +
                " ORDER BY " + DatabaseHelper.MA_LOAIDIEM + " DESC LIMIT 1";
        Cursor cursor = null;
        String nextID = "L01";

        try {
            cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                String lastID = cursor.getString(0);
                try {

                    int number = Integer.parseInt(lastID.replace("L", ""));
                    number++;
                    nextID = String.format("L%02d", number);
                } catch (NumberFormatException e) {
                    nextID = "L01";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return nextID;
    }

    public int updateLoaiDiem(LoaiDiem loaiDiem) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.MA_LOAIDIEM, loaiDiem.getMaLoaiDiem());
        values.put(DatabaseHelper.TEN_LOAIDIEM, loaiDiem.getTenLoaiDiem());
        values.put(DatabaseHelper.TRONGSO, loaiDiem.getTrongSo());

        int rowsUpdated = db.update(
                DatabaseHelper.TB_LOAIDIEM,
                values,
                DatabaseHelper.MA_LOAIDIEM + " = ?",
                new String[]{String.valueOf(loaiDiem.getMaLoaiDiem())}
        );
        db.close();//>0 thành công =0 ko thành công
        return rowsUpdated;
    }

    public int deleteLoaiDiem(String maLoaiDiem) {
        db = dbHelper.getWritableDatabase();
        String selection = DatabaseHelper.MA_LOAIDIEM + " = ?";
        String[] selectionArgs = {maLoaiDiem};

        int rowsDeleted = db.delete(DatabaseHelper.TB_LOAIDIEM, selection, selectionArgs);
        db.close();

        return rowsDeleted; // Trả về số dòng bị xóa, >0 nếu xóa thành công, 0 nếu không có gì bị xóa
    }
}
