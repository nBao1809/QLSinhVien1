package com.example.qlsinhvien.Models;

public class SinhVien {
    private String mssv;
    private String hoTen;
    private String cccd;
    private double ngaySinh;
    private int id;
    private String maLopHanhChinh;
    private String maNganh;


    public SinhVien(String mssv, String hoTen, String cccd, double ngaySinh, int id,
                    String maLopHanhChinh, String maNganh) {
        this.mssv = mssv;
        this.hoTen = hoTen;
        this.cccd = cccd;
        this.ngaySinh = ngaySinh;
        this.id = id;
        this.maLopHanhChinh = maLopHanhChinh;
        this.maNganh = maNganh;
    }


    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public double getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(double ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaLopHanhChinh() {
        return maLopHanhChinh;
    }

    public void setMaLopHanhChinh(String maLopHanhChinh) {
        this.maLopHanhChinh = maLopHanhChinh;
    }

    public String getMaNganh() {
        return maNganh;
    }

    public void setMaNganh(String maNganh) {
        this.maNganh = maNganh;
    }
}
