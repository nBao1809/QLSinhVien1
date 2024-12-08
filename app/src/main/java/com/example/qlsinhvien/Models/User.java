package com.example.qlsinhvien.Models;

import android.graphics.Bitmap;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private int ID;
    private String username;
    private String password;
    private Bitmap photo;
    private String email;
    private String role;

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public Bitmap getPhoto() {
        return photo;
    }
    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    private void setRole(String role) {
        this.role = role;
    }

    public User(int ID, String username, String password, Bitmap photo,String email, String role) {
        this.ID=ID;
        this.username = username;
        this.password = hashPassword(password);
        this.photo=photo;
        this.email = email;
        this.role = role;
    }
    public User(String username, String password, Bitmap photo, String email, String role) {
        this.username = username;
        this.password = hashPassword(password);
        this.photo=photo;
        this.email = email;
        this.role = role;
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
