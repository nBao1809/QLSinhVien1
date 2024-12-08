package com.example.qlsinhvien.Models;

public class LopHocPhan {
    private String maLop, tenLop, maMonHoc, maGiangVienPhuTrach;
    private double ngayBatDau, ngayKetThuc;

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public double getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(double ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public double getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(double ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getMaMonHoc() {
        return maMonHoc;
    }

    public void setMaMonHoc(String maMonHoc) {
        this.maMonHoc = maMonHoc;
    }

    public String getMaGiangVienPhuTrach() {
        return maGiangVienPhuTrach;
    }

    public void setMaGiangVienPhuTrach(String maGiangVienPhuTrach) {
        this.maGiangVienPhuTrach = maGiangVienPhuTrach;
    }

    public LopHocPhan(String maLop, String tenLop, double ngayBatDau, double ngayKetThuc,
                      String maMonHoc, String maGiangVienPhuTrach) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.maMonHoc = maMonHoc;
        this.maGiangVienPhuTrach = maGiangVienPhuTrach;
    }

}
