package com.example.qlsinhvien.Models;

public class HocKy {
    private String maHocKy;
    private String tenHocKy;


    public HocKy(String maHocKy, String tenHocKy) {
        this.maHocKy = maHocKy;
        this.tenHocKy = tenHocKy;

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

}
