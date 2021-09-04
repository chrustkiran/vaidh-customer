package com.vaidh.customer.dto;
import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private String title;
    private String details;
    private HttpStatus status;

    public ErrorResponse(HttpStatus status, Exception e) {
        this.title = status.toString();
        this.details = e.toString();
        this.status = status;
    }

    public ErrorResponse(HttpStatus status, String message) {
        this.title = status.toString();
        this.details = message;
        this.status = status;
    }

    public ErrorResponse(String title, String details, HttpStatus status) {
        this.title = title;
        this.details = details;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
