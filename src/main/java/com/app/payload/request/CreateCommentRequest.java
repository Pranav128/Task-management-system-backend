package com.app.payload.request;

public class CreateCommentRequest {
    private String comment; // The comment text
    private Long userId;    // The ID of the user who created the comment

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}