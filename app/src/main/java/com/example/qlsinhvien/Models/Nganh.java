package com.example.qlsinhvien.Models;

public class Nganh {
    private String maNganh;
    private String tenNganh;

    public Nganh(String maNganh, String tenNganh) {
        this.maNganh = maNganh;
        this.tenNganh = tenNganh;
    }

    public String getMaNganh() {
        return maNganh;
    }

    public void setMaNganh(String maNganh) {
        this.maNganh = maNganh;
    }

    public String getTenNganh() {
        return tenNganh;
    }

    public void setTenNganh(String nganh) {
        this.tenNganh = nganh;
    }
}

