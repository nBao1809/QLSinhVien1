package com.example.qlsinhvien.Models;

public class Role {
    public Role(String maRole, String tenRole) {
        this.maRole = maRole;
        this.tenRole = tenRole;
    }

    public String getMaRole() {
        return maRole;
    }

    public void setMaRole(String maRole) {
        this.maRole = maRole;
    }

    public String getTenRole() {
        return tenRole;
    }

    public void setTenRole(String tenRole) {
        this.tenRole = tenRole;
    }

    private String maRole;
    private String tenRole;
}
