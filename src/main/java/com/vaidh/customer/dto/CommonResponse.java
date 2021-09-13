package com.vaidh.customer.dto;

import com.vaidh.customer.model.inventory.Product;

import java.util.ArrayList;
import java.util.List;

public class CommonResponse {
    private boolean isError;
    private List<CommonResults> commonResults;
    private boolean isSuccess = true;
    private ErrorResponse error;

    public CommonResponse() {
    }

    public CommonResponse(List<CommonResults> commonResults) {
        this.commonResults = commonResults;
    }

    public CommonResponse(boolean isError, ErrorResponse error) {
        this.isSuccess = false;
        this.isError = isError;
        this.error = error;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public List<CommonResults> getCommonResults() {
        return commonResults;
    }

    public void setCommonResults(ArrayList<CommonResults> commonResults) {
        this.commonResults = commonResults;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
