package com.example.qlsinhvien.Models;

public class LoaiDiem {
    private String maLoaiDiem;
    private String tenLoaiDiem;
    private double trongSo;

    public LoaiDiem(String maLoaiDiem, String tenLoaiDiem, double trongSo) {
        this.maLoaiDiem = maLoaiDiem;
        this.tenLoaiDiem = tenLoaiDiem;
        this.trongSo = trongSo;
    }

    public String getMaLoaiDiem() {
        return maLoaiDiem;
    }

    public void setMaLoaiDiem(String maLoaiDiem) {
        this.maLoaiDiem = maLoaiDiem;
    }

    public String getTenLoaiDiem() {
        return tenLoaiDiem;
    }

    public void setTenLoaiDiem(String tenLoaiDiem) {
        this.tenLoaiDiem = tenLoaiDiem;
    }

    public double getTrongSo() {
        return trongSo;
    }

    public void setTrongSo(double trongSo) {
        this.trongSo = trongSo;
    }
}
