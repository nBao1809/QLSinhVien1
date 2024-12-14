package com.example.qlsinhvien.Models;

public class Diem {
    private int maDiem;
    private double diemSo;
    private String maLoaiDiem;
    private String maLopSinhVien;

    public Diem(int maDiem, double diemSo, String maLoaiDiem, String maLopSinhVien) {
        this.maDiem = maDiem;
        this.diemSo = diemSo;
        this.maLoaiDiem = maLoaiDiem;
        this.maLopSinhVien = maLopSinhVien;
    }
    public Diem( double diemSo, String maLoaiDiem, String maLopSinhVien) {
        this.diemSo = diemSo;
        this.maLoaiDiem = maLoaiDiem;
        this.maLopSinhVien = maLopSinhVien;
    }

    public int getMaDiem() {
        return maDiem;
    }

    public void setMaDiem(int maDiem) {
        this.maDiem = maDiem;
    }

    public double getDiemSo() {
        return diemSo;
    }

    public void setDiemSo(double diemSo) {
        this.diemSo = diemSo;
    }

    public String getMaLoaiDiem() {
        return maLoaiDiem;
    }

    public void setMaLoaiDiem(String maLoaiDiem) {
        this.maLoaiDiem = maLoaiDiem;
    }

    public String getMaLopSinhVien() {
        return maLopSinhVien;
    }

    public void setMaLopSinhVien(String maLopSinhVien) {
        this.maLopSinhVien = maLopSinhVien;
    }

}
