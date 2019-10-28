package com.example.yallabee3.model;

public class Report {
    private String id;
    private String SenderId;
    private String message;
    private String problem;


    public Report() {
    }

    public Report(String id, String senderId, String message ,String problem) {
        this.id = id;
        this.SenderId = senderId;
        this.message = message;
        this.problem = problem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }
}
