package com.vaidh.customer.dto;


public class FileResponseMessage extends ResponseCommonMessage{
    private static final long serialVersionUID = 1L;

    private String url;

    public FileResponseMessage(String message, String url) {
        super.setMessage(message);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
