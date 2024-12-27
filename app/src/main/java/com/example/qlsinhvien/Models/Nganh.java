package com.example.qlsinhvien.Models;

import java.util.Objects;

public class Nganh {
    private String maNganh;
    private String tenNganh;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nganh nganh = (Nganh) o;
        return maNganh.equals(nganh.maNganh);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNganh);
    }

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

