package com.vaidh.customer.dto;

public class Endpoint {
    private String name;
    private METHOD method;

    public Endpoint(String name, METHOD method) {
        this.name = name;
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public METHOD getMethod() {
        return method;
    }

    public void setMethod(METHOD method) {
        this.method = method;
    }

    public enum METHOD {
        GET, POST, DELETE
    }
}
