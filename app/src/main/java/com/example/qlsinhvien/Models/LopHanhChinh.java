package com.example.qlsinhvien.Models;

public class LopHanhChinh {
    private String maLopHanhChinh;
    private String tenLopHanhChinh;


    public LopHanhChinh(String maLopHanhChinh, String tenLopHanhChinh) {
        this.maLopHanhChinh = maLopHanhChinh;
        this.tenLopHanhChinh = tenLopHanhChinh;
    }

    public String getMaLopHanhChinh() {
        return maLopHanhChinh;
    }

    public void setMaLopHanhChinh(String maLopHanhChinh) {
        this.maLopHanhChinh = maLopHanhChinh;
    }

    public String getTenLopHanhChinh() {
        return tenLopHanhChinh;
    }

    public void setTenLopHanhChinh(String tenLopHanhChinh) {
        this.tenLopHanhChinh = tenLopHanhChinh;
    }

}
