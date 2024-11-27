package com.example.qlsinhvien.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private List<User> userList;

    public UserManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public List<User> getAllUser() {
        userList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TB_USERS;
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            userList.add(new User(c.getInt(0), c.getString(1), c.getString(2),
                    c.getString(3), c.getString(4), c.getString(5)));
            c.close();
            return userList;
        }
        return null;
    }

    public List<User> getUserByUsername(String username, String Role) {
        userList = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        String[] selection = new String[]{username, Role};
        Cursor c = db.query(DatabaseHelper.TB_USERS, null, "USERNAME = ? AND ROLE= ?",
                selection,
                Role,
                null
                , null);
        if (c != null) {
            c.moveToFirst();
            userList.add(new User(c.getInt(0), c.getString(1), c.getString(2),
                    c.getString(3), c.getString(4), c.getString(5)));
            c.close();
            return userList;
        }
        return null;
    }

    public void insertUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.ID, user.getID());
        values.put(DatabaseHelper.USERNAME, user.getUsername());
        values.put(DatabaseHelper.PHOTO, user.getPhoto());
        values.put(DatabaseHelper.EMAIL, user.getEmail());
        values.put(DatabaseHelper.ROLE, user.getRole());

        db.insert(DatabaseHelper.TB_USERS, null, values);
        db.close();
    }

    public int updateUser(User user) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.ID, user.getID());
        values.put(DatabaseHelper.USERNAME, user.getUsername());
        values.put(DatabaseHelper.PHOTO, user.getPhoto());
        values.put(DatabaseHelper.EMAIL, user.getEmail());
        values.put(DatabaseHelper.ROLE, user.getRole());

        int rowsUpdated = db.update(
                DatabaseHelper.TB_USERS,
                values,
                DatabaseHelper.ID + " = ?",
                new String[]{String.valueOf(user.getID())}
        );
        db.close();//>0 thành công =0 ko thành công
        return rowsUpdated;
    }

    public User checkLogin(String username, String password) {
        db = dbHelper.getReadableDatabase();
        User user = null;
        String[] selection = new String[]{username, password};
        Cursor c = db.query(DatabaseHelper.TB_USERS, null, "USERNAME = ? AND PASSWORD = ? ",
                selection,
                null, null
                , null);
        if (c != null) {
            c.moveToFirst();
            user = new User(c.getInt(0), c.getString(1), c.getString(2),
                    c.getString(3), c.getString(4), c.getString(5));
            c.close();
            return user;
        }
        return null;
    }
}
