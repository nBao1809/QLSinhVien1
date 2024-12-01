package com.example.qlsinhvien.dao;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qlsinhvien.Models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;


import javax.mail.*;
import javax.mail.internet.*;

public class UserManager {
    private final DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private List<User> userList;
    SharedPreferences userRefs, otpRefs;
    SharedPreferences.Editor currentUsereditor, otpEditor;

    public UserManager(Context context) {
        dbHelper = new DatabaseHelper(context);
        userRefs = context.getSharedPreferences("currentUser",
                MODE_PRIVATE);
        currentUsereditor = userRefs.edit();
        otpRefs = context.getSharedPreferences("OTP",
                MODE_PRIVATE);
        otpEditor = otpRefs.edit();
    }

    public long addUser(User user) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.USERNAME, user.getUsername());
        values.put(DatabaseHelper.PASSWORD, user.getPassword());
        values.put(DatabaseHelper.PHOTO, user.getPhoto());
        values.put(DatabaseHelper.EMAIL, user.getEmail());
        values.put(DatabaseHelper.ROLE, user.getRole());

        long rowInserted = db.insert(DatabaseHelper.TB_USERS, null, values);
        db.close();

        return rowInserted; // >0 nếu thêm thành công, -1 nếu thất bại
    }

    public List<User> getAllUser() {
        userList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TB_USERS + "ORDER BY" + DatabaseHelper.ROLE;
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
    public User getUser(int maUser) {
        userList = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        User user = null;
        String[] selection = new String[]{String.valueOf(maUser)};
        Cursor c = db.query(DatabaseHelper.TB_USERS, null,
                DatabaseHelper.ID +
                        "= ?",
                selection,
                null,
                null
                , null);
        if (c != null) {
            c.moveToFirst();
            user = new User(c.getInt(0), c.getString(1), c.getString(2), c.getString(3),
                    c.getString(4), c.getString(5));
            c.close();
            return user;
        }
        return null;
    }
    public int updateUser(User user) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.USERNAME, user.getUsername());
        values.put(DatabaseHelper.PASSWORD, user.getPassword());
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
    public int deleteUser(long userID) {
        db = dbHelper.getWritableDatabase();
        String selection = DatabaseHelper.ID + " = ?";
        String[] selectionArgs = { String.valueOf(userID) };

        int rowsDeleted = db.delete(DatabaseHelper.TB_USERS, selection, selectionArgs);
        db.close();

        return rowsDeleted; // Trả về số dòng bị xóa, >0 nếu xóa thành công, 0 nếu không có gì bị xóa
    }

    public User checkLogin(String username, String password) {
        db = dbHelper.getReadableDatabase();
        User user = null;
        String hashPassWord = User.hashPassword(password);
        String[] selection = new String[]{username, hashPassWord};
        Cursor c = db.query(DatabaseHelper.TB_USERS, null, DatabaseHelper.USERNAME + "= ? AND " +
                        DatabaseHelper.PASSWORD + " = ? ",
                selection,
                null, null
                , null);
        if (c != null) {
            c.moveToFirst();
            user = new User(c.getInt(0), c.getString(1), c.getString(2),
                    c.getString(3), c.getString(4), c.getString(5));
            currentUsereditor.putInt("ID", c.getInt(0));
            currentUsereditor.putString("Username", c.getString(1));
            currentUsereditor.putString("Password", c.getString(2));
            currentUsereditor.putString("Photo", c.getString(3));
            currentUsereditor.putString("Email", c.getString(4));
            currentUsereditor.putString("Role", c.getString(5));
            currentUsereditor.apply();
            c.close();
            return user;
        }
        return null;
    }

    public void sendEmail(String toEmail) {
        String fromEmail="qlsinhvien0@gmail.com";
        String password="zonb hknp gsxw autw";
        /*
        mail: qlsinhvien0@gmail.com
        app-pass mail:zonb hknp gsxw autw
        pass: qlsinhvien123
        */

        String otp = generateOTP();
        otpEditor.putString("otp", otp);
        otpEditor.putLong("otp_time", System.currentTimeMillis());

        String messageBody = "Mã OTP đổi mật khẩu: " + otp;
        String subject = "Đổi mật khẩu";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(messageBody);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public boolean changePassword(String password, String inputOTP) {
        long currentTime = System.currentTimeMillis();
        String otp = otpRefs.getString("otp","");
        long otpTime = otpRefs.getLong("otp_time", 0);
        long timeElapsed = currentTime - otpTime;
        if (timeElapsed <= 300000) {
            if (otp.equals(inputOTP)){
                // đổi pass o day
            }else {
//                nhap sai otp
            }
        }
        return false;
    }
}
