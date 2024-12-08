package com.example.qlsinhvien.dao;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.qlsinhvien.Models.User;


import java.io.ByteArrayOutputStream;
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
    SharedPreferences  otpRefs;
    SharedPreferences.Editor otpEditor;

    public UserManager(Context context) {
        dbHelper = new DatabaseHelper(context);
        otpRefs = context.getSharedPreferences("OTP",
                MODE_PRIVATE);
        otpEditor = otpRefs.edit();
    }

    public long addUser(User user) {
        User isUserExits= getUserByUserName(user.getUsername());
        if (isUserExits!=null){
            return -1;
        }
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        byte[] imageBytes = getBitmapAsByteArray(resizeImage(user.getPhoto()));
        values.put(DatabaseHelper.USERNAME, user.getUsername());
        values.put(DatabaseHelper.PASSWORD, user.getPassword());
        values.put(DatabaseHelper.PHOTO, imageBytes);
        values.put(DatabaseHelper.EMAIL, user.getEmail());
        values.put(DatabaseHelper.ROLE, user.getRole());

        long rowInserted = db.insert(DatabaseHelper.TB_USERS, null, values);
        db.close();

        return rowInserted; // >0 nếu thêm thành công, -1 nếu thất bại
    }

    public List<User> getAllUser() {
        userList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TB_USERS + " ORDER BY " + DatabaseHelper.ROLE;
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null && c.moveToFirst()) {
            do {
                byte[] imageBytes = c.getBlob(3);
                Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                userList.add(new User(c.getInt(0), c.getString(1), c.getString(2),
                       image , c.getString(4), c.getString(5)));
            } while (c.moveToNext());
            c.close();
            return userList;
        }
        if (c != null) {
            c.close();
        }
        return null;
    }

    public User getUserByID(int maUser) {
        db = dbHelper.getReadableDatabase();
        User user = null;
        String[] selection = new String[]{String.valueOf(maUser)};
        Cursor c = db.query(DatabaseHelper.TB_USERS, null,
                DatabaseHelper.ID + "= ?",
                selection, null, null, null);

        if (c != null && c.moveToFirst()) {
            byte[] imageBytes = c.getBlob(3);
            Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            user = new User(c.getInt(0), c.getString(1), c.getString(2), image,
                    c.getString(4), c.getString(5));
            c.close();
            return user;
        }
        if (c != null) {
            c.close();
        }
        return null;
    }
    public User getUserByUserName(String username) {
        db = dbHelper.getReadableDatabase();
        User user = null;
        String[] selection = new String[]{String.valueOf(username)};
        Cursor c = db.query(DatabaseHelper.TB_USERS, null,
                DatabaseHelper.USERNAME + "= ?",
                selection, null, null, null);

        if (c != null && c.moveToFirst()) {
            byte[] imageBytes = c.getBlob(3);
            Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            user = new User(c.getInt(0), c.getString(1), c.getString(2), image,
                    c.getString(4), c.getString(5));
            c.close();
            return user;
        }
        if (c != null) {
            c.close();
        }
        return null;
    }

    public int updateUser(User user) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (user.getUsername() != null) {
            values.put(DatabaseHelper.USERNAME, user.getUsername());
        }
        if (user.getPassword() != null) {
            values.put(DatabaseHelper.PASSWORD, user.getPassword());
        }
        if (user.getEmail() != null) {
            values.put(DatabaseHelper.EMAIL, user.getEmail());
        }
        if (user.getPhoto() != null) {
            byte[] imageBytes=getBitmapAsByteArray(resizeImage( user.getPhoto()));
            values.put(DatabaseHelper.PHOTO, imageBytes);
        }
        if (user.getRole() != null) {
            values.put(DatabaseHelper.ROLE, user.getRole());
        }

        int rowsUpdated = db.update(
                DatabaseHelper.TB_USERS,
                values,
                DatabaseHelper.ID + " = ?",
                new String[]{String.valueOf(user.getID())}
        );
        db.close();//>0 thành công =0 ko thành công
        return rowsUpdated;
    }

    public int updatePhoto(int ID,Bitmap photo) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (photo != null) {
            byte[]imageBytes=getBitmapAsByteArray(resizeImage(photo));
            values.put(DatabaseHelper.PHOTO, imageBytes);
        }

        int rowsUpdated = db.update(
                DatabaseHelper.TB_USERS,
                values,
                DatabaseHelper.ID + " = ?",
                new String[]{String.valueOf(ID)}
        );
        db.close();//>0 thành công =0 ko thành công
        return rowsUpdated;
    }

    public int deleteUser(long userID) {
        db = dbHelper.getWritableDatabase();
        String selection = DatabaseHelper.ID + " = ?";
        String[] selectionArgs = {String.valueOf(userID)};

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
        if (c != null&&c.moveToFirst()) {
            Bitmap image= BitmapFactory.decodeByteArray(c.getBlob(3),0,c.getBlob(3).length);
            user = new User(c.getInt(0), c.getString(1), c.getString(2),
                    image, c.getString(4), c.getString(5));

            c.close();
            return user;
        }
        if (c != null) {
            c.close();
        }
        return null;
    }





    public void sendEmail(final String toEmail) {
        String fromEmail = "qlsinhvien0@gmail.com";
        String password = "zonb hknp gsxw autw";
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
        // Tạo một AsyncTask để gửi email không làm treo UI thread
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // Thiết lập các thuộc tính kết nối
                    Properties properties = new Properties();
                    properties.put("mail.smtp.host", "smtp.gmail.com");
                    properties.put("mail.smtp.port", "587");
                    properties.put("mail.smtp.auth", "true");
                    properties.put("mail.smtp.starttls.enable", "true");

                    // Khởi tạo Session với thông tin xác thực
                    Session session = Session.getInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(fromEmail, password);
                        }
                    });

                    // Tạo thông điệp email
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(fromEmail));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
                    message.setSubject(subject);
                    message.setText(messageBody);

                    // Gửi email
                    Transport.send(message);

                } catch (MessagingException e) {

                }
            }
        });
    }
    public static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);

    }

    public boolean changePassword(String password, String inputOTP) {
        long currentTime = System.currentTimeMillis();
        String otp = otpRefs.getString("otp", "");
        long otpTime = otpRefs.getLong("otp_time", 0);
        long timeElapsed = currentTime - otpTime;
        if (timeElapsed <= 300000) {
            if (otp.equals(inputOTP)) {
                // đổi pass o day
            } else {
//                nhap sai otp
            }
        }
        return false;
    }
    private Bitmap resizeImage(Bitmap originalBitmap) {
        int maxWidth,maxHeight;
        maxWidth=maxHeight=800;
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();

        // Tính tỷ lệ giảm kích thước ảnh
        float ratioBitmap = (float) width / height;
        float ratioMax = (float) maxWidth / maxHeight;

        int finalWidth = maxWidth;
        int finalHeight = maxHeight;

        // Chọn tỷ lệ để giữ tỉ lệ ảnh đúng
        if (ratioMax > ratioBitmap) {
            finalWidth = (int) (maxHeight * ratioBitmap);
        } else {
            finalHeight = (int) (maxWidth / ratioBitmap);
        }

        // Resize ảnh
        return Bitmap.createScaledBitmap(originalBitmap, finalWidth, finalHeight, false);
    }

    public byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }
}

