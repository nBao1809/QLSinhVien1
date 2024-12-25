package com.example.qlsinhvien.Models;

import java.util.Objects;

public class LopHanhChinh {
    private String maLopHanhChinh;
    private String tenLopHanhChinh;


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LopHanhChinh lopHanhChinh= (LopHanhChinh) o;
        return maLopHanhChinh.equals(lopHanhChinh.maLopHanhChinh);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maLopHanhChinh);
    }

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
