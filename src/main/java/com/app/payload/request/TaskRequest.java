package com.app.payload.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class TaskRequest {

    private  CreateTaskRequest createTaskRequest;
    @NotNull(message = "CreatedBy ID is required")
    private Long createdById;
    @NotNull(message = "Assignee ID's are required")
    private List<Long> assigneeIds;
    private String comments;

    public TaskRequest() {
    }

    public TaskRequest(CreateTaskRequest createTaskRequest, Long createdById, List<Long> assigneeIds, String comments) {
        this.createTaskRequest = createTaskRequest;
        this.createdById = createdById;
        this.assigneeIds = assigneeIds;
        this.comments = comments;
    }

    public CreateTaskRequest getCreateTaskRequest() {
        return createTaskRequest;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setCreateTaskRequest(CreateTaskRequest createTaskRequest) {
        this.createTaskRequest = createTaskRequest;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

      public List<Long> getAssigneeIds() {
        return assigneeIds;
    }

    public void setAssigneeIds(List<Long> assigneeIds) {
        this.assigneeIds = assigneeIds;
    }
}
