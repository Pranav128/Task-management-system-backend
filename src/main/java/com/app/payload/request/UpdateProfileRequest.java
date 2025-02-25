package com.app.payload.request;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String currentPassword;
    private String newPassword;
    private String avatar;

    public UpdateProfileRequest(String currentPassword, String newPassword, String avatar) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.avatar = avatar;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
