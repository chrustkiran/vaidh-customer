package com.vaidh.customer.dto;


public class FileResponseMessage implements ResponseMessage{
    private static final long serialVersionUID = 1L;

    private String message;
    private String url;

    public FileResponseMessage(String message, String url) {
        this.message = message;
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
