package com.example.qlsinhvien.Models;

public class MonHoc {
    private String maMonHoc;
    private String tenMonHoc;
    private double tinChi;
    private String maNganh;

    public MonHoc(String maMonHoc, String tenMonHoc, double tinChi, String maNganh) {
        this.maMonHoc = maMonHoc;
        this.tenMonHoc = tenMonHoc;
        this.tinChi = tinChi;
        this.maNganh = maNganh;
    }

    public String getMaMonHoc() {
        return maMonHoc;
    }

    public void setMaMonHoc(String maMonHoc) {
        this.maMonHoc = maMonHoc;
    }

    public String getTenMonHoc() {
        return tenMonHoc;
    }

    public void setTenMonHoc(String tenMonHoc) {
        this.tenMonHoc = tenMonHoc;
    }

    public double getTinChi() {
        return tinChi;
    }

    public void setTinChi(double tinChi) {
        this.tinChi = tinChi;
    }

    public String getMaNganh() {
        return maNganh;
    }

    public void setMaNganh(String maNganh) {
        this.maNganh = maNganh;
    }
}

