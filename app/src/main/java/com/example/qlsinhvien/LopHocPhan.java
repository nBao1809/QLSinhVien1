package com.example.qlsinhvien;

public class LopHocPhan {
    private String tenNganh;
    private String tenMonHoc;
    private String tenLop;

    public LopHocPhan(String tenNganh, String tenMonHoc, String tenLop, String tenGiaoVienPhuTrach) {
        this.tenNganh = tenNganh;
        this.tenMonHoc = tenMonHoc;
        this.tenLop = tenLop;
        this.tenGiaoVienPhuTrach = tenGiaoVienPhuTrach;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    private String tenGiaoVienPhuTrach;

    public String getTenNganh() {
        return tenNganh;
    }

    public void setTenNganh(String tenNganh) {
        this.tenNganh = tenNganh;
    }

    public String getTenMonHoc() {
        return tenMonHoc;
    }

    public void setTenMonHoc(String tenMonHoc) {
        this.tenMonHoc = tenMonHoc;
    }

    public String getTenGiaoVienPhuTrach() {
        return tenGiaoVienPhuTrach;
    }

    public void setTenGiaoVienPhuTrach(String tenGiaoVienPhuTrach) {
        this.tenGiaoVienPhuTrach = tenGiaoVienPhuTrach;
    }



}
