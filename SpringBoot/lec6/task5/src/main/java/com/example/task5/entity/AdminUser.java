package com.example.task5.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin_users")
public class AdminUser extends User {

    private String adminLevel;

    public AdminUser() {
    }

    public AdminUser(String name, Integer age, String adminLevel) {
        super(name, age);
        this.adminLevel = adminLevel;
    }

    public String getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }
}
