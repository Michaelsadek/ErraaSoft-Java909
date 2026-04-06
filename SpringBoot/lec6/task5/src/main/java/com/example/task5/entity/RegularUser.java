package com.example.task5.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "regular_users")
public class RegularUser extends User {

    private String membershipType;

    public RegularUser() {
    }

    public RegularUser(String name, Integer age, String membershipType) {
        super(name, age);
        this.membershipType = membershipType;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }
}
