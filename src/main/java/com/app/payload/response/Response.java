package com.app.payload.response;

import lombok.Data;

@Data
public class Response {
    private String response;

    public Response() {
    }

    public Response(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
