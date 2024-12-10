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
    SharedPreferences otpRefs;
    SharedPreferences.Editor otpEditor;

    public UserManager(Context context) {
        dbHelper = new DatabaseHelper(context);
        otpRefs = context.getSharedPreferences("OTP",
                MODE_PRIVATE);
        otpEditor = otpRefs.edit();

    }

    public long addUser(User user) {
        User isUserExits = getUserByUserName(user.getUsername());
        if (isUserExits != null) {
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
                        image, c.getString(4), c.getString(5)));
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
            byte[] imageBytes = getBitmapAsByteArray(resizeImage(user.getPhoto()));
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
        if (c != null && c.moveToFirst()) {
            Bitmap image = BitmapFactory.decodeByteArray(c.getBlob(3), 0, c.getBlob(3).length);
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


    public void sendEmail(String toEmail,String OTP) {
        String fromEmail = "qlsinhvien0@gmail.com";
        String password = "zonb hknp gsxw autw";
        /*
        mail: qlsinhvien0@gmail.com
        app-pass mail:zonb hknp gsxw autw
        pass: qlsinhvien123
        */
        String otp = OTP;
        String body = "<!DOCTYPE html>\n" +
                "<html lang=\"vi\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Xác thực thay đổi mật khẩu</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f4f4f9;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        .email-container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .email-header {\n" +
                "            text-align: center;\n" +
                "            background-color: #4CAF50;\n" +
                "            color: #ffffff;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 8px;\n" +
                "        }\n" +
                "        .email-header h1 {\n" +
                "            margin: 0;\n" +
                "        }\n" +
                "        .email-body {\n" +
                "            margin-top: 20px;\n" +
                "            font-size: 16px;\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        .email-body p {\n" +
                "            line-height: 1.6;\n" +
                "        }\n" +
                "        .otp-code {\n" +
                "            font-size: 24px;\n" +
                "            font-weight: bold;\n" +
                "            color: #4CAF50;\n" +
                "            margin-top: 10px;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            margin-top: 30px;\n" +
                "            text-align: center;\n" +
                "            font-size: 14px;\n" +
                "            color: #777;\n" +
                "        }\n" +
                "        .footer a {\n" +
                "            color: #4CAF50;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div class=\"email-container\">\n" +
                "    <div class=\"email-header\">\n" +
                "        <h1>Xác nhận thay đổi mật khẩu</h1>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"email-body\">\n" +
                "        <p>Chào bạn,</p>\n" +
                "        <p>Chúng tôi nhận được yêu cầu thay đổi mật khẩu từ tài khoản của bạn. Để bảo mật tài khoản của bạn, vui lòng nhập mã OTP dưới đây vào ứng dụng của chúng tôi để xác nhận thay đổi mật khẩu:</p>\n" +
                "\n" +
                "        <div class=\"otp-code\">\n" +
                "            Mã OTP của bạn: <strong>" + otp + "</strong>\n" +
                "        </div>\n" +
                "\n" +
                "        <p>Mã OTP này có hiệu lực trong vòng 10 phút. Nếu bạn không yêu cầu thay đổi mật khẩu, vui lòng bỏ qua email này.</p>\n" +
                "        <p>Trân trọng,</p>\n" +
                "        <p><strong>Đội ngũ hỗ trợ</strong><br/>Ứng dụng XYZ</p>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"footer\">\n" +
                "        <p>Nếu bạn gặp vấn đề, vui lòng liên hệ với chúng tôi qua <a href=\"mailto:support@xyz.com\">support@xyz.com</a>.</p>\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";
        String messageBody = "Mã OTP đổi mật khẩu: " + otp;
        String subject = "Xác thực yêu cầu thay đổi mật khẩu của bạn";
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
                    message.setContent(body, "text/html;charset=UTF-8");

                    // Gửi email
                    Transport.send(message);

                } catch (MessagingException e) {

                }
            }
        });
    }

    public static String generateOTP( ) {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public boolean changePassword(String password, User user) {

        user.setPassword(password);
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.PASSWORD, user.getPassword());
        int rowUpdated = db.update(DatabaseHelper.TB_USERS, values, "ID= ?", new String[]{
                String.valueOf(user.getID())
        });
        return rowUpdated > 0;
//            }
//            else {
//            nhap sai otp
//            }

    }

    private Bitmap resizeImage(Bitmap originalBitmap) {
        int maxWidth, maxHeight;
        maxWidth = maxHeight = 800;
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
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
        return outputStream.toByteArray();
    }
}

