package com.example.qlsinhvien.Models;

public class Diem {
    private int maDiem;
    private double diemSo;
    private String maLoaiDiem;
    private int id;

    public Diem(int maDiem, double diemSo, String maLoaiDiem, int id) {
        this.maDiem = maDiem;
        this.diemSo = diemSo;
        this.maLoaiDiem = maLoaiDiem;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
