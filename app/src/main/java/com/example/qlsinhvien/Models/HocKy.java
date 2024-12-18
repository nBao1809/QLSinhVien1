package com.example.qlsinhvien.Models;

public class HocKy {
    private String maHocKy;
    private String tenHocKy;
    private String namHoc;

    public HocKy(String maHocKy, String tenHocKy, String namHoc) {
        this.maHocKy = maHocKy;
        this.tenHocKy = tenHocKy;
        this.namHoc = namHoc;
    }

    // Getter và Setter
    public String getMaHocKy() {
        return maHocKy;
    }

    public void setMaHocKy(String maHocKy) {
        this.maHocKy = maHocKy;
    }

    public String getTenHocKy() {
        return tenHocKy;
    }

    public void setTenHocKy(String tenHocKy) {
        this.tenHocKy = tenHocKy;
    }

    public String getNamHoc() {
        return namHoc;
    }

    public void setNamHoc(String namHoc) {
        this.namHoc = namHoc;
    }
}
