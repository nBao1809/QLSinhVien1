package com.example.qlsinhvien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.qlsinhvien.Models.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleManager {

    private final DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private List<Role> RoleList;

    public RoleManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addRole(Role role) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.MA_ROLE, role.getMaRole());
        values.put(DatabaseHelper.TEN_ROLE, role.getTenRole());

        long rowInserted = db.insert(DatabaseHelper.TB_ROLE, null, values);
        db.close();

        return rowInserted; // >0 nếu thêm thành công, -1 nếu thất bại
    }

    public List<Role> getAllRole() {
        RoleList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TB_ROLE;
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c != null && c.moveToFirst()) {
            do {
                RoleList.add(new Role(c.getString(0), c.getString(1)));
            } while (c.moveToNext());
            c.close();
            return RoleList;
        }
        if (c != null) {
            c.close();
        }
        return null;
    }
    public Role getRole(String maRole) {
        db = dbHelper.getReadableDatabase();
        Role role = null;
        String[] selection = new String[]{maRole};
        Cursor c = db.query(DatabaseHelper.TB_ROLE, null,
                DatabaseHelper.MA_ROLE + "= ?",
                selection,
                null,
                null,
                null);

        if (c != null && c.moveToFirst()) {
            role = new Role(c.getString(0), c.getString(1));
            c.close();
            return role;
        }
        if (c != null) {
            c.close();
        }
        return null;
    }

    public int updateHocKy(Role role) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.MA_ROLE, role.getMaRole());
        values.put(DatabaseHelper.TENHOCKY, role.getTenRole());

        int rowsUpdated = db.update(
                DatabaseHelper.TB_ROLE,
                values,
                DatabaseHelper.MA_ROLE + " = ?",
                new String[]{String.valueOf(role.getMaRole())}
        );
        db.close();//>0 thành công =0 ko thành công
        return rowsUpdated;
    }

    public int deleteHocKy(String maRole) {
        db = dbHelper.getWritableDatabase();
        String selection = DatabaseHelper.MA_ROLE + " = ?";
        String[] selectionArgs = {maRole};

        int rowsDeleted = db.delete(DatabaseHelper.TB_ROLE, selection, selectionArgs);
        db.close();

        return rowsDeleted; // Trả về số dòng bị xóa, >0 nếu xóa thành công, 0 nếu không có gì bị xóa
    }
}
