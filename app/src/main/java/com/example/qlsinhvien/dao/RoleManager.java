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

}
