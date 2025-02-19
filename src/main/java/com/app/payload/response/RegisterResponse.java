package com.app.payload.response;

public class RegisterResponse {
    private String resp;
    public RegisterResponse(String resp) {
        this.resp = resp;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }
}
