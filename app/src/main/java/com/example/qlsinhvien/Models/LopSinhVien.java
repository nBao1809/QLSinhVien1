package com.example.qlsinhvien.Models;

public class LopSinhVien {
    private String maLopSinhVien;
    private String maLop;
    private String mssv;

    private String maHocKy;

    public LopSinhVien(String maLopSinhVien, String maLop, String mssv, String maHocKy) {
        this.maLopSinhVien = maLopSinhVien;
        this.maLop = maLop;
        this.mssv = mssv;
        this.maHocKy = maHocKy;
    }

    public String getMaLopSinhVien() {
        return maLopSinhVien;
    }

    public void setMaLopSinhVien(String maLopSinhVien) {
        this.maLopSinhVien = maLopSinhVien;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getMaHocKy() {
        return maHocKy;
    }

    public void setMaHocKy(String maHocKy) {
        this.maHocKy = maHocKy;
    }
}
