package com.example.programinglearningapp.model;

import java.util.List;

public class User {
    private int id;             // ID của người dùng
    private String username;    // Tên người dùng
    private String password;    // Mật khẩu
    private String email;       // Email
    private String dob;         // Ngày sinh
    private int role;           // Vai trò

    // Constructor
    public User(int id, String username, String password, String email, String dob, int role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.role = role;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}


