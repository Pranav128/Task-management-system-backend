package com.app.payload.response;

import java.time.LocalDate;

public class UserResponseDto {
    private Long id;
    private String username, email, gender;
    private  LocalDate dob;
    private String avatar;

    public UserResponseDto(Long id, String username, String email, String gender, LocalDate dob, String avatar) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}
