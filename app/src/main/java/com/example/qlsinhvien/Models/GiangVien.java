package com.example.qlsinhvien.Models;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Objects;

public class GiangVien implements Serializable {
    private String maGiangVien;
    private String hoTen;
    private String cccd;
    private double ngaySinh;
    private String khoa;
    private int id;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiangVien giangVien = (GiangVien) o;
        return maGiangVien.equals(giangVien.maGiangVien);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maGiangVien);
    }


    public GiangVien(String maGiangVien, String hoTen, String cccd, double ngaySinh, String khoa, int id) {
        this.maGiangVien = maGiangVien;
        this.hoTen = hoTen;
        this.cccd = cccd;
        this.ngaySinh = ngaySinh;
        this.khoa = khoa;
        this.id = id;
    }

    // Getter và Setter
    public String getMaGiangVien() {
        return maGiangVien;
    }

    public void setMaGiangVien(String maGiangVien) {
        this.maGiangVien = maGiangVien;
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

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
